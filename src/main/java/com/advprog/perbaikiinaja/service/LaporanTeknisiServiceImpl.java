package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.model.Pesanan;
import com.advprog.perbaikiinaja.model.LaporanTeknisi;
import com.advprog.perbaikiinaja.repository.LaporanTeknisiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LaporanTeknisiServiceImpl implements LaporanTeknisiService {

    @Autowired
    private LaporanTeknisiRepository laporanTeknisiRepository;

    @Autowired
    private PesananService pesananService;

    @Override
    public LaporanTeknisi createLaporanTeknisi(String laporan, long idPesanan) {
        Pesanan pesanan = pesananService.findById(idPesanan);
        if (pesanan == null) {
            throw new RuntimeException("Pesanan not found with ID: " + idPesanan);
        }
        LaporanTeknisi existingLaporanTeknisi = laporanTeknisiRepository.findByPesanan_Id(idPesanan)
                .orElse(null);
        if (existingLaporanTeknisi != null) {
            throw new RuntimeException("Technician Report already exists for this pesanan");
        }
        LaporanTeknisi laporanTeknisi = new LaporanTeknisi(laporan, pesanan);
        return laporanTeknisiRepository.save(laporanTeknisi);
    }

    @Override
    public LaporanTeknisi updateLaporanTeknisi(long idPesanan, String laporan) {
        LaporanTeknisi laporanTeknisi = laporanTeknisiRepository.findByPesanan_Id(idPesanan) 
                .orElseThrow(() -> new RuntimeException("LaporanTeknisi not found with ID: " + idPesanan));   
        laporanTeknisi.setLaporan(laporan);
        return laporanTeknisiRepository.save(laporanTeknisi);
    }

    @Override
    public LaporanTeknisi getLaporanTeknisiByPesananId(long pesananId) {
        LaporanTeknisi laporanTeknisi = laporanTeknisiRepository.findByPesanan_Id(pesananId)
                .orElseThrow(() -> new RuntimeException("LaporanTeknisi not found with pesanan ID: " + pesananId));
        if (laporanTeknisi == null) {
            throw new RuntimeException("LaporanTeknisi not found for pesanan ID: " + pesananId);
        }
        return laporanTeknisi;
    }

    @Override
    public List<LaporanTeknisi> getAllLaporanTeknisi() {
        return laporanTeknisiRepository.findAll();
    }

    @Override
    public List<LaporanTeknisi> getLaporanTeknisiByTeknisi(String emailTeknisi) {
        return laporanTeknisiRepository.findByPesanan_EmailTeknisi(emailTeknisi);
    }

    @Override
    public List<LaporanTeknisi> getLaporanTeknisiByPengguna(String emailPengguna) {
        return laporanTeknisiRepository.findByPesanan_EmailPengguna(emailPengguna);
    }

    @Override
    public List<LaporanTeknisi> findByTeknisi(String emailTeknisi) {
        return laporanTeknisiRepository.findByPesanan_EmailTeknisi(emailTeknisi);
    }
}