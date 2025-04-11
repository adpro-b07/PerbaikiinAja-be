package com.advprog.perbaikiinaja.repository;

import com.advprog.perbaikiinaja.model.Report;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryReportRepository implements ReportRepository {

    private final Map<String, Report> reportStorage = new HashMap<>();

    @Override
    public Report save(Report report) {
        reportStorage.put(report.getId(), report);
        return report;
    }

    @Override
    public Optional<Report> findById(String id) {
        return Optional.ofNullable(reportStorage.get(id));
    }

    @Override
    public List<Report> findAll() {
        return new ArrayList<>(reportStorage.values());
    }

    @Override
    public void deleteById(String id) {
        reportStorage.remove(id);
    }
}
