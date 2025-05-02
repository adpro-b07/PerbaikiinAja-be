package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.model.Review;
import com.advprog.perbaikiinaja.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    public void testAddReview() {
        Review review = new Review(1, 100, 200, 5, "Great service!", System.currentTimeMillis());
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        Review savedReview = reviewService.addReview(review);

        assertNotNull(savedReview);
        assertEquals(1, savedReview.getReviewId());
        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    public void testGetReviewsForTechnician() {
        Review review1 = new Review(1, 100, 200, 5, "Great service!", System.currentTimeMillis());
        Review review2 = new Review(2, 101, 200, 4, "Good service", System.currentTimeMillis());
        when(reviewRepository.findByTechnicianId(200)).thenReturn(Arrays.asList(review1, review2));

        List<Review> reviews = reviewService.getReviewsForTechnician(200);

        assertEquals(2, reviews.size());
        verify(reviewRepository, times(1)).findByTechnicianId(200);
    }

    @Test
    public void testUpdateReview() {
        Review existingReview = new Review(1, 100, 200, 4, "Good service", System.currentTimeMillis());
        when(reviewRepository.findById(1)).thenReturn(Optional.of(existingReview));
        when(reviewRepository.save(any(Review.class))).thenReturn(existingReview);

        Review updatedReview = new Review(1, 100, 200, 5, "Excellent service", System.currentTimeMillis());
        Review result = reviewService.updateReview(updatedReview, 100);

        assertEquals(5, result.getRating());
        assertEquals("Excellent service", result.getReviewText());
        verify(reviewRepository, times(1)).findById(1);
        verify(reviewRepository, times(1)).save(existingReview);
    }

    @Test
    public void testDeleteReview() {
        Review review = new Review(1, 100, 200, 5, "Great service", System.currentTimeMillis());
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));

        reviewService.deleteReview(1, 100, false);

        verify(reviewRepository, times(1)).delete(review);
    }
}