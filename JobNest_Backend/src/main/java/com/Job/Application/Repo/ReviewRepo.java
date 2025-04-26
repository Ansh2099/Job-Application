package com.Job.Application.Repo;

import com.Job.Application.Constants.ReviewsConstants;
import com.Job.Application.Model.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepo extends JpaRepository<Reviews, Long> {
    @Query(name = ReviewsConstants.FindByCompanyId)
    List<Reviews> findByCompanyId(Long companyId);
}
