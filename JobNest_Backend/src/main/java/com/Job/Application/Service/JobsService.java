package com.Job.Application.Service;

import com.Job.Application.Model.Jobs;
import com.Job.Application.Repo.JobsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class JobsService {
    
    private final JobsRepo jobsRepo;
    
    /**
     * Get all jobs
     */
    public List<Jobs> getAllJobs() {
        return jobsRepo.findAll();
    }
    
    /**
     * Get jobs by company ID
     */
    public List<Jobs> getJobsByCompanyId(Long companyId) {
        return jobsRepo.findByCompanyId(companyId);
    }
    
    /**
     * Get job by ID
     */
    public Jobs getJobById(Long id) {
        return jobsRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Job not found with ID: " + id));
    }
    
    /**
     * Create a new job
     */
    @Transactional
    public Jobs createJob(Jobs job) {
        return jobsRepo.save(job);
    }
    
    /**
     * Update an existing job
     */
    @Transactional
    public Jobs updateJob(Long id, Jobs jobDetails) {
        Jobs job = getJobById(id);
        
        // Update job details
        job.setTitle(jobDetails.getTitle());
        job.setDescription(jobDetails.getDescription());
        job.setMinSalary(jobDetails.getMinSalary());
        job.setMaxSalary(jobDetails.getMaxSalary());
        job.setLocation(jobDetails.getLocation());
        job.setCompany(jobDetails.getCompany());
        
        return jobsRepo.save(job);
    }
    
    /**
     * Delete a job
     */
    @Transactional
    public void deleteJob(Long id) {
        Jobs job = getJobById(id);
        jobsRepo.delete(job);
    }
}
