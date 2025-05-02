package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.model.PaymentMethod;

public interface PaymentMethodService {
    PaymentMethod createPaymentMethod(String name);
    PaymentMethod getPaymentMethodById(String id);
    PaymentMethod updatePaymentMethod(String id, String name);
    PaymentMethod getPaymentMethodByName(String name);
    void deletePaymentMethod(String name);
    Iterable<PaymentMethod> getAllPaymentMethods();
}