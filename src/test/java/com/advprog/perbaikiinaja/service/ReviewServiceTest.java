package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.model.Review;
import com.advprog.perbaikiinaja.repository.ReviewRepository;
import com.advprog.perbaikiinaja.service.ReviewServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    public void testAddReview() {
        // Generate Review
        Review review = new Review(1, 100, 200, 5, "Great service!", System.currentTimeMillis());
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        // Act
        Review savedReview = reviewService.addReview(review);

        // Assert
        assertNotNull(savedReview);
        assertEquals(1, savedReview.getReviewId());
        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    public void testGetReviewsForTechnician() {
        // Generate Review
        Review review1 = new Review(1, 100, 200, 5, "Great service!", System.currentTimeMillis());
        Review review2 = new Review(2, 101, 200, 4, "Good service", System.currentTimeMillis());
        List<Review> reviewsList = Arrays.asList(review1, review2);
        when(reviewRepository.findByTechnicianId(200)).thenReturn(reviewsList);

        // Act
        List<Review> retrievedReviews = reviewService.getReviewsForTechnician(200);

        // Assert
        assertEquals(2, retrievedReviews.size());
        verify(reviewRepository, times(1)).findByTechnicianId(200);
    }

    @Test
    public void testUpdateReviewSuccess() {
        // Generate Review
        Review originalReview = new Review(1, 100, 200, 4, "Good service", System.currentTimeMillis());
        when(reviewRepository.findById(1)).thenReturn(Optional.of(originalReview));
        when(reviewRepository.save(any(Review.class))).thenReturn(originalReview);

        Review updatedReview = new Review(1, 100, 200, 5, "Excellent service", System.currentTimeMillis());

        // Act
        Review result = reviewService.updateReview(updatedReview, 100);

        // Assert
        assertEquals(5, result.getRating());
        assertEquals("Excellent service", result.getReviewText());
        verify(reviewRepository, times(1)).findById(1);
        verify(reviewRepository, times(1)).save(originalReview);
    }

    @Test
    public void testUpdateReviewFailDueToWrongUser() {
        // Generate Review
        Review originalReview = new Review(1, 100, 200, 4, "Good service", System.currentTimeMillis());
        when(reviewRepository.findById(1)).thenReturn(Optional.of(originalReview));

        Review updatedReview = new Review(1, 100, 200, 5, "Changed service", System.currentTimeMillis());

        // Update has to fail since user101 is making changes not user100.
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reviewService.updateReview(updatedReview, 101);
        });
        assertTrue(exception.getMessage().contains("User tidak memiliki hak untuk mengubah ulasan ini."));
        verify(reviewRepository, times(1)).findById(1);
        verify(reviewRepository, never()).save(any());
    }

    @Test
    public void testDeleteReviewAsOwner() {
        // Generate Review
        Review review = new Review(1, 100, 200, 5, "Great service", System.currentTimeMillis());
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));

        // Edit review as owner.
        reviewService.deleteReview(1, 100, false);

        // Assert
        verify(reviewRepository, times(1)).delete(review);
    }

    @Test
    public void testDeleteReviewAsAdmin() {
        // Generate Review
        Review review = new Review(1, 100, 200, 5, "Great service", System.currentTimeMillis());
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));

        // Hapus review sebagai admin (admin=true).
        reviewService.deleteReview(1, 101, true);

        // Assert
        verify(reviewRepository, times(1)).delete(review);
    }

    @Test
    public void testDeleteReviewFail() {
        // Generate review.
        Review review = new Review(1, 100, 200, 5, "Great service", System.currentTimeMillis());
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));

        // Has to fail since not the actual user nor admin.
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reviewService.deleteReview(1, 101, false);
        });
        assertTrue(exception.getMessage().contains("User tidak memiliki hak untuk menghapus ulasan ini."));
        verify(reviewRepository, times(1)).findById(1);
        verify(reviewRepository, never()).delete(any());
    }

    @Test
    public void testGetAverageRatingForTechnician() {
        // Generate Reviews
        Review review1 = new Review(1, 100, 200, 4, "Good", System.currentTimeMillis());
        Review review2 = new Review(2, 101, 200, 5, "Excellent", System.currentTimeMillis());
        Review review3 = new Review(3, 102, 200, 3, "Average", System.currentTimeMillis());
        List<Review> reviewsList = Arrays.asList(review1, review2, review3);
        when(reviewRepository.findByTechnicianId(200)).thenReturn(reviewsList);

        // Act
        double avg = reviewService.getAverageRatingForTechnician(200);

        // Assert
        double expectedAverage = (4 + 5 + 3) / 3.0;
        assertEquals(expectedAverage, avg);
        verify(reviewRepository, times(1)).findByTechnicianId(200);
    }
}
