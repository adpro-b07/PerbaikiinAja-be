package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.factory.CouponFactory;
import com.advprog.perbaikiinaja.model.Coupon;
import com.advprog.perbaikiinaja.repository.CouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CouponServiceTest {

    private CouponRepository couponRepository;
    private CouponService couponService;

    @BeforeEach
    void setUp() {
        couponRepository = mock(CouponRepository.class);
        couponService = new CouponService(couponRepository);
    }

    @Test
    public void testCreateCouponSuccess() {
        Coupon generatedCoupon = new Coupon("COUP1234", 30.0, 10);
        // mocking factory static method is optional, bisa dummy kode aja
        when(couponRepository.save(any(Coupon.class))).thenReturn(generatedCoupon);

        Coupon result = couponService.createCoupon(30.0, 10);

        assertNotNull(result);
        assertEquals("COUP1234", result.getCode());
        assertEquals(30.0, result.getDiscount());
        assertEquals(10, result.getMaxUsage());
        verify(couponRepository, times(1)).save(any(Coupon.class));
    }

    @Test
    public void testGetCouponByCode() {
        Coupon coupon = new Coupon("GET123", 15.0, 5);
        when(couponRepository.findByCode("GET123")).thenReturn(Optional.of(coupon));

        Optional<Coupon> result = couponService.getCouponByCode("GET123");

        assertTrue(result.isPresent());
        assertEquals("GET123", result.get().getCode());
    }

    @Test
    public void testGetAllCoupons() {
        List<Coupon> dummyList = List.of(
                new Coupon("A", 10, 1),
                new Coupon("B", 20, 2)
        );
        when(couponRepository.findAll()).thenReturn(dummyList);

        List<Coupon> result = couponService.getAllCoupons();

        assertEquals(2, result.size());
    }

    @Test
    public void testUpdateCouponSuccess() {
        // Setup kupon awal
        Coupon oldCoupon = new Coupon("UPDATE123", 10.0, 5);
        when(couponRepository.findByCode("UPDATE123")).thenReturn(Optional.of(oldCoupon));
        when(couponRepository.save(any(Coupon.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Lakukan update
        Coupon updated = couponService.updateCoupon("UPDATE123", 20.0, 10);

        assertEquals("UPDATE123", updated.getCode());
        assertEquals(20.0, updated.getDiscount());
        assertEquals(10, updated.getMaxUsage());

        verify(couponRepository, times(1)).save(updated);
    }

    @Test
    public void testUpdateCouponNotFound() {
        when(couponRepository.findByCode("NOTFOUND")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            couponService.updateCoupon("NOTFOUND", 20.0, 10);
        });

        assertEquals("Kupon dengan kode NOTFOUND tidak ditemukan.", exception.getMessage());
    }

    @Test
    public void testDeleteCouponSuccess() {
        Coupon coupon = new Coupon("DEL123", 15.0, 5);
        when(couponRepository.findByCode("DEL123")).thenReturn(Optional.of(coupon));

        couponService.deleteCoupon("DEL123");

        verify(couponRepository, times(1)).deleteByCode("DEL123");
    }

    @Test
    public void testDeleteCouponNotFound() {
        when(couponRepository.findByCode("MISSING")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            couponService.deleteCoupon("MISSING");
        });

        assertEquals("Kupon tidak ditemukan", exception.getMessage());
    }
}
