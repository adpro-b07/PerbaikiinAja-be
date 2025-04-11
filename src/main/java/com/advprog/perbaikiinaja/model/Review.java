package com.advprog.perbaikiinaja.model;

public class Review {
    private int reviewId;
    private int userId;
    private int technicianId;
    private int rating;       // 1-5
    private String reviewText;
    private long timestamp;

    public Review(int reviewId, int userId, int technicianId, int rating, String reviewText, long timestamp) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.technicianId = technicianId;
        this.rating = rating;
        this.reviewText = reviewText;
        this.timestamp = timestamp;
    }

    // getters
    public int getReviewId() {
        return reviewId;
    }

    public int getUserId() {
        return userId;
    }

    public int getTechnicianId() {
        return technicianId;
    }

    public int getRating() {
        return rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public long getTimestamp() {
        return timestamp;
    }

    // setters untuk update
    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}
