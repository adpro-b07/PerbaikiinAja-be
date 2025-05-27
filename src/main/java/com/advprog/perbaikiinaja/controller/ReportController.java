package com.advprog.perbaikiinaja.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.advprog.perbaikiinaja.model.Report;
import com.advprog.perbaikiinaja.model.User;
import com.advprog.perbaikiinaja.service.ReportService;
import com.advprog.perbaikiinaja.service.UserService;

@Controller
@RequestMapping("api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Iterable<Report>> getAllReport() {
        try {
            Iterable<Report> reports = reportService.getAllReports();
            return ResponseEntity.ok(reports);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/get/{idPesanan}")
    public ResponseEntity<Report> getReportByIdPesanan(@PathVariable long idPesanan) {
        try {
            Report report = reportService.getReportByPesananId(idPesanan);
            return ResponseEntity.ok(report);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create/{idPesanan}")
    public ResponseEntity<Report> createReport(@RequestBody Map<String, String> payload, @PathVariable long idPesanan) {
        String ulasan = payload.get("ulasan");
        int rating = payload.get("rating") != null ? Integer.parseInt(payload.get("rating")) : 0;
        String emailPengguna = payload.get("emailPengguna");

        if (ulasan == null || rating < 1 || rating > 5 || emailPengguna == null) {
            return ResponseEntity.badRequest().build();
        }
        
        User user = userService.findByEmail(emailPengguna);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        
        try {
            Report report = reportService.createReport(ulasan, rating, idPesanan);
            if (!report.getPesanan().getEmailPengguna().equals(emailPengguna)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            } 
            return ResponseEntity.status(HttpStatus.CREATED).body(report);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{idPesanan}")
    public ResponseEntity<Report> updateReport(@RequestBody Map<String, String> payload, @PathVariable long idPesanan) {
        String ulasan = payload.get("ulasan");
        int rating = payload.get("rating") != null ? Integer.parseInt(payload.get("rating")) : 0;
        
        if (ulasan == null || rating < 1 || rating > 5) {
            return ResponseEntity.badRequest().build();
        }
        
        try {
            Report report = reportService.updateReport(idPesanan, ulasan, rating);
            return ResponseEntity.ok(report);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/teknisi/{email}")
    public ResponseEntity<Iterable<Report>> getReportByTeknisi(@PathVariable String email) {
        try {
            Iterable<Report> reports = reportService.getReportsByTeknisi(email);
            return ResponseEntity.ok(reports);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/pengguna/{email}")
    public ResponseEntity<Iterable<Report>> getReportByPengguna(@PathVariable String email) {
        try {
            Iterable<Report> reports = reportService.getReportsByPengguna(email);
            return ResponseEntity.ok(reports);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable("id") Long id) {
        try {
            reportService.deleteReport(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}