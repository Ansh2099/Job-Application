package com.Job.Application.Controllers;

import com.Job.Application.Model.Companies;
import com.Job.Application.Model.Jobs;
import com.Job.Application.Model.User;
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

@RestController
@RequestMapping("/api/v1/companies/{companyId}/jobs")
@RequiredArgsConstructor
@Tag(name = "Job Controller")
public class JobControllers {

    private final JobsService jobsService;
    private final CompanyService companyService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<Jobs>> getAllJobsByCompanyId(@PathVariable("companyId") Long companyId) {
        return ResponseEntity.ok().body(jobsService.getJobsByCompanyId(companyId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jobs> getJobById(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id) {
        Jobs job = jobsService.getJobById(id);
        
        // Ensure job belongs to the specified company
        if (!job.getCompany().getId().equals(companyId)) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok().body(job);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('RECRUITER')")
    public ResponseEntity<Jobs> createJob(
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
        return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RECRUITER')")
    public ResponseEntity<Jobs> updateJob(
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
        return ResponseEntity.ok().body(updatedJob);
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
