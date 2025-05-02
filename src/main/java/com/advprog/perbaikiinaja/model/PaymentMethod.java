package com.advprog.perbaikiinaja.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PaymentMethod {
    String id;
    String name;

    public PaymentMethod(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
