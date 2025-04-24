package com.Job.Application.Service;

import com.Job.Application.Constants.ApplicationStatus;
import com.Job.Application.Model.JobSubmission;
import com.Job.Application.Repo.JobSubmissionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class JobSubmissionService {
    
    private final JobSubmissionRepo jobSubmissionRepo;
    
    /**
     * Submit a job application
     */
    @Transactional
    public JobSubmission submitApplication(JobSubmission application) {
        return jobSubmissionRepo.save(application);
    }
    
    /**
     * Get all applications for a company
     */
    public List<JobSubmission> getApplicationsByCompany(Long companyId) {
        return jobSubmissionRepo.findByJobCompanyId(companyId);
    }
    
    /**
     * Get all applications for a user
     */
    public List<JobSubmission> getApplicationsByUserId(String userId) {
        return jobSubmissionRepo.findByUserId(Long.valueOf(userId));
    }
    
    /**
     * Get application by ID
     */
    public JobSubmission getApplicationById(Long id) {
        return jobSubmissionRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Application not found with ID: " + id));
    }
    
    /**
     * Update application status
     */
    @Transactional
    public JobSubmission updateApplicationStatus(Long id, ApplicationStatus status) {
        JobSubmission application = getApplicationById(id);
        application.setStatus(status);
        return jobSubmissionRepo.save(application);
    }
} 