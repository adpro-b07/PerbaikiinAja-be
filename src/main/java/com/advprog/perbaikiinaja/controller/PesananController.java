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

import com.advprog.perbaikiinaja.enums.OrderStatus;
import com.advprog.perbaikiinaja.model.Pesanan;
import com.advprog.perbaikiinaja.service.PesananService;

@Controller
@RequestMapping("api/pesanan")
public class PesananController {

    @Autowired
    private PesananService pesananService;

    @GetMapping
    public ResponseEntity<Iterable<Pesanan>> getAllPesanan() {
        try {
            Iterable<Pesanan> pesanan = pesananService.getAllPesanan();
            return ResponseEntity.ok(pesanan);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/get/{idPesanan}")
    public ResponseEntity<Pesanan> getPesananById(@PathVariable("idPesanan") long idPesanan) {
        try {
            Pesanan pesanan = pesananService.getPesananById(idPesanan);
            return ResponseEntity.ok(pesanan);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Pesanan> createPesanan(@RequestBody Map<String, String> payload) {
        String namaBarang = payload.get("namaBarang");
        String kondisiBarang = payload.get("kondisiBarang");
        String kodeKupon = payload.get("kodeKupon");
        String emailPengguna = payload.get("emailPengguna");
        String metodePembayaran = payload.get("metodePembayaran");

        if (namaBarang == null || kondisiBarang == null || emailPengguna == null || metodePembayaran == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Pesanan pesanan = pesananService.createPesanan(namaBarang, kondisiBarang, kodeKupon, emailPengguna, metodePembayaran);
            return new ResponseEntity<>(pesanan, HttpStatus.CREATED);  
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } 
    }

    @PutMapping("/update-status/{idPesanan}")
    public ResponseEntity<Pesanan> updateStatusPesanan(@PathVariable("idPesanan") long idPesanan, @RequestBody Map<String, String> payload) {
        String status = payload.get("status");
        if (status == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            OrderStatus statusEnum = OrderStatus.valueOf(status.toUpperCase());
            Pesanan pesanan = pesananService.updateStatusPesanan(idPesanan, statusEnum.getStatus());
            return ResponseEntity.ok(pesanan);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
