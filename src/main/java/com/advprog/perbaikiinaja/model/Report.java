package com.advprog.perbaikiinaja.model;

import lombok.Data;

@Data
public class Report {
    private String orderId;
    private String technicianId;
    private String description;
    private Long finalPrice;
    private String photoUrl;
}
