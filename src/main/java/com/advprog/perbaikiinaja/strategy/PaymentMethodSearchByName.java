package com.advprog.perbaikiinaja.strategy;

import com.advprog.perbaikiinaja.model.PaymentMethod;
import com.advprog.perbaikiinaja.service.PaymentMethodService;

public class PaymentMethodSearchByName implements SearchStrategy<PaymentMethod> {
    private final PaymentMethodService service;
    private final String name;

    public PaymentMethodSearchByName(PaymentMethodService service, String name) {
        this.service = service;
        this.name = name;
    }

    @Override
    public PaymentMethod search() {
        return service.getPaymentMethodByName(name);
    }
}
