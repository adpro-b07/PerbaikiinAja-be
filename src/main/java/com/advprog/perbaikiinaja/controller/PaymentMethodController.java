package com.advprog.perbaikiinaja.controller;

import com.advprog.perbaikiinaja.model.PaymentMethod;
import com.advprog.perbaikiinaja.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/payment-methods")
public class PaymentMethodController {
    
    private final PaymentMethodService paymentMethodService;
    
    @Autowired
    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }
    
    @PostMapping
    public ResponseEntity<PaymentMethod> createPaymentMethod(
            @RequestBody Map<String, String> payload) {
        
        String name = payload.get("name");
        String accountNumber = payload.get("accountNumber");
        String userId = payload.get("userId");
        
        if (name == null || name.isEmpty() || accountNumber == null || accountNumber.isEmpty() 
                || userId == null || userId.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        PaymentMethod paymentMethod = paymentMethodService.createPaymentMethod(name, accountNumber, userId);
        return new ResponseEntity<>(paymentMethod, HttpStatus.CREATED);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentMethod>> getAllPaymentMethodsByUserId(
            @PathVariable String userId) {
        
        List<PaymentMethod> paymentMethods = paymentMethodService.getAllPaymentMethodsByUserId(userId);
        return new ResponseEntity<>(paymentMethods, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethod> getPaymentMethodById(
            @PathVariable String id) {
        
        try {
            PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(id);
            return new ResponseEntity<>(paymentMethod, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PaymentMethod> updatePaymentMethod(
            @PathVariable String id,
            @RequestBody Map<String, String> payload) {
        
        String name = payload.get("name");
        String accountNumber = payload.get("accountNumber");
        
        if (name == null || name.isEmpty() || accountNumber == null || accountNumber.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        try {
            PaymentMethod updatedPaymentMethod = 
                paymentMethodService.updatePaymentMethod(id, name, accountNumber);
            return new ResponseEntity<>(updatedPaymentMethod, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentMethod(
            @PathVariable String id) {
        
        try {
            paymentMethodService.deletePaymentMethod(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{id}/activate")
    public ResponseEntity<Map<String, String>> activatePaymentMethod(
            @PathVariable String id) {
        
        try {
            paymentMethodService.activatePaymentMethod(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Payment method activated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Map<String, String>> deactivatePaymentMethod(
            @PathVariable String id) {
        
        try {
            paymentMethodService.deactivatePaymentMethod(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Payment method deactivated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}