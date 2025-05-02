package com.advprog.perbaikiinaja.repository;

import com.advprog.perbaikiinaja.model.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReviewRepositoryTest {

    @Mock
    private ReviewRepository reviewRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveAndFindById() {
        // reviews
        Review review = new Review(1, 100, 200, 5, "Great service!", System.currentTimeMillis());

        // save and findbyid
        when(reviewRepository.save(review)).thenReturn(review);
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));

        // save and find
        Review savedReview = reviewRepository.save(review);
        Optional<Review> foundReview = reviewRepository.findById(1);

        // verify
        assertNotNull(savedReview);
        assertTrue(foundReview.isPresent());
        assertEquals(1, foundReview.get().getReviewId());
        assertEquals("Great service!", foundReview.get().getReviewText());
    }

    @Test
    public void testDeleteById() {
        // review
        Review review = new Review(1, 100, 200, 5, "Great service!", System.currentTimeMillis());

        // findbyid and delete
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));
        doNothing().when(reviewRepository).deleteById(1);

        // delete
        reviewRepository.deleteById(1);

        // verify
        verify(reviewRepository, times(1)).deleteById(1);
    }
}