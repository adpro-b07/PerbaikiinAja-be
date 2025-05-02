package com.advprog.perbaikiinaja.repository;

import com.advprog.perbaikiinaja.model.Report;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ReportRepository {
    // Mengubah private menjadi protected agar bisa diakses oleh subclass
    protected final Map<String, Report> reports = new HashMap<>();

    public Report save(Report report) {
        reports.put(report.getOrderId(), report);
        return report;
    }

    public Optional<Report> findByOrderId(String orderId) {
        return Optional.ofNullable(reports.get(orderId));
    }
    
    // Menambahkan metode yang akan di-override
    public List<Report> findAll() {
        return new ArrayList<>(reports.values());
    }
    
    // Menambahkan metode yang akan di-override
    public void deleteById(String orderId) {
        reports.remove(orderId);
    }
}
