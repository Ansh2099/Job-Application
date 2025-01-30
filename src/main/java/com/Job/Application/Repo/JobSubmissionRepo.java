package com.Job.Application.Repo;

import com.Job.Application.Model.JobSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSubmissionRepo extends JpaRepository<JobSubmission, Long> {
    List<JobSubmission> findByJobCompanyId(Long companyId);
    List<JobSubmission> findByJobId(Long jobId);
} 