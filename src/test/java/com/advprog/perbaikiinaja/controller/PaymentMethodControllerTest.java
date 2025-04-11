package com.advprog.perbaikiinaja.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.advprog.perbaikiinaja.model.PaymentMethod;
import com.advprog.perbaikiinaja.service.PaymentMethodService;

@ExtendWith(MockitoExtension.class)
public class PaymentMethodControllerTest {

    @Mock
    private PaymentMethodService paymentMethodService;

    @InjectMocks
    private PaymentMethodController paymentMethodController;

    private PaymentMethod paymentMethod;
    private String userId;
    private String paymentMethodId;
    private String name;
    private String accountNumber;

    @BeforeEach
    void setUp() {
        paymentMethodId = "payment-123";
        userId = "user-456";
        name = "BCA";
        accountNumber = "1234567890";

        paymentMethod = new PaymentMethod(paymentMethodId, name, userId, accountNumber, true);
    }

    @Test
    void testCreatePaymentMethod_Success() {
        // Arrange
        Map<String, String> payload = new HashMap<>();
        payload.put("name", name);
        payload.put("accountNumber", accountNumber);
        payload.put("userId", userId);

        when(paymentMethodService.createPaymentMethod(name, accountNumber, userId))
                .thenReturn(paymentMethod);

        // Act
        ResponseEntity<PaymentMethod> response = paymentMethodController.createPaymentMethod(payload);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(paymentMethodId, response.getBody().getId());
        assertEquals(userId, response.getBody().getUserId());
        assertEquals(name, response.getBody().getName());
        assertEquals(accountNumber, response.getBody().getAccountNumber());
        verify(paymentMethodService).createPaymentMethod(name, accountNumber, userId);
    }

    @Test
    void testCreatePaymentMethod_MissingFields() {
        // Arrange - Missing name
        Map<String, String> payloadMissingName = new HashMap<>();
        payloadMissingName.put("accountNumber", accountNumber);
        payloadMissingName.put("userId", userId);

        // Act
        ResponseEntity<PaymentMethod> responseMissingName = paymentMethodController.createPaymentMethod(payloadMissingName);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseMissingName.getStatusCode());
        assertNull(responseMissingName.getBody());

        // Arrange - Missing accountNumber
        Map<String, String> payloadMissingAccount = new HashMap<>();
        payloadMissingAccount.put("name", name);
        payloadMissingAccount.put("userId", userId);

