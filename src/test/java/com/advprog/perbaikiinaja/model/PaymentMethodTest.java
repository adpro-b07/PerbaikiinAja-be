package com.advprog.perbaikiinaja.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentMethodTest {

    @Test
    public void testConstructor() {
        PaymentMethod method = new PaymentMethod("PM001", "Bank A");

        assertEquals("PM001", method.getId());
        assertEquals("Bank A", method.getName());
    }

    @Test
    public void testBuilder() {
        PaymentMethod method = PaymentMethod.builder()
                .id("PM002")
                .name("Bank B")
                .build();

        assertEquals("PM002", method.getId());
        assertEquals("Bank B", method.getName());
    }
}
