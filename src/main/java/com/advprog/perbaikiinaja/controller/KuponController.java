package com.advprog.perbaikiinaja.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.advprog.perbaikiinaja.model.Kupon;
import com.advprog.perbaikiinaja.service.KuponService;

@Controller
@RequestMapping("/api/kupon")
public class KuponController {

    @Autowired
    private KuponService kuponService;

    @GetMapping
    public ResponseEntity<Iterable<Kupon>> getAllKupon() {
        Iterable<Kupon> kupon = kuponService.getAllKupon();
        return ResponseEntity.ok(kupon);
    }

    @GetMapping("/active")
    public ResponseEntity<Iterable<Kupon>> getActiveKupon() {
        Iterable<Kupon> kupon = kuponService.getActiveKupon();
        return ResponseEntity.ok(kupon);
    }

    @GetMapping("/inactive")
    public ResponseEntity<Iterable<Kupon>> getInactiveKupon() {
        Iterable<Kupon> kupon = kuponService.getInactiveKupon();
        return ResponseEntity.ok(kupon);
    }

    @GetMapping("/{kodeKupon}")
    public ResponseEntity<Kupon> getKuponByKode(@PathVariable String kodeKupon) {
        try {
            Kupon kupon = kuponService.getKuponByKode(kodeKupon);
            return ResponseEntity.ok(kupon);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Kupon> createKupon(@RequestBody Map<String, Object> payload) {
        try {
            String kodeKupon = (String) payload.get("kodeKupon");
            int potongan = 0;
            int batasPemakaian = 0;
            
            if (payload.get("potongan") instanceof Integer) {
                potongan = (Integer) payload.get("potongan");
            } else if (payload.get("potongan") != null) {
                potongan = Integer.parseInt(payload.get("potongan").toString());
            }
            
            if (payload.get("batasPemakaian") instanceof Integer) {
                batasPemakaian = (Integer) payload.get("batasPemakaian");
            } else if (payload.get("batasPemakaian") != null) {
                batasPemakaian = Integer.parseInt(payload.get("batasPemakaian").toString());
            }

            if (kodeKupon == null || potongan <= 0 || batasPemakaian <= 0) {
                return ResponseEntity.badRequest().build();
            }
            
            Kupon newKupon = kuponService.createKupon(kodeKupon, potongan, batasPemakaian);
            return ResponseEntity.status(201).body(newKupon);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            // Handle case where kupon already exists
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{kodeKupon}")
    public ResponseEntity<Kupon> updateKupon(@PathVariable String kodeKupon, @RequestBody Map<String, Object> payload) {
        try {
            int potongan = 0;
            int batasPemakaian = 0;
            
            if (payload.get("potongan") instanceof Integer) {
                potongan = (Integer) payload.get("potongan");
            } else if (payload.get("potongan") != null) {
                potongan = Integer.parseInt(payload.get("potongan").toString());
            }
            
            if (payload.get("batasPemakaian") instanceof Integer) {
                batasPemakaian = (Integer) payload.get("batasPemakaian");
            } else if (payload.get("batasPemakaian") != null) {
                batasPemakaian = Integer.parseInt(payload.get("batasPemakaian").toString());
            }

            if (potongan <= 0 || batasPemakaian <= 0) {
                return ResponseEntity.badRequest().build();
            }
            
            Kupon updatedKupon = kuponService.updateKupon(kodeKupon, potongan, batasPemakaian);
            return ResponseEntity.ok(updatedKupon);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{kodeKupon}")
    public ResponseEntity<Void> deleteKupon(@PathVariable String kodeKupon) {
        try {
            kuponService.deleteKupon(kodeKupon);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}