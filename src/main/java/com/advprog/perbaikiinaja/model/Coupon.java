package com.advprog.perbaikiinaja.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Coupon {
    private String code;
    private double discount;
    private int maxUsage;

    public Coupon(String code, double discount, int maxUsage) {
        if (discount <= 0) {
            throw new IllegalArgumentException("Diskon harus lebih dari 0");
        }
        if (maxUsage <= 0) {
            throw new IllegalArgumentException("Pemakaian maksimal harus lebih dari 0");
        }

        this.code = code;
        this.discount = discount;
        this.maxUsage = maxUsage;
    }
}
