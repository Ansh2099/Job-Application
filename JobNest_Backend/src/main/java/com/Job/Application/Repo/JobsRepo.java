package com.Job.Application.Repo;

import com.Job.Application.Constants.JobsConstants;
import com.Job.Application.Model.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobsRepo extends JpaRepository<Jobs, Long> {
    @Query(name = JobsConstants.FindByCompanyId)
    List<Jobs> findByCompanyId(Long companyId);
}
