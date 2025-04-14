package com.Job.Application.Service;

import com.Job.Application.Model.User;
import com.Job.Application.Repo.UserRepo;
import com.Job.Application.Util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for user-related operations
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepo userRepo;
    private final UserSynchronizer userSynchronizer;

    /**
     * Sync the current authenticated user with Keycloak
     */
    @Transactional
    public User syncUserWithKeycloak() {
        return SecurityUtils.getCurrentToken()
                .map(userSynchronizer::synchronizeWithKeycloak)
                .orElseThrow(() -> new IllegalStateException("User not authenticated with JWT token"));
    }
    
    /**
     * Get the current authenticated user
     */
    public User getCurrentUser() {
        String userId = SecurityUtils.getCurrentUserId()
                .orElseThrow(() -> new IllegalStateException("User not authenticated with JWT token"));
        
        return userRepo.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found in database"));
    }
    
    /**
     * Get a user by their ID
     */
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }
    
    /**
     * Get a user by their email
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }
    
    /**
     * Get all users in the system
     */
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
    
    /**
     * Update a user's information
     */
    @Transactional
    public User updateUser(User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User ID cannot be null for update operation");
        }
        
        if (!userRepo.existsById(user.getId())) {
            throw new IllegalArgumentException("User does not exist with ID: " + user.getId());
        }
        
        return userRepo.save(user);
    }
    
    /**
     * Delete a user by their ID
     */
    @Transactional
    public void deleteUser(String id) {
        userRepo.deleteById(id);
    }
    
    /**
     * Check if a user exists in the system
     */
    public boolean userExists(String id) {
        return userRepo.existsById(id);
    }
    
    /**
     * Check if an email is already registered
     */
    public boolean emailExists(String email) {
        return userRepo.existsByEmail(email);
    }
} 