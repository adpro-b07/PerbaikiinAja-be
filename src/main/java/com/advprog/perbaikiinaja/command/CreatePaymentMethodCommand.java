package com.advprog.perbaikiinaja.command;

import com.advprog.perbaikiinaja.model.PaymentMethod;
import com.advprog.perbaikiinaja.service.PaymentMethodService;

public class CreatePaymentMethodCommand implements Command<PaymentMethod> {

    private PaymentMethodService paymentMethodService;
    private String name;

    public CreatePaymentMethodCommand(PaymentMethodService service, String name) {
        this.paymentMethodService = service;
        this.name = name;
    }

    @Override
    public PaymentMethod execute() {
        return paymentMethodService.createPaymentMethod(name);
    }
}   