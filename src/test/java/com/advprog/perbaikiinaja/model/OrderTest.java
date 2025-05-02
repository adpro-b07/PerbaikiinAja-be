package com.advprog.perbaikiinaja.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {
    @Test
    void defaultOrderStatusShouldBeMenungguEstimasi() {
        Order order = new Order();
        assertEquals(OrderStatus.MENUNGGU_ESTIMASI, order.getStatus());
    }

}
