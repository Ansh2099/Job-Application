package com.Job.Application.Security;

import com.Job.Application.Service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter to synchronize Keycloak users with the local database
 * This runs after authentication to ensure we have up-to-date user data
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class KeycloakUserSyncFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // Only process if we have a JWT authentication
        if (authentication instanceof JwtAuthenticationToken) {
            try {
                log.debug("Synchronizing user from Keycloak");
                userService.syncUserWithKeycloak();
            } catch (Exception e) {
                log.error("Error synchronizing user from Keycloak", e);
                // Continue with the filter chain even if synchronization fails
            }
        }
        
        filterChain.doFilter(request, response);
    }
} 