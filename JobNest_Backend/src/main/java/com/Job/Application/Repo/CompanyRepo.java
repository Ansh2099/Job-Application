package com.Job.Application.Repo;

import com.Job.Application.Constants.CompanyConstants;
import com.Job.Application.Model.Companies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepo extends JpaRepository<Companies, Long> {
    @Query(name = CompanyConstants.SearchByKeyword)
    List<Companies> searchCompanies(String keyword);

}
