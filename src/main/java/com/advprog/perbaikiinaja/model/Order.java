package com.advprog.perbaikiinaja.model;

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
    private String paymentMethod;

    public Order() {
        this.status = OrderStatus.MENUNGGU_ESTIMASI;
    }
}

