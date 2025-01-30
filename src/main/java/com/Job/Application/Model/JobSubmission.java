package com.Job.Application.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class JobSubmission {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    private String applicantName;
    
    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    private String resume; // This could be a file  path or URL
    
    @Column(length = 1000)
    private String coverLetter;
    
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.PENDING;
    
    @ManyToOne
    @JoinColumn(name = "job_id")
    private Jobs job;
    
    public enum ApplicationStatus {
        PENDING,
        ACCEPTED,
        REJECTED
    }

    public @NotBlank String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(@NotBlank String applicantName) {
        this.applicantName = applicantName;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public @NotBlank @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @Email String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank String getResume() {
        return resume;
    }

    public void setResume(@NotBlank String resume) {
        this.resume = resume;
    }

    public Jobs getJob() {
        return job;
    }

    public void setJob(Jobs job) {
        this.job = job;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}