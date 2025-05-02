package com.advprog.perbaikiinaja.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;

public class OrderTest {
    @Test
    void defaultOrderStatusShouldBeMenungguEstimasi() {
        Order order = new Order();
        assertEquals(OrderStatus.MENUNGGU_ESTIMASI, order.getStatus());
    }

    @Test
    void shouldSetAndGetRepairDescription() {
        Order order = new Order();
        order.setRepairDescription("Screen replacement");
        assertEquals("Screen replacement", order.getRepairDescription());
    }

    @Test
    void shouldSetAndGetServiceDate() {
        Order order = new Order();
        LocalDate date = LocalDate.of(2025, 5, 2);
        order.setServiceDate(date);
        assertEquals(date, order.getServiceDate());
    }

    @Test
    void couponShouldBeOptional() {
        Order order = new Order();
        assertNull(order.getCoupon());
        order.setCoupon("COUPON123");
        assertEquals("COUPON123", order.getCoupon());
    }

    @Test
    void shouldSetAndGetPaymentMethod() {
        Order order = new Order();
        PaymentMethod pm = PaymentMethod.builder()
                .id("pm1")
                .name("Credit Card")
                .userId("user1")
                .accountNumber("123456")
                .isActive(true)
                .build();
        order.setPaymentMethod(pm);
        assertEquals(pm, order.getPaymentMethod());
    }
}
