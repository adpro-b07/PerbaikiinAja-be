package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.model.Report;
import java.util.List;

public interface ReportService {
    Report createReport(String ulasan, int rating, long pesananId);
    Report updateReport(long pesananId, String ulasan, int rating);
    Report getReportByPesananId(long pesananId);
    List<Report> getAllReports();
    List<Report> getReportsByTeknisi(String emailTeknisi);
    List<Report> getReportsByPengguna(String emailPengguna);
    List<Report> findByTeknisi(String emailTeknisi);
    void deleteReport(Long id);
}