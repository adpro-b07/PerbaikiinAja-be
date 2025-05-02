package com.advprog.perbaikiinaja.controller;

import com.advprog.perbaikiinaja.model.Review;
import com.advprog.perbaikiinaja.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        Review createdReview = reviewService.addReview(review);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    @GetMapping("/technician/{technicianId}")
    public ResponseEntity<List<Review>> getReviewsForTechnician(@PathVariable int technicianId) {
        List<Review> reviews = reviewService.getReviewsForTechnician(technicianId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable int reviewId, @RequestBody Review review) {
        Review updatedReview = new Review(
            reviewId,
            review.getUserId(),
            review.getTechnicianId(),
            review.getRating(),
            review.getReviewText(),
            review.getTimestamp()
        );
        Review result = reviewService.updateReview(updatedReview, review.getUserId());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable int reviewId,
            @RequestParam int currentUserId,
            @RequestParam boolean isAdmin) {
        reviewService.deleteReview(reviewId, currentUserId, isAdmin);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/technician/{technicianId}/average-rating")
    public ResponseEntity<Double> getAverageRatingForTechnician(@PathVariable int technicianId) {
        double averageRating = reviewService.getAverageRatingForTechnician(technicianId);
        return new ResponseEntity<>(averageRating, HttpStatus.OK);
    }
}