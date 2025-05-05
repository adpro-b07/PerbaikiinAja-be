package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.model.PaymentMethod;
import com.advprog.perbaikiinaja.model.Pesanan;
import com.advprog.perbaikiinaja.model.Report;
import com.advprog.perbaikiinaja.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReportServiceImplTest {

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private PesananService pesananService;

    @InjectMocks
    private ReportServiceImpl reportService;

    private Pesanan dummyPesanan;
    private Report dummyReport;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        PaymentMethod metodePembayaran = new PaymentMethod("Gopay");

        dummyPesanan = new Pesanan(
                "Laptop",
                "Rusak total",
                "DISKON10",
                "pengguna@email.com",
                "teknisi@email.com",
                metodePembayaran
        );
        dummyPesanan.setId(1L);

        dummyReport = new Report("Bagus", 5, dummyPesanan);
    }

    @Test
    public void testCreateReport_Success() {
        when(pesananService.findById(1L)).thenReturn(dummyPesanan);
        when(reportRepository.findByPesanan_Id(1L)).thenReturn(Optional.empty());
        when(reportRepository.save(any(Report.class))).thenReturn(dummyReport);

        Report result = reportService.createReport("Bagus", 5, 1L);

        assertNotNull(result);
        assertEquals("Bagus", result.getUlasan());
        assertEquals(5, result.getRating());
        verify(reportRepository).save(any(Report.class));
    }

    @Test
    public void testCreateReport_ReportAlreadyExists() {
        when(pesananService.findById(1L)).thenReturn(dummyPesanan);
        when(reportRepository.findByPesanan_Id(1L)).thenReturn(Optional.of(dummyReport));

        Exception exception = assertThrows(RuntimeException.class, () ->
                reportService.createReport("Bagus", 5, 1L)
        );
        assertEquals("Report already exists for this pesanan", exception.getMessage());
    }

    @Test
    public void testUpdateReport_Success() {
        when(reportRepository.findByPesanan_Id(1L)).thenReturn(Optional.of(dummyReport));
        when(reportRepository.save(any(Report.class))).thenReturn(dummyReport);

        Report result = reportService.updateReport(1L, "Sangat bagus", 4);

        assertEquals("Sangat bagus", result.getUlasan());
        assertEquals(4, result.getRating());
        verify(reportRepository).save(any(Report.class));
    }

    @Test
    public void testUpdateReport_ReportNotFound() {
        when(reportRepository.findByPesanan_Id(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
                reportService.updateReport(1L, "Ulasan baru", 3)
        );
        assertEquals("Report not found with ID: 1", exception.getMessage());
    }

    @Test
    public void testGetReportByPesananId_Success() {
        when(reportRepository.findByPesanan_Id(1L)).thenReturn(Optional.of(dummyReport));

        Report result = reportService.getReportByPesananId(1L);
        assertEquals(dummyReport, result);
        verify(reportRepository).findByPesanan_Id(1L);
    }

    @Test
    public void testGetAllReports() {
        when(reportRepository.findAll()).thenReturn(List.of(dummyReport));

        List<Report> reports = reportService.getAllReports();
        assertFalse(reports.isEmpty());
        assertEquals(1, reports.size());
        verify(reportRepository).findAll();
    }

    @Test
    public void testGetReportsByTeknisi() {
        when(reportRepository.findByPesanan_EmailTeknisi("teknisi@email.com"))
                .thenReturn(List.of(dummyReport));

        List<Report> reports = reportService.getReportsByTeknisi("teknisi@email.com");
        assertFalse(reports.isEmpty());
        assertEquals(1, reports.size());
        verify(reportRepository).findByPesanan_EmailTeknisi("teknisi@email.com");
    }

    @Test
    public void testGetReportsByPengguna() {
        when(reportRepository.findByPesanan_EmailPengguna("pengguna@email.com"))
                .thenReturn(List.of(dummyReport));

        List<Report> reports = reportService.getReportsByPengguna("pengguna@email.com");
        assertFalse(reports.isEmpty());
        assertEquals(1, reports.size());
        verify(reportRepository).findByPesanan_EmailPengguna("pengguna@email.com");
    }
}