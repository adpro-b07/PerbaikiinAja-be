package com.advprog.perbaikiinaja.repository;

import com.advprog.perbaikiinaja.model.Kupon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class KuponRepositoryTest {
    @Autowired
    private KuponRepository repository;
    
    private Kupon kupon;

    @BeforeEach
    public void setup() {
        repository.deleteAll(); // Clear database before each test
        kupon = new Kupon("KODE50", 50000, 5);
        kupon = repository.save(kupon);
    }

    @Test
    public void testSaveAndFindById() {
        Optional<Kupon> found = repository.findById(kupon.getId());
        assertTrue(found.isPresent());
        assertEquals(kupon.getKodeKupon(), found.get().getKodeKupon());
        assertEquals(kupon.getPotongan(), found.get().getPotongan());
    }

    @Test
    public void testFindAll() {
        List<Kupon> kupons = repository.findAll();
        assertFalse(kupons.isEmpty());
        assertTrue(kupons.stream().anyMatch(k -> k.getId().equals(kupon.getId())));
    }

    @Test
    public void testDeleteById() {
        repository.deleteById(kupon.getId());
        Optional<Kupon> found = repository.findById(kupon.getId());
        assertFalse(found.isPresent());
    }

    @Test
    public void testCount() {
        long count = repository.count();
        assertEquals(1, count);
    }

    @Test
    public void testExistsById() {
        assertTrue(repository.existsById(kupon.getId()));
        assertFalse(repository.existsById("nonexistent-id"));
    }
}