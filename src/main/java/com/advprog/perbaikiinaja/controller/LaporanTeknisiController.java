package com.advprog.perbaikiinaja.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.advprog.perbaikiinaja.model.LaporanTeknisi;
import com.advprog.perbaikiinaja.model.User;
import com.advprog.perbaikiinaja.service.LaporanTeknisiService;
import com.advprog.perbaikiinaja.service.UserService;

@Controller
@RequestMapping("api/laporan-teknisi")
public class LaporanTeknisiController {

    @Autowired
    private LaporanTeknisiService laporanTeknisiService;

    @Autowired
    private UserService userService;
    
    @GetMapping
    public ResponseEntity<Iterable<LaporanTeknisi>> getAllLaporanTeknisi() {

        try {
            Iterable<LaporanTeknisi> laporanTeknisi = laporanTeknisiService.getAllLaporanTeknisi();
            return ResponseEntity.ok(laporanTeknisi);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/get/{idPesanan}")
    public ResponseEntity<LaporanTeknisi> getLaporanTeknisiByIdPesanan(@PathVariable("idPesanan") long idPesanan) {
        try {            
            LaporanTeknisi LaporanTeknisi = laporanTeknisiService.getLaporanTeknisiByPesananId(idPesanan);
            return ResponseEntity.ok(LaporanTeknisi);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create/{idPesanan}")
    public ResponseEntity<LaporanTeknisi> createLaporanTeknisi(@RequestBody Map<String, String> payload, @PathVariable("idPesanan") long idPesanan) {
        String laporan = payload.get("laporan");
        String emailTeknisi = payload.get("emailTeknisi");

        if (laporan == null || emailTeknisi == null) {
            return ResponseEntity.badRequest().build();
        }
        
        User user = userService.findByEmail(emailTeknisi);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (!user.getRole().equals("pengguna")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        try {
            LaporanTeknisi LaporanTeknisi = laporanTeknisiService.createLaporanTeknisi(laporan, idPesanan);
            if (!LaporanTeknisi.getPesanan().getEmailTeknisi().equals(emailTeknisi)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            } 
            return ResponseEntity.status(HttpStatus.CREATED).body(LaporanTeknisi);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{idPesanan}")
    public ResponseEntity<LaporanTeknisi> updateLaporanTeknisi(@RequestBody Map<String, String> payload, @PathVariable("idPesanan") long idPesanan) {
        String laporan = payload.get("laporan");
        
        if (laporan == null) {
            return ResponseEntity.badRequest().build();
        }
        
        try {
            LaporanTeknisi laporanTeknisi = laporanTeknisiService.updateLaporanTeknisi(idPesanan, laporan);
            return ResponseEntity.ok(laporanTeknisi);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/teknisi/{email}")
    public ResponseEntity<Iterable<LaporanTeknisi>> getLaporanTeknisiByTeknisi(@PathVariable String email) {
        try {
            Iterable<LaporanTeknisi> laporanTeknisi = laporanTeknisiService.getLaporanTeknisiByTeknisi(email);
            return ResponseEntity.ok(laporanTeknisi);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/pengguna/{email}")
    public ResponseEntity<Iterable<LaporanTeknisi>> getLaporanTeknisiByPengguna(@PathVariable String email) {
        try {
            Iterable<LaporanTeknisi> LaporanTeknisis = laporanTeknisiService.getLaporanTeknisiByPengguna(email);
            return ResponseEntity.ok(LaporanTeknisis);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}