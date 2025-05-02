package com.advprog.perbaikiinaja.controller;

import com.advprog.perbaikiinaja.model.Review;
import com.advprog.perbaikiinaja.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @Test
    public void testAddReview() throws Exception {
        Review review = new Review(1, 100, 200, 5, "Great service!", System.currentTimeMillis());
        when(reviewService.addReview(any(Review.class))).thenReturn(review);

        mockMvc.perform(post("/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"reviewId\":1,\"userId\":100,\"technicianId\":200,\"rating\":5,\"reviewText\":\"Great service!\",\"timestamp\":1234567890}"))
                .andExpect(status().isCreated()) // Change to isCreated() to expect 201
                .andExpect(jsonPath("$.reviewId").value(1))
                .andExpect(jsonPath("$.userId").value(100))
                .andExpect(jsonPath("$.technicianId").value(200))
                .andExpect(jsonPath("$.rating").value(5))
                .andExpect(jsonPath("$.reviewText").value("Great service!"));
    }

    @Test
    public void testGetReviewsForTechnician() throws Exception {
        Review review1 = new Review(1, 100, 200, 5, "Great service!", System.currentTimeMillis());
        Review review2 = new Review(2, 101, 200, 4, "Good service", System.currentTimeMillis());
        List<Review> reviews = Arrays.asList(review1, review2);
        when(reviewService.getReviewsForTechnician(200)).thenReturn(reviews);

        mockMvc.perform(get("/reviews/technician/200"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].reviewId").value(1))
                .andExpect(jsonPath("$[1].reviewId").value(2));
    }

    @Test
    public void testUpdateReview() throws Exception {
        Review updatedReview = new Review(1, 100, 200, 5, "Excellent service", System.currentTimeMillis());
        when(reviewService.updateReview(any(Review.class), Mockito.eq(100))).thenReturn(updatedReview);

        mockMvc.perform(put("/reviews/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"reviewId\":1,\"userId\":100,\"technicianId\":200,\"rating\":5,\"reviewText\":\"Excellent service\",\"timestamp\":1234567890}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reviewId").value(1))
                .andExpect(jsonPath("$.reviewText").value("Excellent service"));
    }

    @Test
    public void testDeleteReview() throws Exception {
        Mockito.doNothing().when(reviewService).deleteReview(1, 100, false);

        mockMvc.perform(delete("/reviews/1?currentUserId=100&isAdmin=false"))
                .andExpect(status().isOk());
    }
}