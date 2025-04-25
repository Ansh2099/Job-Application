package com.Job.Application.Service;

import com.Job.Application.Mappers.JobMapper;
import com.Job.Application.Model.Jobs;
import com.Job.Application.Repo.JobsRepo;
import com.Job.Application.Response.JobResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobsService {
    
    private final JobsRepo jobsRepo;
    private final JobMapper jobMapper;
    
    /**
     * Get all jobs
     */
    public List<Jobs> getAllJobs() {
        return jobsRepo.findAll();
    }
    
    public List<JobResponse> getAllJobResponses() {
        return getAllJobs().stream()
                .map(jobMapper::toJobResponse)
                .collect(Collectors.toList());
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
    
    public JobResponse getJobResponseById(Long id) {
        return jobMapper.toJobResponse(getJobById(id));
    }
    
    /**
     * Create a new job
     */
    @Transactional
    public Jobs createJob(Jobs job) {
        // Only set fields that exist in the model
        // job.setCompany(job.getCompany()); // If you want to set company, do it here
        return jobsRepo.save(job);
    }
    
    public JobResponse createJobAndGetResponse(Jobs job) {
        return jobMapper.toJobResponse(createJob(job));
    }
    
    /**
     * Update an existing job
     */
    @Transactional
    public Jobs updateJob(Long id, Jobs jobDetails) {
        Jobs job = getJobById(id);
        // Update only existing fields
        job.setTitle(jobDetails.getTitle());
        job.setDescription(jobDetails.getDescription());
        job.setMinSalary(jobDetails.getMinSalary());
        job.setMaxSalary(jobDetails.getMaxSalary());
        job.setLocation(jobDetails.getLocation());
        // job.setCompany(jobDetails.getCompany()); // If you want to allow updating company
        return jobsRepo.save(job);
    }
    
    public JobResponse updateJobAndGetResponse(Long id, Jobs jobDetails) {
        return jobMapper.toJobResponse(updateJob(id, jobDetails));
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
