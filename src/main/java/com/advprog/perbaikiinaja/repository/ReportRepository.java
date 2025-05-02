package com.advprog.perbaikiinaja.repository;

import com.advprog.perbaikiinaja.model.Report;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReportRepository {
    private final Map<Long, Report> reports = new ConcurrentHashMap<>();

    public Report save(Report report) {
        reports.put(report.getPesanan().getId(), report);
        return report;
    }

    public Report findByPesananId(long id) {
        return reports.get(id);
    }

    public void deleteByPesananId(long id) {
        reports.remove(id);
    }

    public List<Report> findAll() {
        return new ArrayList<>(reports.values());
    }

    public List<Report> findByTeknisi(String emailTeknisi) {
        List<Report> reportList = new ArrayList<>();
        for (Report report : reports.values()) {
            if (report.getPesanan().getEmailTeknisi().equals(emailTeknisi)) {
                reportList.add(report);
            }
        }
        return reportList;
    }

    public List<Report> findByPengguna(String emailPengguna) {
        List<Report> reportList = new ArrayList<>();
        for (Report report : reports.values()) {
            if (report.getPesanan().getEmailPengguna().equals(emailPengguna)) {
                reportList.add(report);
            }
        }
        return reportList;
    }

    public void deleteAllReports() {
        reports.clear();
    }
}
