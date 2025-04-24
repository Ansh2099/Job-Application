package com.Job.Application.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import lombok.*;

import com.Job.Application.Constants.ApplicationStatus;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "job_submission")
public class JobSubmission {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
    
    @NotBlank
    @Column(name = "applicant_name")
    private String applicantName;
    
    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    private String resume; // This could be a file path or URL
    
    @Column(name = "cover_letter", length = 1000)
    private String coverLetter;
    
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.PENDING;
    
    private LocalDateTime submissionDate = LocalDateTime.now();
    
    @ManyToOne
    @JoinColumn(name = "job_id")
    private Jobs job;
}