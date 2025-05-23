package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.model.LaporanTeknisi;
import java.util.List;

public interface LaporanTeknisiService {
    LaporanTeknisi createLaporanTeknisi(String laporan, long pesananId);
    LaporanTeknisi updateLaporanTeknisi(long pesananId, String laporan);
    LaporanTeknisi getLaporanTeknisiByPesananId(long pesananId);
    List<LaporanTeknisi> getAllLaporanTeknisi();
    List<LaporanTeknisi> getLaporanTeknisiByTeknisi(String emailTeknisi);
    List<LaporanTeknisi> getLaporanTeknisiByPengguna(String emailPengguna);
    List<LaporanTeknisi> findByTeknisi(String emailTeknisi);
}