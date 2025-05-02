package com.advprog.perbaikiinaja.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Teknisi extends User {
    String alamat;
    int totalPekerjaanSelesai;
    long totalPenghasilan;

    public Teknisi(String id, String namaLengkap, String email, String password, String noTelp, String alamat) {
        super(id, namaLengkap, email, password, noTelp, "Teknisi");
        this.alamat = alamat;
        this.totalPekerjaanSelesai = 0;
        this.totalPenghasilan = 0;
    }
}