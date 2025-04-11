package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.model.Order;
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
        return reportRepo.save(report);
    }
}
