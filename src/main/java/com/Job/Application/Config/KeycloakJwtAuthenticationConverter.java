package com.Job.Application.Config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

/**
 * Converts a Keycloak JWT token to an AbstractAuthenticationToken
 * Also handles extracting roles from the token in a standardized format
 */
@Component
public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    
    private final JwtGrantedAuthoritiesConverter defaultGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    
    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt source) {
        return new JwtAuthenticationToken(
                source,
                Stream.concat(
                        defaultGrantedAuthoritiesConverter.convert(source).stream(),
                        extractAllRoles(source).stream())
                        .collect(toSet()));
    }

    /**
     * Extract roles from the JWT token and convert them to GrantedAuthority objects
     */
    public Collection<? extends GrantedAuthority> extractAllRoles(Jwt jwt) {
        Set<String> roles = extractRolesFromToken(jwt);
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(toSet());
    }
    
    /**
     * Public method to extract roles from a JWT token as strings
     * This can be used by other components like UserSynchronizer
     */
    @SuppressWarnings("unchecked")
    public Set<String> extractRolesFromToken(Jwt token) {
        Set<String> roles = new HashSet<>();
        
        try {
            // Extract roles from resource_access claim (client roles)
            Map<String, Object> resourceAccess = token.getClaimAsMap("resource_access");
            if (resourceAccess != null) {
                Map<String, Object> account = (Map<String, Object>) resourceAccess.get("account");
                if (account != null) {
                    List<String> keycloakRoles = (List<String>) account.get("roles");
                    if (keycloakRoles != null) {
                        for (String role : keycloakRoles) {
                            // Format role name with ROLE_ prefix and standardize format
                            roles.add("ROLE_" + role.replace("-", "_").toUpperCase());
                        }
                    }
                }
            }
            
            // Extract roles from realm_access claim (realm roles)
            Map<String, Object> realmAccess = token.getClaimAsMap("realm_access");
            if (realmAccess != null) {
                List<String> realmRoles = (List<String>) realmAccess.get("roles");
                if (realmRoles != null) {
                    for (String role : realmRoles) {
                        roles.add("ROLE_" + role.replace("-", "_").toUpperCase());
                    }
                }
            }
        } catch (Exception e) {
            // Add a default role if we can't extract any
            roles.add("ROLE_USER");
        }
        
        return roles;
    }
}