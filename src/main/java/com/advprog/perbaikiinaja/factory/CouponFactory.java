package com.advprog.perbaikiinaja.factory;

import com.advprog.perbaikiinaja.model.Coupon;

import java.util.UUID;

public class CouponFactory {

    public static Coupon createCoupon(double discount, int maxUsage) {
        // Validasi
        if (discount <= 0 || discount > 100) {
            throw new IllegalArgumentException("Diskon harus antara 1 dan 100");
        }

        if (maxUsage <= 0) {
            throw new IllegalArgumentException("Pemakaian maksimal harus lebih dari 0");
        }

        // Generate kode unik 8 karakter (uppercase)
        String code = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();

        // Buat objek Coupon
        return new Coupon(code, discount, maxUsage);
    }
}
