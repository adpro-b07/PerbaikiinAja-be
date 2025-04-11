package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.model.Order;
import com.advprog.perbaikiinaja.model.OrderStatus;
import com.advprog.perbaikiinaja.model.Report;
import com.advprog.perbaikiinaja.repository.OrderRepository;
import com.advprog.perbaikiinaja.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepo;
    private final OrderRepository orderRepo;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepo, OrderRepository orderRepo) {
        this.reportRepo = reportRepo;
        this.orderRepo = orderRepo;
    }

    @Override
    public Report createReport(Report report) {
        Order order = orderRepo.findById(report.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.SELESAI) {
            throw new IllegalStateException("Laporan hanya bisa dibuat jika status order adalah SELESAI.");
        }

        return reportRepo.save(report);
    }

}
