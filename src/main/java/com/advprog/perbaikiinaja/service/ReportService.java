package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.model.Report;
import com.advprog.perbaikiinaja.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Report createReport(Report report) {
        return reportRepository.save(report);
    }

    public List<Report> findAllReports() {
        return reportRepository.findAll();
    }

    public Optional<Report> findReportById(String id) {
        return reportRepository.findById(id);
    }
}
