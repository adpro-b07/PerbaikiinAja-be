package com.advprog.perbaikiinaja.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.advprog.perbaikiinaja.model.PaymentMethod;
import com.advprog.perbaikiinaja.repository.PaymentMethodRepository;

@ExtendWith(MockitoExtension.class)
public class PaymentMethodServiceImplTest {

    @Mock
    private PaymentMethodRepository paymentMethodRepository;

    @InjectMocks
    private PaymentMethodServiceImpl paymentMethodService;

    private PaymentMethod paymentMethod;
    private String userId;
    private String paymentMethodId;

    @BeforeEach
    void setUp() {
        userId = "user-123";
        paymentMethodId = UUID.randomUUID().toString();
        paymentMethod = new PaymentMethod(
                paymentMethodId,
                "BCA",
                userId,
                "1234567890",
                true
        );
    }

    @Test
    void testCreatePaymentMethod() {
        // Arrange
        String name = "BCA";
        String accountNumber = "1234567890";
        
        when(paymentMethodRepository.save(any(PaymentMethod.class))).thenReturn(paymentMethod);

        // Act
        PaymentMethod created = paymentMethodService.createPaymentMethod(name, accountNumber, userId);

        // Assert
        assertNotNull(created);
        assertEquals(name, created.getName());
        assertEquals(accountNumber, created.getAccountNumber());
        assertEquals(userId, created.getUserId());
        assertTrue(created.isActive());
        verify(paymentMethodRepository).save(any(PaymentMethod.class));
    }

    @Test
    void testGetAllPaymentMethodsByUserId() {
        // Arrange
        List<PaymentMethod> paymentMethods = new ArrayList<>();
        paymentMethods.add(paymentMethod);
        
        PaymentMethod anotherUserPaymentMethod = new PaymentMethod(
                UUID.randomUUID().toString(),
                "Mandiri",
                "another-user",
                "0987654321",
                true
        );
        
        List<PaymentMethod> allPaymentMethods = new ArrayList<>(paymentMethods);
        allPaymentMethods.add(anotherUserPaymentMethod);
        
        when(paymentMethodRepository.findAll()).thenReturn(allPaymentMethods);

        // Act
        List<PaymentMethod> result = paymentMethodService.getAllPaymentMethodsByUserId(userId);

        // Assert
        assertEquals(1, result.size());
        assertEquals(paymentMethod, result.get(0));
        verify(paymentMethodRepository).findAll();
    }

    @Test
    void testGetPaymentMethodById_Found() {
        // Arrange
        when(paymentMethodRepository.findById(paymentMethodId)).thenReturn(Optional.of(paymentMethod));

        // Act
        PaymentMethod result = paymentMethodService.getPaymentMethodById(paymentMethodId);

        // Assert
        assertNotNull(result);
        assertEquals(paymentMethodId, result.getId());
        verify(paymentMethodRepository).findById(paymentMethodId);
    }

    @Test
    void testGetPaymentMethodById_NotFound() {
        // Arrange
        String nonExistentId = "non-existent-id";
        when(paymentMethodRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            paymentMethodService.getPaymentMethodById(nonExistentId);
        });
        
        assertEquals("Payment method not found with ID: " + nonExistentId, exception.getMessage());
        verify(paymentMethodRepository).findById(nonExistentId);
    }

    @Test
    void testUpdatePaymentMethod() {
        // Arrange
        String newName = "BNI";
        String newAccountNumber = "5555555555";
        
        PaymentMethod updatedPaymentMethod = new PaymentMethod(
                paymentMethodId,
                newName,
                userId,
                newAccountNumber,
                true
        );
        
        when(paymentMethodRepository.findById(paymentMethodId)).thenReturn(Optional.of(paymentMethod));
        when(paymentMethodRepository.save(any(PaymentMethod.class))).thenReturn(updatedPaymentMethod);

        // Act
        PaymentMethod result = paymentMethodService.updatePaymentMethod(paymentMethodId, newName, newAccountNumber);

        // Assert
        assertNotNull(result);
        assertEquals(paymentMethodId, result.getId());
        assertEquals(newName, result.getName());
        assertEquals(newAccountNumber, result.getAccountNumber());
        assertEquals(userId, result.getUserId());
        verify(paymentMethodRepository).findById(paymentMethodId);
        verify(paymentMethodRepository).save(any(PaymentMethod.class));
    }

    @Test
    void testDeletePaymentMethod_Exists() {
        // Arrange
        when(paymentMethodRepository.existsById(paymentMethodId)).thenReturn(true);
        doNothing().when(paymentMethodRepository).deleteById(paymentMethodId);

        // Act
        paymentMethodService.deletePaymentMethod(paymentMethodId);

        // Assert
        verify(paymentMethodRepository).existsById(paymentMethodId);
        verify(paymentMethodRepository).deleteById(paymentMethodId);
    }

    @Test
    void testDeletePaymentMethod_NotExists() {
        // Arrange
        String nonExistentId = "non-existent-id";
        when(paymentMethodRepository.existsById(nonExistentId)).thenReturn(false);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            paymentMethodService.deletePaymentMethod(nonExistentId);
        });
        
        assertEquals("Payment method not found with ID: " + nonExistentId, exception.getMessage());
        verify(paymentMethodRepository).existsById(nonExistentId);
        verify(paymentMethodRepository, never()).deleteById(anyString());
    }

    @Test
    void testActivatePaymentMethod() {
        // Arrange
        PaymentMethod inactivePaymentMethod = new PaymentMethod(
                paymentMethodId,
                "BCA",
                userId,
                "1234567890",
                false
        );
        
        PaymentMethod activatedPaymentMethod = new PaymentMethod(
                paymentMethodId,
                "BCA",
                userId,
                "1234567890",
                true
        );
        
        when(paymentMethodRepository.findById(paymentMethodId)).thenReturn(Optional.of(inactivePaymentMethod));
        when(paymentMethodRepository.save(any(PaymentMethod.class))).thenReturn(activatedPaymentMethod);

        // Act
        paymentMethodService.activatePaymentMethod(paymentMethodId);

        // Assert
        verify(paymentMethodRepository).findById(paymentMethodId);
        verify(paymentMethodRepository).save(any(PaymentMethod.class));
    }

    @Test
    void testDeactivatePaymentMethod() {
        // Arrange
        PaymentMethod activePaymentMethod = new PaymentMethod(
                paymentMethodId,
                "BCA",
                userId,
                "1234567890",
                true
        );
        
        PaymentMethod deactivatedPaymentMethod = new PaymentMethod(
                paymentMethodId,
                "BCA",
                userId,
                "1234567890",
                false
        );
        
        when(paymentMethodRepository.findById(paymentMethodId)).thenReturn(Optional.of(activePaymentMethod));
        when(paymentMethodRepository.save(any(PaymentMethod.class))).thenReturn(deactivatedPaymentMethod);

        // Act
        paymentMethodService.deactivatePaymentMethod(paymentMethodId);

        // Assert
        verify(paymentMethodRepository).findById(paymentMethodId);
        verify(paymentMethodRepository).save(any(PaymentMethod.class));
    }
}