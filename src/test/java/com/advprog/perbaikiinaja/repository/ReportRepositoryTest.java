package com.advprog.perbaikiinaja.repository;

import com.advprog.perbaikiinaja.model.Report;
import com.advprog.perbaikiinaja.model.PaymentMethod;
import com.advprog.perbaikiinaja.model.Pesanan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReportRepositoryTest {
    private ReportRepository repository;
    private Report report;
    private Pesanan pesanan;

    @BeforeEach
    public void setup() {
        repository = new ReportRepository();
        pesanan = new Pesanan("AC", "Tidak dingin", null, "pengguna@mail.com", "teknisi@mail.com", new PaymentMethod("PM02", "Bank B"));
        report = new Report("Bagus", 5, pesanan);
        repository.save(report);
    }

    @Test
    public void testSaveAndFindByPesananId() {
        assertEquals(report, repository.findByPesananId(pesanan.getId()));
    }

    @Test
    public void testFindAll() {
        assertTrue(repository.findAll().contains(report));
    }

    @Test
    public void testFindByTeknisi() {
        assertTrue(repository.findByTeknisi("teknisi@mail.com").contains(report));
    }

    @Test
    public void testFindByPengguna() {
        assertTrue(repository.findByPengguna("pengguna@mail.com").contains(report));
    }

    @Test
    public void testDeleteByPesananId() {
        repository.deleteByPesananId(pesanan.getId());
        assertNull(repository.findByPesananId(pesanan.getId()));
    }

    @Test
    public void testDeleteAllReports() {
        repository.deleteAllReports();
        assertTrue(repository.findAll().isEmpty());
    }
}