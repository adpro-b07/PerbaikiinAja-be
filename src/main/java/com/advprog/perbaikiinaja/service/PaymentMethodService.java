package com.advprog.perbaikiinaja.service;

import java.util.List;

import com.advprog.perbaikiinaja.model.PaymentMethod;

public interface PaymentMethodService {
    PaymentMethod createPaymentMethod(String name, String accountNumber, String userId);
    List<PaymentMethod> getAllPaymentMethodsByUserId(String userId);
    PaymentMethod getPaymentMethodById(String id);
    PaymentMethod updatePaymentMethod(String id, String name, String accountNumber);
    void deletePaymentMethod(String id);
    void activatePaymentMethod(String id);
    void deactivatePaymentMethod(String id);
}