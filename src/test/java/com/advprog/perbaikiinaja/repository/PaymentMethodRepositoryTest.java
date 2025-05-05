package com.advprog.perbaikiinaja.repository;

import com.advprog.perbaikiinaja.model.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class PaymentMethodRepositoryTest {
    @Autowired
    private PaymentMethodRepository repository;

    private PaymentMethod method;

    @BeforeEach
    public void setup() {
        repository.deleteAll();
        method = new PaymentMethod("Transfer");
        method = repository.save(method);
    }

    @Test
    public void testSaveAndFindById() {
        Optional<PaymentMethod> found = repository.findById(method.getId()); // Using Long id
        assertTrue(found.isPresent());
        assertEquals(method.getName(), found.get().getName());
    }

    @Test
    public void testFindAll() {
        List<PaymentMethod> list = repository.findAll();
        assertFalse(list.isEmpty());
        assertTrue(list.stream().anyMatch(m -> m.getName().equals("Transfer")));
    }

    @Test
    public void testExistsByName() {
        assertTrue(repository.existsByName("Transfer"));
        assertFalse(repository.existsByName("NonExistent"));
    }

    @Test
    public void testDeleteByName() {
        repository.deleteByName("Transfer");
        assertFalse(repository.existsByName("Transfer"));
    }
}