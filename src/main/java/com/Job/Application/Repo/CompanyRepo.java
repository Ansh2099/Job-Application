package com.Job.Application.Repo;

import com.Job.Application.Model.Companies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepo extends JpaRepository<Companies, Long> {
}
