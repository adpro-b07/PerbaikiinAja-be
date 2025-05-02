package com.advprog.perbaikiinaja.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ReportTest {
    @Test
    public void testReportCreation() {
        // Membuat Report dengan setter (bukan konstruktor)
        Report report = new Report();
        report.setOrderId("RPT001");
        report.setTechnicianId("TECH123");
        report.setDescription("Kerusakan pada motherboard");

        assertEquals("RPT001", report.getOrderId());
        assertEquals("TECH123", report.getTechnicianId());
        assertEquals("Kerusakan pada motherboard", report.getDescription());
    }
}
