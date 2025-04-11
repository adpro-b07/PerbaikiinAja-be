package com.advprog.perbaikiinaja.controller;

import com.advprog.perbaikiinaja.model.Coupon;
import com.advprog.perbaikiinaja.service.CouponService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.*;

@WebMvcTest(CouponController.class)
public class CouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CouponService couponService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetAllCoupons() throws Exception {
        List<Coupon> dummyList = List.of(
                new Coupon("ABC123", 20.0, 5),
                new Coupon("DEF456", 30.0, 10)
        );

        when(couponService.getAllCoupons()).thenReturn(dummyList);

        mockMvc.perform(get("/api/coupons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testCreateCoupon() throws Exception {
        Coupon dummyCoupon = new Coupon("NEWDISKON", 25.0, 7);
        when(couponService.createCoupon(25.0, 7)).thenReturn(dummyCoupon);

        mockMvc.perform(post("/api/coupons")
                .param("discount", "25.0")
                .param("maxUsage", "7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("NEWDISKON"));
    }

    @Test
    public void testGetCouponByCode() throws Exception {
        Coupon coupon = new Coupon("LOOKME", 15.0, 3);
        when(couponService.getCouponByCode("LOOKME")).thenReturn(Optional.of(coupon));

        mockMvc.perform(get("/api/coupons/LOOKME"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.discount").value(15.0));
    }
}
