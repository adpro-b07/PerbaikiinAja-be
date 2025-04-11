package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.model.Report;
import com.advprog.perbaikiinaja.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReportServiceTest {

    private ReportRepository reportRepository;
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        reportRepository = mock(ReportRepository.class);
        reportService = new ReportService(reportRepository);
    }

    @Test
    public void testCreateReport() {
        Report report = new Report("RPT001", "TECH123", "Kerusakan sistem", "WAITING", LocalDateTime.now());
        when(reportRepository.save(any(Report.class))).thenReturn(report);

        Report result = reportService.createReport(report);

        assertNotNull(result);
        assertEquals("RPT001", result.getId());
        verify(reportRepository, times(1)).save(report);
    }

    @Test
    public void testFindAllReports() {
        List<Report> dummyReports = List.of(
                new Report("R1", "T1", "A", "WAITING", LocalDateTime.now()),
                new Report("R2", "T2", "B", "DONE", LocalDateTime.now())
        );
        when(reportRepository.findAll()).thenReturn(dummyReports);

        List<Report> result = reportService.findAllReports();

        assertEquals(2, result.size());
        verify(reportRepository, times(1)).findAll();
    }

    @Test
    public void testFindReportByIdSuccess() {
        Report report = new Report("RPT007", "TECH007", "Kipas rusak", "WAITING", LocalDateTime.now());
        when(reportRepository.findById("RPT007")).thenReturn(Optional.of(report));

        Optional<Report> result = reportService.findReportById("RPT007");

        assertTrue(result.isPresent());
        assertEquals("TECH007", result.get().getTechnicianId());
    }
}
