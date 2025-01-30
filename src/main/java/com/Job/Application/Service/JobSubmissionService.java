package com.Job.Application.Service;

import com.Job.Application.Model.JobSubmission;
import com.Job.Application.Repo.JobSubmissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSubmissionService {

    @Autowired
    private JobSubmissionRepo repo;

    public JobSubmission submitApplication(JobSubmission application) {
        return repo.save(application);
    }

    public List<JobSubmission> getApplicationsByCompany(Long companyId) {
        return repo.findByJobCompanyId(companyId);
    }

    public JobSubmission updateApplicationStatus(Long id, JobSubmission.ApplicationStatus status) {
        JobSubmission application = repo.findById(id).orElse(null);
        if (application != null) {
            application.setStatus(status);
            return repo.save(application);
        }
        return null;
    }
} 