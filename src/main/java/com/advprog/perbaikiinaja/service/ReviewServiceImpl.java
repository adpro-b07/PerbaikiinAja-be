package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.model.Review;
import com.advprog.perbaikiinaja.repository.ReviewRepository;
import com.advprog.perbaikiinaja.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public Review addReview(Review review) {
        // Simpan review baru ke database
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsForTechnician(int technicianId) {
        // Ambil semua review milik teknisi tertentu
        return reviewRepository.findByTechnicianId(technicianId);
    }

    @Override
    public Review updateReview(Review review, int currentUserId) {
        Review existingReview = reviewRepository.findById(review.getReviewId())
                .orElseThrow(() -> new RuntimeException("Review tidak ditemukan."));

        // Validasi: hanya pemilik review yang bisa update
        if (existingReview.getUserId() != currentUserId) {
            throw new RuntimeException("User tidak memiliki hak untuk mengubah ulasan ini.");
        }

        // Update data review
        existingReview.setRating(review.getRating());
        existingReview.setReviewText(review.getReviewText());

        return reviewRepository.save(existingReview);
    }

    @Override
    public void deleteReview(int reviewId, int currentUserId, boolean isAdmin) {
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review tidak ditemukan."));

        // Validasi: hanya pemilik atau admin yang bisa hapus review
        if (existingReview.getUserId() != currentUserId && !isAdmin) {
            throw new RuntimeException("User tidak memiliki hak untuk menghapus ulasan ini.");
        }

        reviewRepository.delete(existingReview);
    }

    @Override
    public double getAverageRatingForTechnician(int technicianId) {
        List<Review> reviews = getReviewsForTechnician(technicianId);
        if (reviews.isEmpty()) {
            return 0;
        }
        return reviews.stream().mapToInt(Review::getRating).average().orElse(0);
    }
}
