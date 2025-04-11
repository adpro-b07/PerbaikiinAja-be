package com.advprog.perbaikiinaja.repository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.advprog.perbaikiinaja.model.PaymentMethod;

public class PaymentMethodRepositoryTest {
    private PaymentMethodRepository paymentMethodRepository;
    private PaymentMethod paymentMethod1;
    private PaymentMethod paymentMethod2;
    private PaymentMethod paymentMethod3;
    private String userId1;
    private String userId2;

    @BeforeEach
    void setUp() {
        paymentMethodRepository = new PaymentMethodRepository();

        userId1 = "user-123";
        userId2 = "user-456";

        // Create test payment methods
        paymentMethod1 = new PaymentMethod("payment-1", "BCA", userId1, "1234567890", true);
        paymentMethod2 = new PaymentMethod("payment-2", "Mandiri", userId1, "0987654321", true);
        paymentMethod3 = new PaymentMethod("payment-3", "BNI", userId2, "1122334455", false);
    }

    @Test
    void testSaveCreate() {
        // Save a new payment method
        PaymentMethod result = paymentMethodRepository.save(paymentMethod1);
        
        // Verify the saved payment method
        assertEquals(paymentMethod1.getId(), result.getId());
        assertEquals(paymentMethod1.getName(), result.getName());
        assertEquals(paymentMethod1.getUserId(), result.getUserId());
        assertEquals(paymentMethod1.getAccountNumber(), result.getAccountNumber());
        assertEquals(paymentMethod1.isActive(), result.isActive());
        
        // Verify the payment method can be retrieved
        Optional<PaymentMethod> findResult = paymentMethodRepository.findById(paymentMethod1.getId());
        assertTrue(findResult.isPresent());
        assertEquals(paymentMethod1.getId(), findResult.get().getId());
        assertEquals(paymentMethod1.getName(), findResult.get().getName());
        assertEquals("1234567890", findResult.get().getAccountNumber());
    }

    @Test
    void testSaveWithNullId() {
        // Create payment method with null ID
        PaymentMethod paymentMethodWithNullId = new PaymentMethod(null, "CIMB", userId1, "9988776655", true);
        
        // Save it
        PaymentMethod result = paymentMethodRepository.save(paymentMethodWithNullId);
        
        // Verify ID was generated
        assertNotNull(result.getId());
        assertEquals("CIMB", result.getName());
        assertEquals(userId1, result.getUserId());
        assertEquals("9988776655", result.getAccountNumber());
        assertTrue(result.isActive());
    }

    @Test
    void testSaveUpdate() {
        // First save the payment method
        paymentMethodRepository.save(paymentMethod1);
        
        // Create an updated version with the same ID
        PaymentMethod updatedPaymentMethod = new PaymentMethod(
                paymentMethod1.getId(),
                "BCA Updated",
                paymentMethod1.getUserId(),
                "9999999999",
                paymentMethod1.isActive()
        );
        
        // Save the update
        PaymentMethod result = paymentMethodRepository.save(updatedPaymentMethod);
        
        // Verify the updated payment method
        assertEquals(paymentMethod1.getId(), result.getId());
        assertEquals("BCA Updated", result.getName());
        assertEquals("9999999999", result.getAccountNumber());
        
        // Verify the update was persisted
        Optional<PaymentMethod> findResult = paymentMethodRepository.findById(paymentMethod1.getId());
        assertTrue(findResult.isPresent());
        assertEquals("BCA Updated", findResult.get().getName());
        assertEquals("9999999999", findResult.get().getAccountNumber());
    }

