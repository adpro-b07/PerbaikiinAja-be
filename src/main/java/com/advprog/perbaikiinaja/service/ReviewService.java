package com.advprog.perbaikiinaja.service;
import com.advprog.perbaikiinaja.model.Review;
import com.advprog.perbaikiinaja.repository.ReviewRepository;

import java.util.List;

public interface ReviewService {
    Review addReview(Review review);
    List<Review> getReviewsForTechnician(int technicianId);
    Review updateReview(Review review, int currentUserId);
    void deleteReview(int reviewId, int currentUserId, boolean isAdmin);
    double getAverageRatingForTechnician(int technicianId);
}