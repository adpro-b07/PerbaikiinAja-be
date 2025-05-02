package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.model.Report;

import java.util.List;
import java.util.Optional;

public interface ReportService {
    Report createReport(Report report);
    
    // Menambahkan method yang dibutuhkan oleh controller
    List<Report> getReports();
    Optional<Report> findByOrderId(String orderId);
}
