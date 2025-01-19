package com.Job.Application.Controllers;

import com.Job.Application.Model.Reviews;
import com.Job.Application.Service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewControllers {

    @Autowired
    private ReviewService service;

    @GetMapping("/")
    public ResponseEntity<List<Reviews>> getAllReviews() {
        return new ResponseEntity<>(service.getAllReviews(), HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reviews> getReviewById(@PathVariable Long id){
        Reviews review = service.getReviewById(id);
        if (review != null)
            return new ResponseEntity<>(review, HttpStatus.FOUND);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/")
    public ResponseEntity<?> postReview(@Valid @RequestBody Reviews review){
        try{
            return new ResponseEntity<>(service.postReview(review), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReview(@PathVariable long id){
        Reviews review = service.getReviewById(id);
        if (review != null){
            service.deleteReview(id);
            return new ResponseEntity<>(HttpStatus.FOUND);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReview(@Valid @RequestPart Reviews review, @RequestPart Long id){
        try {
            Reviews review1 = service.getReviewById(id);
            if (review1 != null)
                return new ResponseEntity<>(service.updateReview(review, id), HttpStatus.FOUND);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
