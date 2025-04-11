package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.factory.CouponFactory;
import com.advprog.perbaikiinaja.model.Coupon;
import com.advprog.perbaikiinaja.repository.CouponRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public Coupon createCoupon(double discount, int maxUsage) {
        Coupon coupon = CouponFactory.createCoupon(discount, maxUsage);
        return couponRepository.save(coupon);
    }

    public Optional<Coupon> getCouponByCode(String code) {
        return couponRepository.findByCode(code);
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    // tambahin updateCoupon, deleteCoupon, dsb
    public Coupon updateCoupon(String code, double newDiscount, int newMaxUsage) {
    Optional<Coupon> optionalCoupon = couponRepository.findByCode(code);
    if (optionalCoupon.isEmpty()) {
        throw new IllegalArgumentException("Kupon dengan kode " + code + " tidak ditemukan.");
    }

    Coupon existingCoupon = optionalCoupon.get();

    // Update field (kalau kamu pakai Lombok @Data, bisa langsung setter)
    existingCoupon.setDiscount(newDiscount);
    existingCoupon.setMaxUsage(newMaxUsage);

    return couponRepository.save(existingCoupon); // overwrite yang lama
}

public void deleteCoupon(String code) {
    Optional<Coupon> optionalCoupon = couponRepository.findByCode(code);
    if (optionalCoupon.isPresent()) {
        couponRepository.deleteByCode(code); // method ini nanti kita bikin juga di repository
    } else {
        throw new IllegalArgumentException("Kupon tidak ditemukan");
    }
}
}
