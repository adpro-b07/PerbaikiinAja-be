package com.advprog.perbaikiinaja.model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CouponTest {
    @Test
    public void testCouponCreation() {
        Coupon coupon = new Coupon("TESTCODE", 25.0, 10);

        assertEquals("TESTCODE", coupon.getCode());
        assertEquals(25.0, coupon.getDiscount());
        assertEquals(10, coupon.getMaxUsage());
    }

    @Test
    public void testNegativeDiscountThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Coupon("NEGTEST", -10.0, 5);
        });

        assertEquals("Diskon harus lebih dari 0", exception.getMessage());
    }

    @Test
    public void testZeroMaxUsageThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Coupon("USAGETEST", 20.0, 0);
        });

        assertEquals("Pemakaian maksimal harus lebih dari 0", exception.getMessage());
    }
}
