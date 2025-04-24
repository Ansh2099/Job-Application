package com.Job.Application.Repo;

import com.Job.Application.Model.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepo extends JpaRepository<Reviews, Long> {
    List<Reviews> findByCompanyId(Long companyId);
}
