package com.Job.Application.Model;

import com.Job.Application.Common.BaseAuditClass;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User extends BaseAuditClass {

    @Id
    private String id; // Using Keycloak UUID as primary key
    
    @NotBlank
    private String username;
    
    @NotBlank
    private String firstName;
    
    @NotBlank
    private String lastName;
    
    @Email
    @NotBlank
    private String email;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles;
    
    // Common profile fields
    private String profilePicture;
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

    // Utility methods
    @Transient
    public boolean isJobSeeker() {
        return roles != null && roles.contains("ROLE_JOB_SEEKER");
    }
    
    public boolean isRecruiter() {
        return roles != null && roles.contains("ROLE_RECRUITER");
    }
} 