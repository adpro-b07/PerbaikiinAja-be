package com.advprog.perbaikiinaja.repository;

import com.advprog.perbaikiinaja.model.PaymentMethod;
import com.advprog.perbaikiinaja.model.Pesanan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PesananRepositoryTest {
    @Autowired
    private PesananRepository repository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    private Pesanan pesanan;

    @BeforeEach
    public void setup() {
        repository.deleteAll();
        PaymentMethod method = new PaymentMethod("Bank A");
        method = paymentMethodRepository.save(method);
        pesanan = new Pesanan("TV", "Rusak", "KUPON10", "a@mail.com", "b@mail.com", method);
        pesanan = repository.save(pesanan);
    }

    @Test
    public void testSaveAndFindById() {
        Optional<Pesanan> found = repository.findById(pesanan.getId());
        assertTrue(found.isPresent());
        assertEquals(pesanan.getNamaBarang(), found.get().getNamaBarang());
        assertEquals(pesanan.getKondisiBarang(), found.get().getKondisiBarang());
    }

    @Test
    public void testFindAll() {
        List<Pesanan> pesanans = repository.findAll();
        assertFalse(pesanans.isEmpty());
        assertTrue(pesanans.stream().anyMatch(p -> p.getId().equals(pesanan.getId())));
    }

    @Test
    public void testDeleteById() {
        repository.deleteById(pesanan.getId());
        Optional<Pesanan> found = repository.findById(pesanan.getId());
        assertFalse(found.isPresent());
    }

    @Test
    public void testCount() {
        long count = repository.count();
        assertEquals(1, count);
    }
}