package com.advprog.perbaikiinaja.model;

import java.io.Serializable;
import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.advprog.perbaikiinaja.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Pesanan implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String namaBarang;
    String kondisiBarang;
    String tanggalServis;
    String kodeKupon;
    String emailPengguna;
    String emailTeknisi;
    String statusPesanan;
    long harga = -1;
    String tanggalSelesai;

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    PaymentMethod metodePembayaran;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "report_id")
    @JsonIgnore
    Report report;

    public Pesanan(String namaBarang, String kondisiBarang, String kodeKupon, String emailPengguna, String emailTeknisi, PaymentMethod metodePembayaran) {
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
