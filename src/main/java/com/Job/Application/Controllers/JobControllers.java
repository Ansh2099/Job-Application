package com.Job.Application.Controllers;

import com.Job.Application.Service.JobsService;
import com.Job.Application.Service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.Job.Application.Model.Jobs;
import com.Job.Application.Model.Companies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/companies/{companyId}/jobs")
public class JobControllers {

    @Autowired
    private JobsService service;

    @Autowired
    private CompanyService companyService;

    @GetMapping("/")
    public ResponseEntity<List<Jobs>> getAllJobs(@PathVariable Long companyId) {
        Companies company = companyService.getCompanyById(companyId);
        if (company == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(company.getJobs(), HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jobs> getJobById(@PathVariable Long id){
        Jobs job = service.getJobById(id);
        if (job != null)
            return new ResponseEntity<>(job, HttpStatus.FOUND);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/")
    public ResponseEntity<?> postJob(@PathVariable Long companyId, @Valid @RequestBody Jobs job) {
        try {
            Companies company = companyService.getCompanyById(companyId);
            if (company == null) {
                return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
            }
            job.setCompany(company);
            return new ResponseEntity<>(service.postJob(job), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long companyId, @PathVariable Long id) {
        Jobs job = service.getJobById(id);
        if (job != null && job.getCompany().getId().equals(companyId)) {
            service.deleteJob(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJob(@PathVariable Long companyId,
                                     @PathVariable Long id,
                                     @Valid @RequestBody Jobs job) {
        try {
            Jobs existingJob = service.getJobById(id);
            if (existingJob != null && existingJob.getCompany().getId().equals(companyId)) {
                Companies company = companyService.getCompanyById(companyId);
                job.setCompany(company);
                job.setId(id);
                return new ResponseEntity<>(service.updateJobs(job, id), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
