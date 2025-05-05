package com.advprog.perbaikiinaja.repository;

import com.advprog.perbaikiinaja.model.Report;
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
public class ReportRepositoryTest {
    @Autowired
    private ReportRepository repository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private PesananRepository pesananRepository;
    
    private Report report;
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
    
        report = new Report("Bagus", 5, pesanan);
        pesanan.setReport(report);
        report = repository.save(report);
        pesanan = pesananRepository.save(pesanan);
    }

    @Test
    public void testSaveAndFindByPesananId() {
        Optional<Report> found = repository.findByPesanan_Id(pesanan.getId());
        assertTrue(found.isPresent());
        assertEquals("Bagus", found.get().getUlasan());
        assertEquals(5, found.get().getRating());
    }

    @Test
    public void testFindAll() {
        List<Report> reports = repository.findAll();
        assertFalse(reports.isEmpty());
        assertTrue(reports.stream().anyMatch(r -> r.getId().equals(report.getId())));
    }

    @Test
    public void testFindByTeknisi() {
        List<Report> reports = repository.findByPesanan_EmailTeknisi("teknisi@mail.com");
        assertFalse(reports.isEmpty());
        assertEquals("Bagus", reports.get(0).getUlasan());
    }

    @Test
    public void testFindByPengguna() {
        List<Report> reports = repository.findByPesanan_EmailPengguna("pengguna@mail.com");
        assertFalse(reports.isEmpty());
        assertEquals("Bagus", reports.get(0).getUlasan());
    }

    @Test
    public void testCount() {
        long count = repository.count();
        assertEquals(1, count);
    }
}