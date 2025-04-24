package com.Job.Application.Service;

import com.Job.Application.Model.Reviews;
import com.Job.Application.Repo.ReviewRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReviewService {
    
    private final ReviewRepo reviewRepo;
    
    /**
     * Get all reviews
     */
    public List<Reviews> getAllReviews() {
        return reviewRepo.findAll();
    }
    
    /**
     * Get reviews by company ID
     */
    public List<Reviews> getReviewsByCompanyId(Long companyId) {
        return reviewRepo.findByCompanyId(companyId);
    }
    
    /**
     * Get review by ID
     */
    public Reviews getReviewById(Long id) {
        return reviewRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Review not found with ID: " + id));
    }
    
    /**
     * Create a new review
     */
    @Transactional
    public Reviews createReview(Reviews review) {
        return reviewRepo.save(review);
    }
    
    /**
     * Update an existing review
     */
    @Transactional
    public Reviews updateReview(Long id, Reviews reviewDetails) {
        Reviews review = getReviewById(id);
        
        // Update review details
        review.setTitle(reviewDetails.getTitle());
        review.setDescription(reviewDetails.getDescription());
        review.setRating(reviewDetails.getRating());
        review.setCompany(reviewDetails.getCompany());
        
        return reviewRepo.save(review);
    }
    
    /**
     * Delete a review
     */
    @Transactional
    public void deleteReview(Long id) {
        Reviews review = getReviewById(id);
        reviewRepo.delete(review);
    }
}