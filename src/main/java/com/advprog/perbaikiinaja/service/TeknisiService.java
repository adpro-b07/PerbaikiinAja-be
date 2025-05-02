package com.advprog.perbaikiinaja.service;

import java.util.List;

import com.advprog.perbaikiinaja.model.Pesanan;
import com.advprog.perbaikiinaja.model.User;

public interface TeknisiService {
    User createTeknisi(String namaLengkap, String email, String password, String noTelp, String alamat);
    List<Pesanan> getAllPesananTeknisi(String emailTeknisi);  
    List<Pesanan> getPesananMenungguKonfirmasiTeknisi(String emailTeknisi);
    void selesaikanPesanan(long idPesanan);
    int hitungRatingTeknisi(String emailTeknisi);
}