package com.advprog.perbaikiinaja.repository;

import com.advprog.perbaikiinaja.model.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
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
        Report report = new Report("RPT001", "TECH01", "Kerusakan motherboard", "WAITING", LocalDateTime.now());
        repository.save(report);

        Optional<Report> result = repository.findById("RPT001");

        assertTrue(result.isPresent());
        assertEquals("TECH01", result.get().getTechnicianId());
    }

    @Test
    public void testFindAll() {
        repository.save(new Report("R1", "TECH1", "Deskripsi 1", "DONE", LocalDateTime.now()));
        repository.save(new Report("R2", "TECH2", "Deskripsi 2", "WAITING", LocalDateTime.now()));

        List<Report> allReports = repository.findAll();

        assertEquals(2, allReports.size());
    }

    @Test
    public void testDeleteById() {
        repository.save(new Report("DELID", "TECH3", "Hapus laporan", "WAITING", LocalDateTime.now()));
        repository.deleteById("DELID");

        Optional<Report> result = repository.findById("DELID");
        assertFalse(result.isPresent());
    }
}
