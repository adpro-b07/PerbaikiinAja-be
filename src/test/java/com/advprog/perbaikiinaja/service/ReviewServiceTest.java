package com.advprog.perbaikiinaja.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.advprog.perbaikiinaja.model.Review;
import com.advprog.perbaikiinaja.service.ReviewService;

import java.util.List;

public class ReviewServiceTest {

    private ReviewService reviewService;

    @BeforeEach
    public void setUp() {
        reviewService = new ReviewService();
    }

    @Test
    public void testAddReview() {
        Review review = new Review(1, 100, 200, 5, "Great service!", System.currentTimeMillis());
        reviewService.addReview(review);
        List<Review> reviews = reviewService.getReviewsForTechnician(200);
        assertEquals(1, reviews.size(), "Harus ada 1 review untuk teknisi 200.");
        assertEquals(review, reviews.get(0), "Review yang diambil harus sama dengan review yang ditambahkan.");
    }

    @Test
    public void testUpdateReviewSuccess() {
        // generate review.
        Review review = new Review(1, 100, 200, 4, "Good service", System.currentTimeMillis());
        reviewService.addReview(review);

        // update review.
        Review updatedReview = new Review(1, 100, 200, 5, "Excellent service", System.currentTimeMillis());
        reviewService.updateReview(updatedReview, 100);

        Review retrievedReview = reviewService.getReviewsForTechnician(200).get(0);
        assertEquals(5, retrievedReview.getRating(), "Rating harus terupdate jadi 5.");
        assertEquals("Excellent service", retrievedReview.getReviewText(), "ReviewText harus terupdate.");
    }

    @Test
    public void testUpdateReviewFail() {
        // generate review.
        Review review = new Review(1, 100, 200, 4, "Good service", System.currentTimeMillis());
        reviewService.addReview(review);

        // update has to fail since user101 is making not 100.
        Review updatedReview = new Review(1, 100, 200, 5, "Changed review", System.currentTimeMillis());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reviewService.updateReview(updatedReview, 101);
        });
        assertTrue(exception.getMessage().contains("User tidak memiliki hak untuk mengubah ulasan ini."));
    }

    @Test
    public void testDeleteReviewAsOwner() {
        // generate review.
        Review review = new Review(1, 100, 200, 5, "Great service", System.currentTimeMillis());
        reviewService.addReview(review);

        // edit review as owner.
        reviewService.deleteReview(1, 100, false);
        List<Review> reviews = reviewService.getReviewsForTechnician(200);
        assertTrue(reviews.isEmpty(), "Review harus sudah terhapus.");
    }

    @Test
    public void testDeleteReviewAsAdmin() {
        // generate review.
        Review review = new Review(1, 100, 200, 5, "Great service", System.currentTimeMillis());
        reviewService.addReview(review);
        // Hapus review sebagai admin (admin=true).
        reviewService.deleteReview(1, 101, true);
        List<Review> reviews = reviewService.getReviewsForTechnician(200);
        assertTrue(reviews.isEmpty(), "Review harus sudah terhapus oleh admin.");
    }

    @Test
    public void testDeleteReviewFail() {
        // generate review.
        Review review = new Review(1, 100, 200, 5, "Great service", System.currentTimeMillis());
        reviewService.addReview(review);
        // has to fail since not the actual user nor admin.
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reviewService.deleteReview(1, 101, false);
        });
        assertTrue(exception.getMessage().contains("User tidak memiliki hak untuk menghapus ulasan ini."));
    }

    @Test
    public void testGetAverageRatingForTechnician() {
        Review review1 = new Review(1, 100, 200, 4, "Good", System.currentTimeMillis());
        Review review2 = new Review(2, 101, 200, 5, "Excellent", System.currentTimeMillis());
        Review review3 = new Review(3, 102, 200, 3, "Average", System.currentTimeMillis());
        reviewService.addReview(review1);
        reviewService.addReview(review2);
        reviewService.addReview(review3);

        double expectedAverage = (4 + 5 + 3) / 3.0;
        double average = reviewService.getAverageRatingForTechnician(200);
        assertEquals(expectedAverage, average, "Rata-rata rating harus sesuai dengan perhitungan.");
    }
}