        // Act
        ResponseEntity<PaymentMethod> responseMissingAccount = paymentMethodController.createPaymentMethod(payloadMissingAccount);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseMissingAccount.getStatusCode());
        assertNull(responseMissingAccount.getBody());

        // Arrange - Missing userId
        Map<String, String> payloadMissingUserId = new HashMap<>();
        payloadMissingUserId.put("name", name);
        payloadMissingUserId.put("accountNumber", accountNumber);

        // Act
        ResponseEntity<PaymentMethod> responseMissingUserId = paymentMethodController.createPaymentMethod(payloadMissingUserId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseMissingUserId.getStatusCode());
        assertNull(responseMissingUserId.getBody());

        // Verify service was never called
        verify(paymentMethodService, never()).createPaymentMethod(anyString(), anyString(), anyString());
    }

    @Test
    void testGetAllPaymentMethodsByUserId() {
        // Arrange
        List<PaymentMethod> paymentMethods = new ArrayList<>();
        paymentMethods.add(paymentMethod);
        
        when(paymentMethodService.getAllPaymentMethodsByUserId(userId))
                .thenReturn(paymentMethods);

        // Act
        ResponseEntity<List<PaymentMethod>> response = paymentMethodController.getAllPaymentMethodsByUserId(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(paymentMethodId, response.getBody().get(0).getId());
        verify(paymentMethodService).getAllPaymentMethodsByUserId(userId);
    }

    @Test
    void testGetPaymentMethodById_Success() {
        // Arrange
        when(paymentMethodService.getPaymentMethodById(paymentMethodId))
                .thenReturn(paymentMethod);

        // Act
        ResponseEntity<PaymentMethod> response = paymentMethodController.getPaymentMethodById(paymentMethodId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(paymentMethodId, response.getBody().getId());
        verify(paymentMethodService).getPaymentMethodById(paymentMethodId);
    }

    @Test
    void testGetPaymentMethodById_NotFound() {
        // Arrange
        when(paymentMethodService.getPaymentMethodById(paymentMethodId))
                .thenThrow(new RuntimeException("Payment method not found"));

        // Act
        ResponseEntity<PaymentMethod> response = paymentMethodController.getPaymentMethodById(paymentMethodId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(paymentMethodService).getPaymentMethodById(paymentMethodId);
    }

    @Test
    void testUpdatePaymentMethod_Success() {
        // Arrange
        String newName = "BNI";
        String newAccountNumber = "9876543210";
        
        Map<String, String> payload = new HashMap<>();
        payload.put("name", newName);
        payload.put("accountNumber", newAccountNumber);
        
        PaymentMethod updatedPaymentMethod = new PaymentMethod(
                paymentMethodId, newName, userId, newAccountNumber, true
        );
        
        when(paymentMethodService.updatePaymentMethod(paymentMethodId, newName, newAccountNumber))
                .thenReturn(updatedPaymentMethod);

        // Act
        ResponseEntity<PaymentMethod> response = paymentMethodController.updatePaymentMethod(paymentMethodId, payload);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(paymentMethodId, response.getBody().getId());
        assertEquals(newName, response.getBody().getName());
        assertEquals(newAccountNumber, response.getBody().getAccountNumber());
        verify(paymentMethodService).updatePaymentMethod(paymentMethodId, newName, newAccountNumber);
    }

    @Test
    void testUpdatePaymentMethod_MissingFields() {
        // Arrange - Missing name
        Map<String, String> payloadMissingName = new HashMap<>();
        payloadMissingName.put("accountNumber", "9876543210");

        // Act
        ResponseEntity<PaymentMethod> responseMissingName = 
                paymentMethodController.updatePaymentMethod(paymentMethodId, payloadMissingName);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseMissingName.getStatusCode());
        assertNull(responseMissingName.getBody());

        // Arrange - Missing accountNumber
        Map<String, String> payloadMissingAccount = new HashMap<>();
        payloadMissingAccount.put("name", "BNI");

        // Act
        ResponseEntity<PaymentMethod> responseMissingAccount = 
                paymentMethodController.updatePaymentMethod(paymentMethodId, payloadMissingAccount);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseMissingAccount.getStatusCode());
        assertNull(responseMissingAccount.getBody());

        // Verify service was never called
        verify(paymentMethodService, never()).updatePaymentMethod(anyString(), anyString(), anyString());
    }

    @Test
    void testUpdatePaymentMethod_NotFound() {
        // Arrange
        String newName = "BNI";
        String newAccountNumber = "9876543210";
        
        Map<String, String> payload = new HashMap<>();
        payload.put("name", newName);
        payload.put("accountNumber", newAccountNumber);
        
        when(paymentMethodService.updatePaymentMethod(paymentMethodId, newName, newAccountNumber))
                .thenThrow(new RuntimeException("Payment method not found"));

        // Act
        ResponseEntity<PaymentMethod> response = 
                paymentMethodController.updatePaymentMethod(paymentMethodId, payload);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(paymentMethodService).updatePaymentMethod(paymentMethodId, newName, newAccountNumber);
    }

    @Test
    void testDeletePaymentMethod_Success() {
        // Arrange
        doNothing().when(paymentMethodService).deletePaymentMethod(paymentMethodId);

        // Act
        ResponseEntity<Void> response = paymentMethodController.deletePaymentMethod(paymentMethodId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(paymentMethodService).deletePaymentMethod(paymentMethodId);
    }

    @Test
    void testDeletePaymentMethod_NotFound() {
        // Arrange
        doThrow(new RuntimeException("Payment method not found"))
                .when(paymentMethodService).deletePaymentMethod(paymentMethodId);

        // Act
        ResponseEntity<Void> response = paymentMethodController.deletePaymentMethod(paymentMethodId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(paymentMethodService).deletePaymentMethod(paymentMethodId);
    }

    @Test
    void testActivatePaymentMethod_Success() {
        // Arrange
        doNothing().when(paymentMethodService).activatePaymentMethod(paymentMethodId);

        // Act
        ResponseEntity<Map<String, String>> response = 
                paymentMethodController.activatePaymentMethod(paymentMethodId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Payment method activated successfully", response.getBody().get("message"));
        verify(paymentMethodService).activatePaymentMethod(paymentMethodId);
    }

    @Test
    void testActivatePaymentMethod_NotFound() {
        // Arrange
        doThrow(new RuntimeException("Payment method not found"))
                .when(paymentMethodService).activatePaymentMethod(paymentMethodId);

        // Act
        ResponseEntity<Map<String, String>> response = 
                paymentMethodController.activatePaymentMethod(paymentMethodId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(paymentMethodService).activatePaymentMethod(paymentMethodId);
    }

    @Test
    void testDeactivatePaymentMethod_Success() {
        // Arrange
        doNothing().when(paymentMethodService).deactivatePaymentMethod(paymentMethodId);

        // Act
        ResponseEntity<Map<String, String>> response = 
                paymentMethodController.deactivatePaymentMethod(paymentMethodId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Payment method deactivated successfully", response.getBody().get("message"));
        verify(paymentMethodService).deactivatePaymentMethod(paymentMethodId);
    }

    @Test
    void testDeactivatePaymentMethod_NotFound() {
        // Arrange
        doThrow(new RuntimeException("Payment method not found"))
                .when(paymentMethodService).deactivatePaymentMethod(paymentMethodId);

        // Act
        ResponseEntity<Map<String, String>> response = 
                paymentMethodController.deactivatePaymentMethod(paymentMethodId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(paymentMethodService).deactivatePaymentMethod(paymentMethodId);
    }
    
    @Test
    void testCreatePaymentMethod_NullName() {
        // Arrange - name is null
        Map<String, String> payload = new HashMap<>();
        payload.put("accountNumber", accountNumber);
        payload.put("userId", userId);
        // name is not added to payload

        // Act
        ResponseEntity<PaymentMethod> response = paymentMethodController.createPaymentMethod(payload);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(paymentMethodService, never()).createPaymentMethod(anyString(), anyString(), anyString());
    }

    @Test
    void testCreatePaymentMethod_EmptyName() {
        // Arrange - name is empty
        Map<String, String> payload = new HashMap<>();
        payload.put("name", "");
        payload.put("accountNumber", accountNumber);
        payload.put("userId", userId);

        // Act
        ResponseEntity<PaymentMethod> response = paymentMethodController.createPaymentMethod(payload);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(paymentMethodService, never()).createPaymentMethod(anyString(), anyString(), anyString());
    }

    @Test
    void testCreatePaymentMethod_NullAccountNumber() {
        // Arrange - accountNumber is null
        Map<String, String> payload = new HashMap<>();
        payload.put("name", name);
        payload.put("userId", userId);
        // accountNumber is not added to payload

        // Act
        ResponseEntity<PaymentMethod> response = paymentMethodController.createPaymentMethod(payload);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(paymentMethodService, never()).createPaymentMethod(anyString(), anyString(), anyString());
    }

    @Test
    void testCreatePaymentMethod_EmptyAccountNumber() {
        // Arrange - accountNumber is empty
        Map<String, String> payload = new HashMap<>();
        payload.put("name", name);
        payload.put("accountNumber", "");
        payload.put("userId", userId);

        // Act
        ResponseEntity<PaymentMethod> response = paymentMethodController.createPaymentMethod(payload);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(paymentMethodService, never()).createPaymentMethod(anyString(), anyString(), anyString());
    }

    @Test
    void testCreatePaymentMethod_NullUserId() {
        // Arrange - userId is null
        Map<String, String> payload = new HashMap<>();
        payload.put("name", name);
        payload.put("accountNumber", accountNumber);
        // userId is not added to payload

        // Act
        ResponseEntity<PaymentMethod> response = paymentMethodController.createPaymentMethod(payload);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(paymentMethodService, never()).createPaymentMethod(anyString(), anyString(), anyString());
    }

    @Test
    void testCreatePaymentMethod_EmptyUserId() {
        // Arrange - userId is empty
        Map<String, String> payload = new HashMap<>();
        payload.put("name", name);
        payload.put("accountNumber", accountNumber);
        payload.put("userId", "");

        // Act
        ResponseEntity<PaymentMethod> response = paymentMethodController.createPaymentMethod(payload);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(paymentMethodService, never()).createPaymentMethod(anyString(), anyString(), anyString());
    }

    // Test untuk updatePaymentMethod
    @Test
    void testUpdatePaymentMethod_NullName() {
        // Arrange - name is null
        Map<String, String> payload = new HashMap<>();
        payload.put("accountNumber", accountNumber);
        // name is not added to payload

        // Act
        ResponseEntity<PaymentMethod> response = paymentMethodController.updatePaymentMethod(paymentMethodId, payload);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(paymentMethodService, never()).updatePaymentMethod(anyString(), anyString(), anyString());
    }

    @Test
    void testUpdatePaymentMethod_EmptyName() {
        // Arrange - name is empty
        Map<String, String> payload = new HashMap<>();
        payload.put("name", "");
        payload.put("accountNumber", accountNumber);

        // Act
        ResponseEntity<PaymentMethod> response = paymentMethodController.updatePaymentMethod(paymentMethodId, payload);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(paymentMethodService, never()).updatePaymentMethod(anyString(), anyString(), anyString());
    }

    @Test
    void testUpdatePaymentMethod_NullAccountNumber() {
        // Arrange - accountNumber is null
        Map<String, String> payload = new HashMap<>();
        payload.put("name", name);
        // accountNumber is not added to payload

        // Act
        ResponseEntity<PaymentMethod> response = paymentMethodController.updatePaymentMethod(paymentMethodId, payload);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(paymentMethodService, never()).updatePaymentMethod(anyString(), anyString(), anyString());
    }

    @Test
    void testUpdatePaymentMethod_EmptyAccountNumber() {
        // Arrange - accountNumber is empty
        Map<String, String> payload = new HashMap<>();
        payload.put("name", name);
        payload.put("accountNumber", "");

        // Act
        ResponseEntity<PaymentMethod> response = paymentMethodController.updatePaymentMethod(paymentMethodId, payload);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(paymentMethodService, never()).updatePaymentMethod(anyString(), anyString(), anyString());
    }
}