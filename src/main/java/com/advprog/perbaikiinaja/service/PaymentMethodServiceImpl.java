package com.advprog.perbaikiinaja.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.advprog.perbaikiinaja.model.PaymentMethod;
import com.advprog.perbaikiinaja.repository.PaymentMethodRepository;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Override
    public PaymentMethod createPaymentMethod(String name, String accountNumber, String userId) {
        // Generate a new UUID for the payment method
        String id = UUID.randomUUID().toString();
        
        // Create new payment method with active status by default
        PaymentMethod paymentMethod = new PaymentMethod(id, name, userId, accountNumber, true);
        
        // Save and return the created payment method
        return paymentMethodRepository.save(paymentMethod);
    }

    @Override
    public List<PaymentMethod> getAllPaymentMethodsByUserId(String userId) {
        // Get all payment methods and filter by userId
        return paymentMethodRepository.findAll().stream()
                .filter(paymentMethod -> paymentMethod.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public PaymentMethod getPaymentMethodById(String id) {
        // Find payment method by ID or throw exception if not found
        return paymentMethodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment method not found with ID: " + id));
    }

    @Override
    public PaymentMethod updatePaymentMethod(String id, String name, String accountNumber) {
        // Find existing payment method
        PaymentMethod existingPaymentMethod = getPaymentMethodById(id);
        
        // Create updated payment method with the same ID and user ID
        PaymentMethod updatedPaymentMethod = new PaymentMethod(
                id,
                name,
                existingPaymentMethod.getUserId(),
                accountNumber,
                existingPaymentMethod.isActive()
        );
        
        // Save and return the updated payment method
        return paymentMethodRepository.save(updatedPaymentMethod);
    }

    @Override
    public void deletePaymentMethod(String id) {
        // Check if payment method exists
        if (!paymentMethodRepository.existsById(id)) {
            throw new RuntimeException("Payment method not found with ID: " + id);
        }
        
        // Delete payment method by ID
        paymentMethodRepository.deleteById(id);
    }

    @Override
    public void activatePaymentMethod(String id) {
        // Find existing payment method
        PaymentMethod existingPaymentMethod = getPaymentMethodById(id);
        
        // Create updated payment method with active status
        PaymentMethod updatedPaymentMethod = new PaymentMethod(
                existingPaymentMethod.getId(),
                existingPaymentMethod.getName(),
                existingPaymentMethod.getUserId(),
                existingPaymentMethod.getAccountNumber(),
                true  // Set active to true
        );
        
        // Save the updated payment method
        paymentMethodRepository.save(updatedPaymentMethod);
    }

    @Override
    public void deactivatePaymentMethod(String id) {
        // Find existing payment method
        PaymentMethod existingPaymentMethod = getPaymentMethodById(id);
        
        // Create updated payment method with inactive status
        PaymentMethod updatedPaymentMethod = new PaymentMethod(
                existingPaymentMethod.getId(),
                existingPaymentMethod.getName(),
                existingPaymentMethod.getUserId(),
                existingPaymentMethod.getAccountNumber(),
                false  // Set active to false
        );
        
        // Save the updated payment method
        paymentMethodRepository.save(updatedPaymentMethod);
    }
}