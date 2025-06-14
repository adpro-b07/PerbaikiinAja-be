package com.advprog.perbaikiinaja.controller;

import java.util.List;
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

import com.advprog.perbaikiinaja.command.AmbilPesananCommand;
import com.advprog.perbaikiinaja.dto.AmbilPesananRequestDTO;
import com.advprog.perbaikiinaja.dto.CreatePesananRequestDTO;
import com.advprog.perbaikiinaja.dto.UpdateStatusRequestDTO;
import com.advprog.perbaikiinaja.enums.OrderStatus;
import com.advprog.perbaikiinaja.model.Pesanan;
import com.advprog.perbaikiinaja.service.PesananService;

import jakarta.validation.Valid;

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

    @GetMapping("/pengguna/{email}")
    public ResponseEntity<List<Pesanan>> getPesananByPengguna(@PathVariable("email") String email) {
        try {
            List<Pesanan> pesanan = pesananService.findByPengguna(email);
            return ResponseEntity.ok(pesanan);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/teknisi/{email}")
    public ResponseEntity<List<Pesanan>> getPesananByTeknisi(@PathVariable("email") String email) {
        try {
            List<Pesanan> pesanan = pesananService.findByTeknisi(email);
            return ResponseEntity.ok(pesanan);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PostMapping("/create")
    public ResponseEntity<Pesanan> createPesanan(@Valid @RequestBody CreatePesananRequestDTO requestDTO) {
        try {
            Pesanan pesanan = pesananService.createPesanan(
                requestDTO.getNamaBarang(), 
                requestDTO.getKondisiBarang(), 
                requestDTO.getKodeKupon(), 
                requestDTO.getEmailPengguna(), 
                requestDTO.getMetodePembayaran());
            return new ResponseEntity<>(pesanan, HttpStatus.CREATED);  
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } 
    }

    @PutMapping("/update-status/{idPesanan}")
    public ResponseEntity<Pesanan> updateStatusPesanan(
            @PathVariable("idPesanan") long idPesanan, 
            @Valid @RequestBody UpdateStatusRequestDTO requestDTO) {
        try {
            OrderStatus statusEnum = OrderStatus.valueOf(requestDTO.getStatus().toUpperCase());
            Pesanan pesanan = pesananService.updateStatusPesanan(idPesanan, statusEnum.getStatus());
            return ResponseEntity.ok(pesanan);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{idPesanan}")
    public ResponseEntity<Void> deletePesanan(@PathVariable("idPesanan") long idPesanan) {
        try {
            pesananService.deletePesanan(idPesanan);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/ambil-pesanan/{idPesanan}")
    public ResponseEntity<Pesanan> ambilPesanan(
            @PathVariable("idPesanan") long idPesanan, 
            @Valid @RequestBody AmbilPesananRequestDTO requestDTO) {
        try {
            AmbilPesananCommand command = new AmbilPesananCommand(
                pesananService, 
                idPesanan, 
                requestDTO.getEstimasiHarga(), 
                requestDTO.getEstimasiWaktu());
            Pesanan updatedPesanan = command.execute();
            return ResponseEntity.ok(updatedPesanan);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}