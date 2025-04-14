package com.Job.Application.Controllers;

import com.Job.Application.Constants.ApplicationStatus;
import com.Job.Application.Model.JobSubmission;
import com.Job.Application.Service.JobSubmissionService;
import com.Job.Application.Service.JobsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/applications")
public class JobSubmissionController {

    private final JobSubmissionService service;
    private final JobsService jobsService;

    @PostMapping("/jobs/{jobId}/apply")
    public ResponseEntity<?> submitApplication(@PathVariable Long jobId, 
                                            @Valid @RequestBody JobSubmission application) {
        try {
            application.setJob(jobsService.getJobById(jobId));
            return new ResponseEntity<>(service.submitApplication(application), 
                                      HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/companies/{companyId}")
    public ResponseEntity<?> getCompanyApplications(@PathVariable Long companyId) {
        return new ResponseEntity<>(service.getApplicationsByCompany(companyId), 
                                  HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateApplicationStatus(@PathVariable Long id, 
                                                  @RequestParam ApplicationStatus status) {
        JobSubmission updated = service.updateApplicationStatus(id, status);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
} 