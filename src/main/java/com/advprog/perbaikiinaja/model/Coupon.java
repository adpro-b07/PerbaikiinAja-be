package com.advprog.perbaikiinaja.model;

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

    public String getCode() {
        return code;
    }

    public double getDiscount() {
        return discount;
    }

    public int getMaxUsage() {
        return maxUsage;
    }
}
