package com.advprog.perbaikiinaja.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import com.advprog.perbaikiinaja.enums.OrderStatus;

@Getter
@Setter
public class Pesanan {

    private static long counterId = 0;
    long id;
    String namaBarang;
    String kondisiBarang;
    String tanggalServis;
    String kodeKupon;
    String emailPengguna;
    String emailTeknisi;
    String statusPesanan;
    long harga = -1;
    String tanggalSelesai;
    PaymentMethod metodePembayaran;

    public Pesanan(String namaBarang, String kondisiBarang, String kodeKupon, String emailPengguna, String emailTeknisi, PaymentMethod metodePembayaran) {
        this.id = counterId++;
        this.namaBarang = namaBarang;
        this.kondisiBarang = kondisiBarang;
        this.tanggalServis = Date.valueOf(java.time.LocalDate.now()).toString();
        this.kodeKupon = kodeKupon;
        this.emailPengguna = emailPengguna;
        this.emailTeknisi = emailTeknisi;
        this.statusPesanan = OrderStatus.WAITING_TEKNISI.getStatus();
        this.metodePembayaran = metodePembayaran;
    }
}
