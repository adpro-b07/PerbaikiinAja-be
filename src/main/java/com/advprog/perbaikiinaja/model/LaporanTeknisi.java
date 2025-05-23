package com.advprog.perbaikiinaja.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class LaporanTeknisi implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String laporan;
    int rating;

    @OneToOne(mappedBy = "laporanTeknisi")
    Pesanan pesanan;

    public LaporanTeknisi(String laporan, Pesanan pesanan) {
        this.laporan = laporan;
        this.pesanan = pesanan;
    }
}