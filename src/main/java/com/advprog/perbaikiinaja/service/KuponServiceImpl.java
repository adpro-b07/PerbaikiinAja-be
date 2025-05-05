package com.advprog.perbaikiinaja.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.advprog.perbaikiinaja.model.Kupon;
import com.advprog.perbaikiinaja.repository.KuponRepository;

@Service
public class KuponServiceImpl implements KuponService {

    @Autowired
    private KuponRepository kuponRepository;

    @Override
    public Iterable<Kupon> getAllKupon() {
        return kuponRepository.findAll();
    }

    public List<Kupon> getActiveKupon() {
        List<Kupon> activeKuponList = new ArrayList<>();
        for (Kupon kupon : kuponRepository.findAll()) {
            if (kupon.getBatasPemakaian() > 0) {
                activeKuponList.add(kupon);
            }
        }
        return activeKuponList;
    }

    public List<Kupon> getInactiveKupon() {
        List<Kupon> inactiveKuponList = new ArrayList<>();
        for (Kupon kupon : kuponRepository.findAll()) {
            if (kupon.getBatasPemakaian() <= 0) {
                inactiveKuponList.add(kupon);
            }
        }
        return inactiveKuponList;
    }

    @Override
    public Kupon getKuponByKode(String kodeKupon) {
        Kupon kupon = findByKodeKupon(kodeKupon);
        if (kupon == null) {
            throw new RuntimeException("Kupon dengan kode " + kodeKupon + " tidak ditemukan");
        }
        return kupon;
    }

    @Override
    public Kupon createKupon(String kodeKupon, int diskon, int batasPemakaian) {
        if (diskon <= 0) {
            throw new IllegalArgumentException("Diskon harus lebih dari 0");
        }
        
        if (batasPemakaian <= 0) {
            throw new IllegalArgumentException("Batas pemakaian harus lebih dari 0");
        }
        
        Kupon existingKupon = findByKodeKupon(kodeKupon);
        if (existingKupon != null) {
            throw new RuntimeException("Kupon dengan kode " + kodeKupon + " sudah ada");
        }
        
        Kupon newKupon = new Kupon(kodeKupon, diskon, batasPemakaian);
        return kuponRepository.save(newKupon);
    }

    @Override
    public Kupon updateKupon(String kodeKupon, int newDiskon, int newBatasPemakaian) {
        if (newDiskon <= 0) {
            throw new IllegalArgumentException("Diskon harus lebih dari 0");
        }
        
        if (newBatasPemakaian <= 0) {
            throw new IllegalArgumentException("Batas pemakaian harus lebih dari 0");
        }
        
        Kupon kupon = findByKodeKupon(kodeKupon);
        if (kupon == null) {
            throw new RuntimeException("Kupon dengan kode " + kodeKupon + " tidak ditemukan");
        }
        
        kupon.setPotongan(newDiskon);
        kupon.setBatasPemakaian(newBatasPemakaian);
        return kuponRepository.save(kupon);
    }

    @Override
    public void deleteKupon(String kodeKupon) {
        Kupon kupon = findByKodeKupon(kodeKupon);
        if (kupon == null) {
            throw new RuntimeException("Kupon dengan kode " + kodeKupon + " tidak ditemukan");
        }
        kuponRepository.deleteById(kupon.getId());
    }

    @Override
    public void decrementKuponUsage(String kodeKupon) {
        Kupon kupon = findByKodeKupon(kodeKupon);
        if (kupon == null) {
            throw new RuntimeException("Kupon dengan kode " + kodeKupon + " tidak ditemukan");
        }
        if (kupon.getBatasPemakaian() <= 0) {
            throw new RuntimeException("Kupon dengan kode " + kodeKupon + " sudah habis digunakan");
        }
        kupon.setBatasPemakaian(kupon.getBatasPemakaian() - 1);
        kuponRepository.save(kupon);
    }

    @Override
    public void incrementKuponUsage(String kodeKupon) {
        Kupon kupon = findByKodeKupon(kodeKupon);
        if (kupon == null) {
            throw new RuntimeException("Kupon dengan kode " + kodeKupon + " tidak ditemukan");
        }
        kupon.setBatasPemakaian(kupon.getBatasPemakaian() + 1);
        kuponRepository.save(kupon);
    }

    @Override
    public Kupon findByKodeKupon(String kodeKupon) {
        List<Kupon> kuponList = kuponRepository.findAll();
        for (Kupon kupon : kuponList) {
            if (kupon.getKodeKupon().equals(kodeKupon)) {
                return kupon;
            }
        }
        return null;
    }
}