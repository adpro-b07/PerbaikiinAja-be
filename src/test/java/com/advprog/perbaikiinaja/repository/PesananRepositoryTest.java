package com.advprog.perbaikiinaja.repository;

import com.advprog.perbaikiinaja.model.PaymentMethod;
import com.advprog.perbaikiinaja.model.Pesanan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PesananRepositoryTest {
    private PesananRepository repository;
    private Pesanan pesanan;

    @BeforeEach
    public void setup() {
        repository = new PesananRepository();
        pesanan = new Pesanan("TV", "Rusak", "KUPON10", "a@mail.com", "b@mail.com", new PaymentMethod("PM01", "Bank A"));
        repository.save(pesanan);
    }

    @Test
    public void testSaveAndFindById() {
        Pesanan found = repository.findById(pesanan.getId());
        assertEquals(pesanan, found);
    }

    @Test
    public void testFindAll() {
        assertTrue(repository.findAll().contains(pesanan));
    }

    @Test
    public void testFindByPengguna() {
        assertTrue(repository.findByPengguna("a@mail.com").contains(pesanan));
    }

    @Test
    public void testDeleteById() {
        repository.deleteById(pesanan.getId());
        assertNull(repository.findById(pesanan.getId()));
    }
}