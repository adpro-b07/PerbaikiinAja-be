package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.model.Pesanan;
import com.advprog.perbaikiinaja.model.Report;
import com.advprog.perbaikiinaja.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private PesananService pesananService;

    @Override
    public Report createReport(String ulasan, int rating, long idPesanan) {
        Pesanan pesanan = pesananService.findById(idPesanan);
        if (pesanan == null) {
            throw new RuntimeException("Pesanan not found with ID: " + idPesanan);
        }
        Report existingReport = reportRepository.findByPesanan_Id(idPesanan)
                .orElse(null);
        if (existingReport != null) {
            throw new RuntimeException("Report already exists for this pesanan");
        }
        Report report = new Report(ulasan, rating, pesanan);
        return reportRepository.save(report);
    }

    @Override
    public Report updateReport(long idPesanan, String ulasan, int rating) {
        Report report = reportRepository.findByPesanan_Id(idPesanan) 
                .orElseThrow(() -> new RuntimeException("Report not found with ID: " + idPesanan));   
        report.setUlasan(ulasan);
        report.setRating(rating);
        return reportRepository.save(report);
    }

    @Override
    public Report getReportByPesananId(long pesananId) {
        Report report = reportRepository.findByPesanan_Id(pesananId)
                .orElseThrow(() -> new RuntimeException("Report not found with pesanan ID: " + pesananId));
        if (report == null) {
            throw new RuntimeException("Report not found for pesanan ID: " + pesananId);
        }
        return report;
    }

    @Override
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @Override
    public List<Report> getReportsByTeknisi(String emailTeknisi) {
        return reportRepository.findByPesanan_EmailTeknisi(emailTeknisi);
    }

    @Override
    public List<Report> getReportsByPengguna(String emailPengguna) {
        return reportRepository.findByPesanan_EmailPengguna(emailPengguna);
    }

    @Override
    public List<Report> findByTeknisi(String emailTeknisi) {
        return reportRepository.findByPesanan_EmailTeknisi(emailTeknisi);
    }
}