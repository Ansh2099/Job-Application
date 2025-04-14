package com.Job.Application.Constants;

/**
 * Constants for role names used in the application
 * All role constants are prefixed with ROLE_ as required by Spring Security
 */
public class RoleConstants {
    // User roles
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_JOB_SEEKER = "ROLE_JOB_SEEKER";
    public static final String ROLE_RECRUITER = "ROLE_RECRUITER";
    
    // Prevent instantiation
    private RoleConstants() {
        throw new IllegalStateException("Constants class");
    }
} 