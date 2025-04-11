package com.advprog.perbaikiinaja.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PaymentMethod {
    String id;
    String name;
    String userId; // Foreign key to User
    String accountNumber;
    boolean isActive;

    public PaymentMethod(String id, String name, String userId, String accountNumber, boolean isActive) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.accountNumber = accountNumber;
        this.isActive = isActive;
    }

}
