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
        return reportService.findAllReports();
    }

    @GetMapping("/{id}")
    public Report getReportById(@PathVariable String id) {
        Optional<Report> report = reportService.findReportById(id);
        return report.orElseThrow(() -> new IllegalArgumentException("Report not found"));
    }
}
