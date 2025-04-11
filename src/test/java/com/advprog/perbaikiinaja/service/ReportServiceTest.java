package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.model.Order;
import com.advprog.perbaikiinaja.model.OrderStatus;
import com.advprog.perbaikiinaja.model.Report;
import com.advprog.perbaikiinaja.repository.OrderRepository;
import com.advprog.perbaikiinaja.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReportServiceTest {
    private ReportRepository reportRepo;
    private OrderRepository orderRepo;
    private ReportService reportService;

    @BeforeEach
    void setUp() {
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

}
