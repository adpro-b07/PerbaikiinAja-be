package com.advprog.perbaikiinaja.repository;

import com.advprog.perbaikiinaja.model.LaporanTeknisi;
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
public class LaporanTeknisiRepositoryTest {
    @Autowired
    private LaporanTeknisiRepository repository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private PesananRepository pesananRepository;
    
    private LaporanTeknisi laporanTeknisi;
    private Pesanan pesanan;

    @BeforeEach
    public void setup() {
        repository.deleteAll();
        pesananRepository.deleteAll();
        paymentMethodRepository.deleteAll();
    
        PaymentMethod method = new PaymentMethod("Bank B");
        method = paymentMethodRepository.save(method);
    
        pesanan = new Pesanan("AC", "Tidak dingin", null, "pengguna@mail.com", "teknisi@mail.com", method);
        pesanan = pesananRepository.save(pesanan);
    
        laporanTeknisi = new LaporanTeknisi("Mengisi ulang air freon", pesanan);
        pesanan.setLaporanTeknisi(laporanTeknisi);
        laporanTeknisi = repository.save(laporanTeknisi);
        pesanan = pesananRepository.save(pesanan);
    }

    @Test
    public void testSaveAndFindByPesananId() {
        Optional<LaporanTeknisi> found = repository.findByPesanan_Id(pesanan.getId());
        assertTrue(found.isPresent());
        assertEquals("Mengisi ulang air freon", found.get().getLaporan());
    }

    @Test
    public void testFindAll() {
        List<LaporanTeknisi> reports = repository.findAll();
        assertFalse(reports.isEmpty());
        assertTrue(reports.stream().anyMatch(r -> r.getId().equals(laporanTeknisi.getId())));
    }

    @Test
    public void testFindByTeknisi() {
        List<LaporanTeknisi> reports = repository.findByPesanan_EmailTeknisi("teknisi@mail.com");
        assertFalse(reports.isEmpty());
        assertEquals("Mengisi ulang air freon", reports.get(0).getLaporan());
    }

    @Test
    public void testFindByPengguna() {
        List<LaporanTeknisi> reports = repository.findByPesanan_EmailPengguna("pengguna@mail.com");
        assertFalse(reports.isEmpty());
        assertEquals("Mengisi ulang air freon", reports.get(0).getLaporan());
    }

}