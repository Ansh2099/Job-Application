package com.Job.Application.Service;

import com.Job.Application.Model.User;
import com.Job.Application.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepo userRepo;
    private final UserSynchronizer userSynchronizer;
    
    /**
     * Get the currently authenticated user
     */
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
            String userId = jwtToken.getToken().getSubject();
            
            return userRepo.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("Current user not found in database"));
        }
        
        throw new IllegalStateException("Not authenticated with JWT token");
    }
    
    /**
     * Get all users
     */
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
    
    /**
     * Update a user
     */
    @Transactional
    public User updateUser(User user) {
        // Ensure the user exists
        userRepo.findById(String.valueOf(user.getId()))
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + user.getId()));
        
        return userRepo.save(user);
    }
    
    /**
     * Get user by ID
     */
    public User getUserById(String id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + id));
    }
    
    /**
     * Get user by email
     */
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User not found with email: " + email));
    }
    
    /**
     * Check if user exists by email
     */
    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }
}
