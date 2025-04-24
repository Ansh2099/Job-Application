package com.Job.Application.Controllers;

import com.Job.Application.Model.Companies;
import com.Job.Application.Model.Reviews;
import com.Job.Application.Model.User;
import com.Job.Application.Service.CompanyService;
import com.Job.Application.Service.ReviewService;
import com.Job.Application.Service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/companies/{companyId}/reviews")
@Tag(name = "Review Controller")
public class ReviewControllers {

    private final ReviewService reviewService;
    private final CompanyService companyService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<Reviews>> getAllReviewsByCompanyId(@PathVariable("companyId") Long companyId) {
        return ResponseEntity.ok().body(reviewService.getReviewsByCompanyId(companyId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reviews> getReviewById(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id) {
        Reviews review = reviewService.getReviewById(id);
        
        // Ensure review belongs to the specified company
        if (!review.getCompany().getId().equals(companyId)) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok().body(review);
    }

    @PostMapping
    @PreAuthorize("hasRole('JOB_SEEKER')")
    public ResponseEntity<Reviews> createReview(
            @PathVariable("companyId") Long companyId,
            @Valid @RequestBody Reviews review) {
        
        // Associate review with company
        Companies company = companyService.getCompanyById(companyId);
        review.setCompany(company);
        
        // Associate with current user
        User currentUser = userService.getCurrentUser();
        review.setUserId(currentUser.getId());
        
        Reviews createdReview = reviewService.createReview(review);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('JOB_SEEKER') or hasRole('ADMIN')")
    public ResponseEntity<Reviews> updateReview(
            @PathVariable("companyId") Long companyId,
            @PathVariable("id") Long id,
            @Valid @RequestBody Reviews review) {
        
        // Ensure review belongs to the specified company
        Reviews existingReview = reviewService.getReviewById(id);
        if (!existingReview.getCompany().getId().equals(companyId)) {
            return ResponseEntity.notFound().build();
        }
        
        // Check if current user is the owner of the review or an admin
        User currentUser = userService.getCurrentUser();
        if (!currentUser.getId().equals(existingReview.getUserId()) && 
            !currentUser.getRoles().contains("ROLE_ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        Companies company = companyService.getCompanyById(companyId);
        review.setCompany(company);
        review.setUserId(existingReview.getUserId());
        
        Reviews updatedReview = reviewService.updateReview(id, review);
        return ResponseEntity.ok().body(updatedReview);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('JOB_SEEKER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteReview(
            @PathVariable("companyId") Long companyId,
            @PathVariable("id") Long id) {
        
        // Ensure review belongs to the specified company
        Reviews review = reviewService.getReviewById(id);
        if (!review.getCompany().getId().equals(companyId)) {
            return ResponseEntity.notFound().build();
        }
        
        // Check if current user is the owner of the review or an admin
        User currentUser = userService.getCurrentUser();
        if (!currentUser.getId().equals(review.getUserId()) && 
            !currentUser.getRoles().contains("ROLE_ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}