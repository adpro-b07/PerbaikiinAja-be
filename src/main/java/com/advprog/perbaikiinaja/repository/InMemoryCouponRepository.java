package com.advprog.perbaikiinaja.repository;

import com.advprog.perbaikiinaja.model.Coupon;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryCouponRepository implements CouponRepository {

    private final Map<String, Coupon> couponStorage = new HashMap<>();

    @Override
    public Coupon save(Coupon coupon) {
        couponStorage.put(coupon.getCode(), coupon);
        return coupon;
    }

    @Override
    public Optional<Coupon> findByCode(String code) {
        return Optional.ofNullable(couponStorage.get(code));
    }

    @Override
    public List<Coupon> findAll() {
        return new ArrayList<>(couponStorage.values());
    }

    public void deleteByCode(String code) {
    couponStorage.remove(code);
}
}
