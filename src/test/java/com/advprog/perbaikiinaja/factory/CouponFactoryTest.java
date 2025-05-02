package com.advprog.perbaikiinaja.factory;

import com.advprog.perbaikiinaja.model.Coupon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CouponFactoryTest {

    @Test
    public void testCreateCouponWithValidInput() {
        Coupon coupon = CouponFactory.createCoupon(20.0, 30);

        assertNotNull(coupon);
        assertEquals(20.0, coupon.getDiscount());
        assertEquals(30, coupon.getMaxUsage());
        assertNotNull(coupon.getCode());
        assertEquals(8, coupon.getCode().length());
    }

    @Test
    public void testCreateCouponWithInvalidDiscount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CouponFactory.createCoupon(0, 10); // diskon tidak valid
        });

        assertEquals("Diskon harus antara 1 dan 100", exception.getMessage());
    }

    @Test
    public void testCreateCouponWithInvalidUsage() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CouponFactory.createCoupon(20, 0); // penggunaan tidak valid
        });

        assertEquals("Pemakaian maksimal harus lebih dari 0", exception.getMessage());
    }
}