    @Test
    void testFindByIdIfIdFound() {
        // Save all payment methods
        for (PaymentMethod paymentMethod : List.of(paymentMethod1, paymentMethod2, paymentMethod3)) {
            paymentMethodRepository.save(paymentMethod);
        }
        
        // Find one by ID
        Optional<PaymentMethod> findResult = paymentMethodRepository.findById(paymentMethod2.getId());
        
        // Verify it was found with correct data
        assertTrue(findResult.isPresent());
        assertEquals(paymentMethod2.getId(), findResult.get().getId());
        assertEquals(paymentMethod2.getName(), findResult.get().getName());
        assertEquals(paymentMethod2.getUserId(), findResult.get().getUserId());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        // Save all payment methods
        for (PaymentMethod paymentMethod : List.of(paymentMethod1, paymentMethod2)) {
            paymentMethodRepository.save(paymentMethod);
        }
        
        // Try to find a non-existent ID
        Optional<PaymentMethod> findResult = paymentMethodRepository.findById("non-existent-id");
        
        // Verify it was not found
        assertFalse(findResult.isPresent());
    }

    @Test
    void testFindAll() {
        // Initially, repository should be empty
        assertTrue(paymentMethodRepository.findAll().isEmpty());
        
        // Save all payment methods
        paymentMethodRepository.save(paymentMethod1);
        paymentMethodRepository.save(paymentMethod2);
        paymentMethodRepository.save(paymentMethod3);
        
        // Verify all were returned
        List<PaymentMethod> result = paymentMethodRepository.findAll();
        assertEquals(3, result.size());
        
        // Verify the content
        assertTrue(result.stream().anyMatch(pm -> pm.getId().equals(paymentMethod1.getId())));
        assertTrue(result.stream().anyMatch(pm -> pm.getId().equals(paymentMethod2.getId())));
        assertTrue(result.stream().anyMatch(pm -> pm.getId().equals(paymentMethod3.getId())));
    }

    @Test
    void testDeleteById() {
        // Save all payment methods
        paymentMethodRepository.save(paymentMethod1);
        paymentMethodRepository.save(paymentMethod2);
        
        // Verify they exist
        assertTrue(paymentMethodRepository.existsById(paymentMethod1.getId()));
        assertTrue(paymentMethodRepository.existsById(paymentMethod2.getId()));
        
        // Delete one
        paymentMethodRepository.deleteById(paymentMethod1.getId());
        
        // Verify it's gone but the other still exists
        assertFalse(paymentMethodRepository.existsById(paymentMethod1.getId()));
        assertTrue(paymentMethodRepository.existsById(paymentMethod2.getId()));
    }

    @Test
    void testExistsById() {
        // Initially, should be false
        assertFalse(paymentMethodRepository.existsById("any-id"));
        
        // Save and check
        paymentMethodRepository.save(paymentMethod1);
        assertTrue(paymentMethodRepository.existsById(paymentMethod1.getId()));
        assertFalse(paymentMethodRepository.existsById("non-existent-id"));
    }

    @Test
    void testFindByUserId() {
        // Save all payment methods
        paymentMethodRepository.save(paymentMethod1);
        paymentMethodRepository.save(paymentMethod2);
        paymentMethodRepository.save(paymentMethod3);
        
        // Find by userId1
        List<PaymentMethod> user1Results = paymentMethodRepository.findByUserId(userId1);
        assertEquals(2, user1Results.size());
        assertTrue(user1Results.stream().anyMatch(pm -> pm.getId().equals(paymentMethod1.getId())));
        assertTrue(user1Results.stream().anyMatch(pm -> pm.getId().equals(paymentMethod2.getId())));
        
        // Find by userId2
        List<PaymentMethod> user2Results = paymentMethodRepository.findByUserId(userId2);
        assertEquals(1, user2Results.size());
        assertTrue(user2Results.stream().anyMatch(pm -> pm.getId().equals(paymentMethod3.getId())));
    }

    @Test
    void testFindByUserIdNonExistent() {
        // Save all payment methods
        paymentMethodRepository.save(paymentMethod1);
        paymentMethodRepository.save(paymentMethod2);
        
        // Find by non-existent userId
        List<PaymentMethod> results = paymentMethodRepository.findByUserId("non-existent-user");
        assertTrue(results.isEmpty());
    }
}