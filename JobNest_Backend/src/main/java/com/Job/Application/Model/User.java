package com.Job.Application.Model;

import com.Job.Application.Common.BaseAuditClass;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

public class User extends BaseAuditClass {

    private static final int LAST_ACTIVATE_INTERVAL = 5;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Using Keycloak UUID as primary key
    
    @NotBlank
    private String username;
    
    @NotBlank
    private String firstName;
    
    @NotBlank
    private String lastName;
    
    @Email
    @NotBlank
    private String email;

    @Transient
    @Column(name = "roles")
    private Set<String> roles;

    // Common profile fields
    private String phoneNumber;
    
    // Job Seeker specific fields
    private String resume;
    private String skills;
    private String experience;
    
    // Recruiter specific fields
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Companies company;
    private String position;
    private LocalDateTime lastSeen;

    // Utility methods
    @Transient
    public boolean isJobSeeker() {
        return roles != null && roles.contains("ROLE_JOB_SEEKER");
    }

    @Transient
    public boolean isRecruiter() {
        return roles != null && roles.contains("ROLE_RECRUITER");
    }

    public Boolean isFirstLogin() {
        return true;
    }

    @Transient
    public boolean isUserOnline() {
        return lastSeen != null && lastSeen.isAfter(LocalDateTime.now().minusMinutes(LAST_ACTIVATE_INTERVAL));
    }
}