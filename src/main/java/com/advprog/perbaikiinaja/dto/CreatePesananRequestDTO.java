package com.advprog.perbaikiinaja.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CreatePesananRequestDTO {

    @NotBlank(message = "Nama barang wajib diisi")
    private String namaBarang;

    @NotBlank(message = "Kondisi barang wajib diisi")
    private String kondisiBarang;

    private String kodeKupon;

    @Email(message = "Format email pengguna tidak valid")
    private String emailPengguna;

    @NotBlank(message = "Metode pembayaran wajib diisi")
    private String metodePembayaran;

    // Getters & Setters
    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getKondisiBarang() {
        return kondisiBarang;
    }

    public void setKondisiBarang(String kondisiBarang) {
        this.kondisiBarang = kondisiBarang;
    }

    public String getKodeKupon() {
        return kodeKupon;
    }

    public void setKodeKupon(String kodeKupon) {
        this.kodeKupon = kodeKupon;
    }

    public String getEmailPengguna() {
        return emailPengguna;
    }

    public void setEmailPengguna(String emailPengguna) {
        this.emailPengguna = emailPengguna;
    }

    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    public void setMetodePembayaran(String metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }
}
