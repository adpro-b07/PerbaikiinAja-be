package com.advprog.perbaikiinaja.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Order {
    private String id;
    private String technicianId;
    private String customerId;
    private String itemName;
    private String itemCondition;
    private int estimatedHours;
    private long estimatedPrice;
    private OrderStatus status;
    
    private String repairDescription;
    private LocalDate serviceDate;
    private String coupon; // Optional coupon code
    private PaymentMethod paymentMethod;

    public Order() {
        this.status = OrderStatus.MENUNGGU_ESTIMASI;
    }
}

