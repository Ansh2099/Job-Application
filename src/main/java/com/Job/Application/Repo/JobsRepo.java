package com.Job.Application.Repo;

import com.Job.Application.Model.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobsRepo extends JpaRepository<Jobs, Long> {
}
