package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getOrdersForTechnician(String technicianId);

    void updateEstimation(String orderId, int hours, Long price);
}

