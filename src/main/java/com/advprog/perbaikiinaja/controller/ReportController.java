package com.advprog.perbaikiinaja.controller;

import com.advprog.perbaikiinaja.model.Report;
import com.advprog.perbaikiinaja.service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public List<Report> getAllReports() {
        // Menggunakan method yang tersedia di ReportService
        return reportService.getReports();
    }

    @GetMapping("/{id}")
    public Report getReportById(@PathVariable String id) {
        // Menggunakan method yang tersedia di ReportService (findByOrderId)
        Optional<Report> report = reportService.findByOrderId(id);
        return report.orElseThrow(() -> new IllegalArgumentException("Report not found"));
    }
}
