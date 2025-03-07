package com.Job.Application.Service;

import com.Job.Application.Model.Reviews;
import com.Job.Application.Repo.ReviewRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepo repo;

    public List<Reviews> getAllReviews() {
        return repo.findAll();
    }

    public Reviews postReview(Reviews review) {
        return repo.save(review);
    }

    public void deleteReview(Long id) {
        repo.deleteById(id);
    }

    public Reviews getReviewById(long id) {
        return repo.findById(id).orElse(null);
    }

    public Object updateReview(@Valid Reviews review, Long id) {
        return repo.save(review);
    }
}