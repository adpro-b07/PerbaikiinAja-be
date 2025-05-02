package com.advprog.perbaikiinaja.repository;

import com.advprog.perbaikiinaja.model.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryReportRepositoryTest {

    private InMemoryReportRepository repository;

    @BeforeEach
    public void setUp() {
        repository = new InMemoryReportRepository();
    }

    @Test
    public void testSaveAndFindById() {
        // Menggunakan setter alih-alih konstruktor
        Report report = new Report();
        report.setOrderId("RPT001");
        report.setTechnicianId("TECH01");
        report.setDescription("Kerusakan motherboard");
        
        repository.save(report);

        // Menggunakan findByOrderId alih-alih findById
        Optional<Report> result = repository.findByOrderId("RPT001");

        assertTrue(result.isPresent());
        assertEquals("TECH01", result.get().getTechnicianId());
    }

    @Test
    public void testFindAll() {
        // Menggunakan setter alih-alih konstruktor
        Report report1 = new Report();
        report1.setOrderId("R1");
        report1.setTechnicianId("TECH1");
        report1.setDescription("Deskripsi 1");
        repository.save(report1);
        
        Report report2 = new Report();
        report2.setOrderId("R2");
        report2.setTechnicianId("TECH2");
        report2.setDescription("Deskripsi 2");
        repository.save(report2);

        List<Report> allReports = repository.findAll();

        assertEquals(2, allReports.size());
    }

    @Test
    public void testDeleteById() {
        // Menggunakan setter alih-alih konstruktor
        Report report = new Report();
        report.setOrderId("DELID");
        report.setTechnicianId("TECH3");
        report.setDescription("Hapus laporan");
        repository.save(report);
        
        repository.deleteById("DELID");

        // Menggunakan findByOrderId alih-alih findById
        Optional<Report> result = repository.findByOrderId("DELID");
        assertFalse(result.isPresent());
    }
}
