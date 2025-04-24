package com.Job.Application.Controllers;

import com.Job.Application.Constants.ApplicationStatus;
import com.Job.Application.Model.JobSubmission;
import com.Job.Application.Model.Jobs;
import com.Job.Application.Model.User;
import com.Job.Application.Service.JobSubmissionService;
import com.Job.Application.Service.JobsService;
import com.Job.Application.Service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/applications")
@Tag(name = "Job Submission Controller")
public class JobSubmissionController {

    private final JobSubmissionService jobSubmissionService;
    private final JobsService jobsService;
    private final UserService userService;

    /**
     * Submit a job application (available only to job seekers)
     */
    @PostMapping("/jobs/{jobId}/apply")
    @PreAuthorize("hasRole('JOB_SEEKER')")
    public ResponseEntity<JobSubmission> submitApplication(
            @PathVariable Long jobId,
            @Valid @RequestBody JobSubmission application) {
        
        Jobs job = jobsService.getJobById(jobId);
        application.setJob(job);
        
        // Associate with current user
        User currentUser = userService.getCurrentUser();
        application.setUserId(currentUser.getId());
        
        // Use user profile data if available
        if (application.getApplicantName() == null || application.getApplicantName().isBlank()) {
            application.setApplicantName(currentUser.getFirstName() + " " + currentUser.getLastName());
        }
        
        if (application.getEmail() == null || application.getEmail().isBlank()) {
            application.setEmail(currentUser.getEmail());
        }
        
        JobSubmission submittedApplication = jobSubmissionService.submitApplication(application);
        return new ResponseEntity<>(submittedApplication, HttpStatus.CREATED);
    }

    /**
     * Get all applications for a company (available only to recruiters)
     */
    @GetMapping("/companies/{companyId}")
    @PreAuthorize("hasRole('RECRUITER')")
    public ResponseEntity<List<JobSubmission>> getCompanyApplications(@PathVariable Long companyId) {
        // Check if current user is a recruiter for this company
        User currentUser = userService.getCurrentUser();
        if (currentUser.isRecruiter() && 
            (currentUser.getCompany() == null || !currentUser.getCompany().getId().equals(companyId))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        List<JobSubmission> applications = jobSubmissionService.getApplicationsByCompany(companyId);
        return ResponseEntity.ok(applications);
    }
    
    /**
     * Get all applications for the current user (available only to job seekers)
     */
    @GetMapping("/my-applications")
    @PreAuthorize("hasRole('JOB_SEEKER')")
    public ResponseEntity<List<JobSubmission>> getMyApplications() {
        User currentUser = userService.getCurrentUser();
        List<JobSubmission> applications = jobSubmissionService.getApplicationsByUserId(currentUser.getId());
        return ResponseEntity.ok(applications);
    }

    /**
     * Update application status (available only to recruiters)
     */
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('RECRUITER')")
    public ResponseEntity<JobSubmission> updateApplicationStatus(
            @PathVariable Long id,
            @RequestParam ApplicationStatus status) {
        
        JobSubmission application = jobSubmissionService.getApplicationById(id);
        
        // Check if current user is a recruiter for the company that owns this job
        User currentUser = userService.getCurrentUser();
        if (currentUser.isRecruiter() && 
            (currentUser.getCompany() == null || 
             !currentUser.getCompany().getId().equals(application.getJob().getCompany().getId()))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        JobSubmission updatedApplication = jobSubmissionService.updateApplicationStatus(id, status);
        return ResponseEntity.ok(updatedApplication);
    }
} 