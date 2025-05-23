package com.advprog.perbaikiinaja.service;

import java.util.List;

import com.advprog.perbaikiinaja.model.Pesanan;

public interface PesananService {
    void setHarga(long idPesanan, long harga);
    void deletePesanan(long idPesanan);
    Pesanan createPesanan(String namaBarang, String kondisiBarang, String kodeKupon, String emailPengguna, String metodePembayaran);
    Pesanan createPesanan(Pesanan pesanan);
    Iterable<Pesanan> getAllPesanan();
    Pesanan getPesananById(long idPesanan);
    List<Pesanan> findByPengguna(String emailPengguna);
    Pesanan updateStatusPesanan(long idPesanan, String statusPesanan);
    List<Pesanan> findByTeknisi(String emailTeknisi);
    List<Pesanan> findByTeknisiMenungguTeknisi(String emailTeknisi);
    Pesanan findById(long id);
    List<Pesanan> findByPenggunaMenungguPengguna(String emailPengguna);
    void deleteByPesananId(long idPesanan);
    Pesanan updateHargaPesanan(long idPesanan, long newHarga);
    Pesanan ambilPesanan(long idPesanan, long estimasiHarga, int estimasiWaktu);
}