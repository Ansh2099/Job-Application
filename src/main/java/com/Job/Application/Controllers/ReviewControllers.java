package com.Job.Application.Controllers;

import com.Job.Application.Model.Reviews;
import com.Job.Application.Service.ReviewService;
import com.Job.Application.Service.CompanyService;
import com.Job.Application.Model.Companies;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies/{companyId}/reviews")
public class ReviewControllers {

    @Autowired
    private ReviewService service;

    @Autowired
    private CompanyService companyService;

    @GetMapping("/")
    public ResponseEntity<List<Reviews>> getAllReviews(@PathVariable Long companyId) {
        Companies company = companyService.getCompanyById(companyId);
        if (company == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(company.getReviews(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reviews> getReviewById(@PathVariable Long companyId, @PathVariable Long id) {
        Reviews review = service.getReviewById(id);
        if (review != null && review.getCompany() != null && review.getCompany().getId().equals(companyId)) {
            return new ResponseEntity<>(review, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/")
    public ResponseEntity<?> postReview(@PathVariable Long companyId, @Valid @RequestBody Reviews review) {
        try {
            Companies company = companyService.getCompanyById(companyId);
            if (company == null) {
                return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
            }
            review.setCompany(company);
            return new ResponseEntity<>(service.postReview(review), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long companyId, @PathVariable Long id) {
        Reviews review = service.getReviewById(id);
        if (review != null && review.getCompany() != null && review.getCompany().getId().equals(companyId)) {
            service.deleteReview(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Long companyId, 
                                        @PathVariable Long id,
                                        @Valid @RequestBody Reviews review) {
        try {
            Reviews existingReview = service.getReviewById(id);
            if (existingReview != null && existingReview.getCompany() != null && 
                existingReview.getCompany().getId().equals(companyId)) {
                Companies company = companyService.getCompanyById(companyId);
                review.setCompany(company);
                review.setId(id);
                return new ResponseEntity<>(service.updateReview(review, id), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
