package com.advprog.perbaikiinaja.repository;

import com.advprog.perbaikiinaja.model.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class PaymentMethodRepositoryTest {
    private PaymentMethodRepository repository;
    private PaymentMethod method;

    @BeforeEach
    public void setup() {
        repository = new PaymentMethodRepository();
        method = new PaymentMethod("1", "Transfer");
        repository.save(method);
    }

    @Test
    public void testSaveAndFindById() {
        PaymentMethod found = repository.findById(method.getId());
        assertEquals(method, found);
    }

    @Test
    public void testFindAll() {
        List<PaymentMethod> list = repository.findAll();
        assertTrue(list.contains(method));
    }

    @Test
    public void testDeleteByName() {
        repository.deleteByName("Bank A");
        assertFalse(repository.existsByName("Bank A"));
    }

    @Test
    public void testExistsByName() {
        assertTrue(repository.existsByName("Bank A"));
    }
}