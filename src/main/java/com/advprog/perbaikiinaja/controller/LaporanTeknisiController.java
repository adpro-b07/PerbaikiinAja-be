package com.advprog.perbaikiinaja.controller;

import com.advprog.perbaikiinaja.dto.CreateLaporanTeknisiRequestDTO;
import com.advprog.perbaikiinaja.dto.UpdateLaporanTeknisiRequestDTO;
import com.advprog.perbaikiinaja.model.LaporanTeknisi;
import com.advprog.perbaikiinaja.model.User;
import com.advprog.perbaikiinaja.service.LaporanTeknisiService;
import com.advprog.perbaikiinaja.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/laporan-teknisi")
public class LaporanTeknisiController {

    @Autowired
    private LaporanTeknisiService laporanTeknisiService;

    @Autowired
    private UserService userService;

    @PostMapping("/create/{idPesanan}")
    public ResponseEntity<LaporanTeknisi> createLaporanTeknisi(
            @PathVariable("idPesanan") long idPesanan,
            @Valid @RequestBody CreateLaporanTeknisiRequestDTO requestDTO) {
        try {
            // Validate technician role
            User user = userService.findByEmail(requestDTO.getEmailTeknisi());
            if (user == null || !"Teknisi".equals(user.getRole())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            LaporanTeknisi laporanTeknisi = laporanTeknisiService.createLaporanTeknisi(
                    requestDTO.getLaporan(), idPesanan);
            return new ResponseEntity<>(laporanTeknisi, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{idPesanan}")
    public ResponseEntity<LaporanTeknisi> updateLaporanTeknisi(
            @PathVariable("idPesanan") long idPesanan,
            @Valid @RequestBody UpdateLaporanTeknisiRequestDTO requestDTO) {
        try {
            LaporanTeknisi laporanTeknisi = laporanTeknisiService.updateLaporanTeknisi(
                    idPesanan, requestDTO.getLaporan());
            return ResponseEntity.ok(laporanTeknisi);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<LaporanTeknisi>> getAllLaporanTeknisi() {
        try {
            List<LaporanTeknisi> laporanTeknisiList = laporanTeknisiService.getAllLaporanTeknisi();
            return ResponseEntity.ok(laporanTeknisiList);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/teknisi/{email}")
    public ResponseEntity<List<LaporanTeknisi>> getLaporanTeknisiByTeknisi(@PathVariable("email") String email) {
        try {
            List<LaporanTeknisi> laporanTeknisiList = laporanTeknisiService.getLaporanTeknisiByTeknisi(email);
            return ResponseEntity.ok(laporanTeknisiList);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/pengguna/{email}")
    public ResponseEntity<List<LaporanTeknisi>> getLaporanTeknisiByPengguna(@PathVariable("email") String email) {
        try {
            List<LaporanTeknisi> laporanTeknisiList = laporanTeknisiService.getLaporanTeknisiByPengguna(email);
            return ResponseEntity.ok(laporanTeknisiList);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/pesanan/{idPesanan}")
    public ResponseEntity<LaporanTeknisi> getLaporanTeknisiByPesananId(@PathVariable("idPesanan") long idPesanan) {
        try {
            LaporanTeknisi laporanTeknisi = laporanTeknisiService.getLaporanTeknisiByPesananId(idPesanan);
            return ResponseEntity.ok(laporanTeknisi);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}