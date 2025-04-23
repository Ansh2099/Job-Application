package com.Job.Application.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import lombok.*;

import com.Job.Application.Constants.ApplicationStatus;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JobSubmission {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // ID of the user who submitted the application
    private String userId;
    
    @NotBlank
    private String applicantName;
    
    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    private String resume; // This could be a file path or URL
    
    @Column(length = 1000)
    private String coverLetter;
    
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.PENDING;
    
    private java.time.LocalDateTime submissionDate = java.time.LocalDateTime.now();
    
    @ManyToOne
    @JoinColumn(name = "job_id")
    private Jobs job;
}