package com.advprog.perbaikiinaja.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentMethodTest {

    @Test
    public void testConstructor() {
        PaymentMethod method = new PaymentMethod("Bank A");

        assertEquals("Bank A", method.getName());
    }
}
