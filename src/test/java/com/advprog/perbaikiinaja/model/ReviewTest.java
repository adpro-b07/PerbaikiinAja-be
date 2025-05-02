package com.advprog.perbaikiinaja.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewTest {

    @Test
    public void testReviewGettersAndSetters() {
        // review object
        Review review = new Review(1, 100, 200, 5, "Great service!", System.currentTimeMillis());

        // verif getter
        assertEquals(1, review.getReviewId());
        assertEquals(100, review.getUserId());
        assertEquals(200, review.getTechnicianId());
        assertEquals(5, review.getRating());
        assertEquals("Great service!", review.getReviewText());

        // update values
        review.setRating(4);
        review.setReviewText("Good service");

        // verify
        assertEquals(4, review.getRating());
        assertEquals("Good service", review.getReviewText());
    }
}