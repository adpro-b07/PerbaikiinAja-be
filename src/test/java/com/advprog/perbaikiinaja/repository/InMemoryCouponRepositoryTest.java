package com.advprog.perbaikiinaja.repository;

import com.advprog.perbaikiinaja.model.Coupon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryCouponRepositoryTest {

    private InMemoryCouponRepository repository;

    @BeforeEach
    public void setUp() {
        repository = new InMemoryCouponRepository();
    }

    @Test
    public void testSaveAndFindByCode() {
        Coupon coupon = new Coupon("SAVE123", 20.0, 10);
        repository.save(coupon);

        Optional<Coupon> result = repository.findByCode("SAVE123");

        assertTrue(result.isPresent());
        assertEquals("SAVE123", result.get().getCode());
    }

    @Test
    public void testFindAll() {
        repository.save(new Coupon("C1", 10.0, 1));
        repository.save(new Coupon("C2", 15.0, 2));

        List<Coupon> allCoupons = repository.findAll();

        assertEquals(2, allCoupons.size());
    }

    @Test
    public void testDeleteByCode() {
        repository.save(new Coupon("DEL123", 30.0, 5));
        repository.deleteByCode("DEL123");

        Optional<Coupon> result = repository.findByCode("DEL123");
        assertFalse(result.isPresent());
    }

    @Test
    public void testDeleteNonExistentCouponDoesNothing() {
        assertDoesNotThrow(() -> repository.deleteByCode("GAKADA"));
    }
}
