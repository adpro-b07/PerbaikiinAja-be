package com.advprog.perbaikiinaja.service;

import java.util.List;

import com.advprog.perbaikiinaja.model.Pesanan;
import com.advprog.perbaikiinaja.model.User;

public interface PenggunaService {
    User createPengguna(String namaLengkap, String email, String password, String noTelp, String alamat);
    List<Pesanan> getPesananMenungguKonfirmasiPengguna(String emailPengguna);
    void terimaPesanan(long idPesanan);
    void tolakPesanan(long idPesanan);
}
