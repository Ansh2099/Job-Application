package com.Job.Application.Controllers;

import com.Job.Application.Mappers.JobMapper;
import com.Job.Application.Model.Companies;
import com.Job.Application.Model.Jobs;
import com.Job.Application.Model.User;
import com.Job.Application.Response.JobResponse;
import com.Job.Application.Service.CompanyService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/companies/{companyId}/jobs")
@RequiredArgsConstructor
@Tag(name = "Job Controller")
public class JobControllers {

    private final JobsService jobsService;
    private final CompanyService companyService;
    private final UserService userService;
    private final JobMapper jobMapper;

    @GetMapping
    public ResponseEntity<List<JobResponse>> getAllJobsByCompanyId(@PathVariable("companyId") Long companyId) {
        List<JobResponse> jobResponses = jobsService.getJobsByCompanyId(companyId).stream()
                .map(jobMapper::toJobResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(jobResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJobById(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id) {
        Jobs job = jobsService.getJobById(id);
        
        // Ensure job belongs to the specified company
        if (!job.getCompany().getId().equals(companyId)) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok().body(jobMapper.toJobResponse(job));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('RECRUITER')")
    public ResponseEntity<JobResponse> createJob(
            @PathVariable("companyId") Long companyId,
            @Valid @RequestBody Jobs job) {
        
        // Check if current user is a recruiter for this company
        User currentUser = userService.getCurrentUser();
        if (currentUser.isRecruiter() && 
            (currentUser.getCompany() == null || !currentUser.getCompany().getId().equals(companyId))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        // Associate job with company
        Companies company = companyService.getCompanyById(companyId);
        job.setCompany(company);
        
        Jobs createdJob = jobsService.createJob(job);
        return new ResponseEntity<>(jobMapper.toJobResponse(createdJob), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RECRUITER')")
    public ResponseEntity<JobResponse> updateJob(
            @PathVariable("companyId") Long companyId,
            @PathVariable("id") Long id,
            @Valid @RequestBody Jobs job) {
        
        // Check if current user is a recruiter for this company
        User currentUser = userService.getCurrentUser();
        if (currentUser.isRecruiter() && 
            (currentUser.getCompany() == null || !currentUser.getCompany().getId().equals(companyId))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        // Ensure job belongs to the specified company
        Jobs existingJob = jobsService.getJobById(id);
        if (!existingJob.getCompany().getId().equals(companyId)) {
            return ResponseEntity.notFound().build();
        }
        
        Companies company = companyService.getCompanyById(companyId);
        job.setCompany(company);
        
        Jobs updatedJob = jobsService.updateJob(id, job);
        return ResponseEntity.ok().body(jobMapper.toJobResponse(updatedJob));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RECRUITER')")
    public ResponseEntity<Void> deleteJob(
            @PathVariable("companyId") Long companyId,
            @PathVariable("id") Long id) {
        
        // Check if current user is a recruiter for this company
        User currentUser = userService.getCurrentUser();
        if (currentUser.isRecruiter() && 
            (currentUser.getCompany() == null || !currentUser.getCompany().getId().equals(companyId))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        // Ensure job belongs to the specified company
        Jobs job = jobsService.getJobById(id);
        if (!job.getCompany().getId().equals(companyId)) {
            return ResponseEntity.notFound().build();
        }
        
        jobsService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }
}
