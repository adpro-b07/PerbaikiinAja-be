package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.model.Order;
import com.advprog.perbaikiinaja.model.OrderStatus;
import com.advprog.perbaikiinaja.model.Report;
import com.advprog.perbaikiinaja.repository.OrderRepository;
import com.advprog.perbaikiinaja.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

<<<<<<< HEAD
import java.time.LocalDateTime;
import java.util.List;
=======
>>>>>>> e9da9cd2dd7c33ad5da0e5f5f7c8a4e6ea5980e8
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReportServiceTest {
<<<<<<< HEAD

    private ReportRepository reportRepository;
=======
    private ReportRepository reportRepo;
    private OrderRepository orderRepo;
>>>>>>> e9da9cd2dd7c33ad5da0e5f5f7c8a4e6ea5980e8
    private ReportService reportService;

    @BeforeEach
    void setUp() {
<<<<<<< HEAD
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
=======
        reportRepo = mock(ReportRepository.class);
        orderRepo = mock(OrderRepository.class);
        reportService = new ReportServiceImpl(reportRepo, orderRepo);
    }

    @Test
    void createReport_shouldSaveCorrectly() {
        Report report = new Report();
        report.setOrderId("order123");
        report.setTechnicianId("tech123");
        report.setDescription("Ganti layar");
        report.setFinalPrice(200000L);

        Order order = new Order();
        order.setId("order123");
        order.setStatus(OrderStatus.SELESAI);

        when(orderRepo.findById("order123")).thenReturn(Optional.of(order));
        when(reportRepo.save(report)).thenReturn(report);

        Report saved = reportService.createReport(report);

        assertEquals("order123", saved.getOrderId());
        assertEquals("tech123", saved.getTechnicianId());
        verify(reportRepo).save(report);
    }

    @Test
    void createReport_shouldSaveCorrectlyIfOrderIsSelesai() {
        Report report = new Report();
        report.setOrderId("order123");
        report.setTechnicianId("tech123");
        report.setDescription("Ganti layar");
        report.setFinalPrice(200000L);

        Order order = new Order();
        order.setId("order123");
        order.setStatus(OrderStatus.SELESAI);

        when(orderRepo.findById("order123")).thenReturn(Optional.of(order));
        when(reportRepo.save(report)).thenReturn(report);

        Report saved = reportService.createReport(report);

        assertEquals("order123", saved.getOrderId());
        verify(reportRepo).save(report);
    }


    @Test
    void createReport_shouldThrowExceptionIfOrderNotSelesai() {
        Report report = new Report();
        report.setOrderId("order456");

        Order order = new Order();
        order.setId("order456");
        order.setStatus(OrderStatus.DALAM_PROSES);  // status = belum selesai

        when(orderRepo.findById("order456")).thenReturn(Optional.of(order));

        assertThrows(IllegalStateException.class, () -> {
            reportService.createReport(report);
        });
    }

>>>>>>> e9da9cd2dd7c33ad5da0e5f5f7c8a4e6ea5980e8
}
