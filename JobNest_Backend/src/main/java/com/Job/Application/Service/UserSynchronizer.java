package com.Job.Application.Service;

import com.Job.Application.Config.KeycloakJwtAuthenticationConverter;
import com.Job.Application.Model.User;
import com.Job.Application.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service responsible for synchronizing Keycloak users with the local database
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserSynchronizer {

    private final UserRepo userRepo;
    private final KeycloakJwtAuthenticationConverter keycloakJwtAuthenticationConverter;

    /**
     * Synchronize user data from Keycloak to our database
     * This is called after authentication to ensure we have up-to-date user info
     */
    @Transactional
    public User synchronizeWithKeycloak(Jwt token) {
        log.info("Synchronizing user with Keycloak");
        Map<String, Object> claims = token.getClaims();
        
        // Check if sub claim exists (this is the user ID from Keycloak)
        if (claims.containsKey("sub")) {
            String userId = claims.get("sub").toString();
            log.info("Synchronizing user with ID {}", userId);
            
            // Extract user information from token
            String username = getClaimAsString(claims, "preferred_username");
            String email = getClaimAsString(claims, "email");
            String firstName = getClaimAsString(claims, "given_name");
            String lastName = getClaimAsString(claims, "family_name");
            
            // Extract user roles from token using the converter
            Set<String> roles = keycloakJwtAuthenticationConverter.extractRolesFromToken(token);
            
            // Try to find existing user by ID first
            Optional<User> existingUserById = userRepo.findById(userId);
            User user;
            
            if (existingUserById.isPresent()) {
                // Update existing user
                user = existingUserById.get();
                user.setUsername(username);
                user.setEmail(email);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setRoles(roles);
                log.info("Updating existing user with ID {}", userId);
            } else if (email != null && !email.isBlank()) {
                // Try to find by email if user doesn't exist by ID
                Optional<User> existingUserByEmail = userRepo.findByEmail(email);
                
                if (existingUserByEmail.isPresent()) {
                    user = existingUserByEmail.get();
                    // Update ID to match Keycloak ID
                    user.setId(userId);
                    user.setUsername(username);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setRoles(roles);
                    log.info("Updating existing user with email {} to have ID {}", email, userId);
                } else {
                    // Create new user
                    user = createNewUser(userId, username, email, firstName, lastName, roles);
                }
            } else {
                // Create new user if no email is present
                user = createNewUser(userId, username, email, firstName, lastName, roles);
            }
            
            // Save the user
            User savedUser = userRepo.save(user);
            log.info("User synchronization completed for ID {}", userId);
            return savedUser;
        } else {
            log.warn("Cannot synchronize user: missing 'sub' claim in token");
            throw new IllegalStateException("Invalid token: missing 'sub' claim");
        }
    }
    
    /**
     * Get the current user based on the JWT token
     */
    public Optional<User> getCurrentUser(Jwt token) {
        if (token != null && token.hasClaim("sub")) {
            String userId = token.getClaimAsString("sub");
            return userRepo.findById(userId);
        }
        return Optional.empty();
    }
    
    /**
     * Helper method to create a new user
     */
    private User createNewUser(String userId, String username, String email, 
                             String firstName, String lastName, Set<String> roles) {
        log.info("Creating new user with ID {} and email {}", userId, email);
        return User.builder()
                .id(userId)
                .username(username)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .roles(roles)
                .firstLogin(true)
                .build();
    }
    
    /**
     * Helper method to get a claim as a string
     */
    private String getClaimAsString(Map<String, Object> claims, String claimName) {
        if (claims.containsKey(claimName) && claims.get(claimName) != null) {
            return claims.get(claimName).toString();
        }
        return null;
    }
}