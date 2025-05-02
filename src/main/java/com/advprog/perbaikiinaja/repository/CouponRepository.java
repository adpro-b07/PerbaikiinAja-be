package com.advprog.perbaikiinaja.repository;

import com.advprog.perbaikiinaja.model.Coupon;

import java.util.List;
import java.util.Optional;

public interface CouponRepository {
    Coupon save(Coupon coupon);
    Optional<Coupon> findByCode(String code);
    List<Coupon> findAll();
    void deleteByCode(String code);
}
