package com.advprog.perbaikiinaja.repository;

import com.advprog.perbaikiinaja.model.Report;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ReportRepository {
    private final Map<String, Report> reports = new HashMap<>();

    public Report save(Report report) {
        reports.put(report.getOrderId(), report);
        return report;
    }

    public Optional<Report> findByOrderId(String orderId) {
        return Optional.ofNullable(reports.get(orderId));
    }
}
