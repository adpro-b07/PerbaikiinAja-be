package com.advprog.perbaikiinaja.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.advprog.perbaikiinaja.model.PaymentMethod;
import com.advprog.perbaikiinaja.repository.PaymentMethodRepository;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Override
    public PaymentMethod createPaymentMethod(String name) {
        if (paymentMethodRepository.existsByName(name)) {
            throw new RuntimeException("Payment method with name " + name + " already exists.");
        }
        if (name == null || name.isEmpty()) {
            throw new RuntimeException("Payment method name cannot be null or empty.");
        }
        
        PaymentMethod paymentMethod = new PaymentMethod(name);
        
        return paymentMethodRepository.save(paymentMethod);
    }

    @Override
    public PaymentMethod getPaymentMethodById(Long id) {
        return paymentMethodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment method not found with ID: " + id));
    }

    @Override
    public PaymentMethod getPaymentMethodByName(String name) {
        List<PaymentMethod> paymentMethods = paymentMethodRepository.findAll();
        for (PaymentMethod paymentMethod : paymentMethods) {
            if (paymentMethod.getName().equals(name)) {
                return paymentMethod;
            }
        }
        return null;
    }

    @Override
    public PaymentMethod updatePaymentMethod(String name, String newName) {
        PaymentMethod existingPaymentMethod = getPaymentMethodByName(name);
        if (existingPaymentMethod == null) {
            throw new RuntimeException("Payment method not found with name: " + name);
        }
        if (paymentMethodRepository.existsByName(newName)) {
            throw new RuntimeException("Payment method with Name " + newName + " already exists.");
        }

        existingPaymentMethod.setName(newName);

        return paymentMethodRepository.save(existingPaymentMethod);
    }

    @Override
    @Transactional
    public void deletePaymentMethod(String name) {
        if (!paymentMethodRepository.existsByName(name)) {
            throw new RuntimeException("Payment method not found with Name: " + name);
        }
        
        paymentMethodRepository.deleteByName(name);
    }

    @Override
    public Iterable<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodRepository.findAll();
    }
}