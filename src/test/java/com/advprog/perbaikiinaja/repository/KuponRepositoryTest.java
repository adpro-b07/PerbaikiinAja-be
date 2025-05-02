package com.advprog.perbaikiinaja.repository;

public package com.advprog.perbaikiinaja.repository;

import com.advprog.perbaikiinaja.model.Kupon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class KuponRepositoryTest {
    private KuponRepository repository;
    private Kupon kupon;

    @BeforeEach
    public void setup() {
        repository = new KuponRepository();
        kupon = new Kupon("KODE50", 50000, 5);
        repository.save(kupon);
    }

    @Test
    public void testSaveAndFindById() {
        Kupon found = repository.findById(kupon.getId());
        assertEquals(kupon, found);
    }

    @Test
    public void testFindAll() {
        List<Kupon> kupons = repository.findAll();
        assertTrue(kupons.contains(kupon));
    }

    @Test
    public void testDeleteById() {
        repository.deleteById(kupon.getId());
        assertNull(repository.findById(kupon.getId()));
    }

    @Test
    public void testGetActiveKupon() {
        List<Kupon> active = repository.getActiveKupon();
        assertTrue(active.contains(kupon));
    }

    @Test
    public void testGetInactiveKupon() {
        kupon.setBatasPemakaian(0);
        repository.save(kupon);
        List<Kupon> inactive = repository.getInactiveKupon();
        assertTrue(inactive.contains(kupon));
    }

    @Test
    public void testGetAllKupon() {
        List<Kupon> all = repository.getAllKupon();
        assertTrue(all.contains(kupon));
    }
} {
    
}
