package com.advprog.perbaikiinaja.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PaymentMethodTest {
    private String id;
    private String userId;
    private String name;
    private String accountNumber;
    
    @BeforeEach
    void setUp() {
        this.id = "payment-123";
        this.userId = "user-456";
        this.name = "BCA";
        this.accountNumber = "1234567890";
    }
    
    @Test
    void testCreatePaymentMethod() {
        PaymentMethod paymentMethod = new PaymentMethod(id, name, userId, accountNumber, true);
        
        assertEquals(id, paymentMethod.getId());
        assertEquals(userId, paymentMethod.getUserId());
        assertEquals(name, paymentMethod.getName());
        assertEquals(accountNumber, paymentMethod.getAccountNumber());
        assertTrue(paymentMethod.isActive());
    }
    
    @Test
    void testCreatePaymentMethodWithBuilder() {
        PaymentMethod paymentMethod = PaymentMethod.builder()
                .id(id)
                .name(name)
                .userId(userId)
                .accountNumber(accountNumber)
                .isActive(true)
                .build();
        
        assertEquals(id, paymentMethod.getId());
        assertEquals(userId, paymentMethod.getUserId());
        assertEquals(name, paymentMethod.getName());
        assertEquals(accountNumber, paymentMethod.getAccountNumber());
        assertTrue(paymentMethod.isActive());
    }
    
    @Test
    void testCreateInactivePaymentMethod() {
        PaymentMethod paymentMethod = new PaymentMethod(id, name, userId, accountNumber, false);
        
        assertEquals(id, paymentMethod.getId());
        assertEquals(userId, paymentMethod.getUserId());
        assertEquals(name, paymentMethod.getName());
        assertEquals(accountNumber, paymentMethod.getAccountNumber());
        assertFalse(paymentMethod.isActive());
    }
    
    @Test
    void testEquality() {
        PaymentMethod paymentMethod1 = new PaymentMethod(id, name, userId, accountNumber, true);
        PaymentMethod paymentMethod2 = new PaymentMethod(id, name, userId, accountNumber, true);
        
        // Objects with same ID should be considered equal
        assertEquals(paymentMethod1.getId(), paymentMethod2.getId());
    }
    
    @Test
    void testDifferentIds() {
        PaymentMethod paymentMethod1 = new PaymentMethod(id, name, userId, accountNumber, true);
        PaymentMethod paymentMethod2 = new PaymentMethod("different-id", name, userId, accountNumber, true);
        
        // Objects with different IDs should not be equal
        assertNotEquals(paymentMethod1.getId(), paymentMethod2.getId());
    }
    
    @Test
    void testSameUserDifferentPaymentMethods() {
        PaymentMethod paymentMethod1 = new PaymentMethod("payment-1", "BCA", userId, "1234567890", true);
        PaymentMethod paymentMethod2 = new PaymentMethod("payment-2", "Mandiri", userId, "0987654321", true);
        
        // Same user can have different payment methods
        assertEquals(paymentMethod1.getUserId(), paymentMethod2.getUserId());
        assertNotEquals(paymentMethod1.getId(), paymentMethod2.getId());
        assertNotEquals(paymentMethod1.getName(), paymentMethod2.getName());
        assertNotEquals(paymentMethod1.getAccountNumber(), paymentMethod2.getAccountNumber());
    }
    
    @Test
    void testDifferentUsersSameDetails() {
        PaymentMethod paymentMethod1 = new PaymentMethod(id, name, "user-1", accountNumber, true);
        PaymentMethod paymentMethod2 = new PaymentMethod(id, name, "user-2", accountNumber, true);
        
        // Different users can have payment methods with same details
        assertNotEquals(paymentMethod1.getUserId(), paymentMethod2.getUserId());
        assertEquals(paymentMethod1.getName(), paymentMethod2.getName());
        assertEquals(paymentMethod1.getAccountNumber(), paymentMethod2.getAccountNumber());
    }
    
    @Test
    void testActiveAndInactivePaymentMethods() {
        PaymentMethod activePaymentMethod = new PaymentMethod(id, name, userId, accountNumber, true);
        PaymentMethod inactivePaymentMethod = new PaymentMethod(id, name, userId, accountNumber, false);
        
        assertTrue(activePaymentMethod.isActive());
        assertFalse(inactivePaymentMethod.isActive());
    }
}