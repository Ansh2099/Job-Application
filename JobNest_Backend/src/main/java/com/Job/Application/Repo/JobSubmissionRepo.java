package com.Job.Application.Repo;

import com.Job.Application.Constants.JobSubmissionConstants;
import com.Job.Application.Model.JobSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSubmissionRepo extends JpaRepository<JobSubmission, Long> {
    @Query(name = JobSubmissionConstants.FindByJobCompanyId)
    List<JobSubmission> findByJobCompanyId(Long companyId);

    @Query(name = JobSubmissionConstants.FindByJobId)
    List<JobSubmission> findByJobId(Long jobId);

    @Query(name = JobSubmissionConstants.FindByUserId)
    List<JobSubmission> findByUserId(Long userId);
} 