package com.advprog.perbaikiinaja.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ReportTest {
    @Test
    public void testReportCreation() {
        String technicianId = "TECH123";
        String description = "Kerusakan pada motherboard";
        String status = "WAITING";
        LocalDateTime createdAt = LocalDateTime.now();

        Report report = new Report("RPT001", technicianId, description, status, createdAt);

        assertEquals("RPT001", report.getId());
        assertEquals("TECH123", report.getTechnicianId());
        assertEquals("Kerusakan pada motherboard", report.getDescription());
        assertEquals("WAITING", report.getStatus());
        assertEquals(createdAt, report.getCreatedAt());
    }

    @Test
    public void testReportWithEmptyDescriptionThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Report("RPT002", "TECH456", "", "WAITING", LocalDateTime.now());
        });

        assertEquals("Deskripsi tidak boleh kosong", exception.getMessage());
    }

    @Test
    public void testReportWithNullStatusThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Report("RPT003", "TECH789", "Kerusakan layar", null, LocalDateTime.now());
        });

        assertEquals("Status laporan tidak boleh null", exception.getMessage());
    }
}
