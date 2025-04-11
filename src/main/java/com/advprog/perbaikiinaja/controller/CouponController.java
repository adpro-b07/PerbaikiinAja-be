package com.advprog.perbaikiinaja.controller;

import com.advprog.perbaikiinaja.model.Coupon;
import com.advprog.perbaikiinaja.service.CouponService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping
    public List<Coupon> getAllCoupons() {
        return couponService.getAllCoupons();
    }

    @PostMapping
    public Coupon createCoupon(@RequestParam double discount, @RequestParam int maxUsage) {
        return couponService.createCoupon(discount, maxUsage);
    }

    @GetMapping("/{code}")
    public Coupon getCouponByCode(@PathVariable String code) {
        Optional<Coupon> coupon = couponService.getCouponByCode(code);
        return coupon.orElseThrow(() -> new IllegalArgumentException("Coupon not found"));
    }
}
