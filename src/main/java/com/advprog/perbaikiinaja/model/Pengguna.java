package com.advprog.perbaikiinaja.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pengguna extends User {

    List<PaymentMethod> metodePembayaran;
    String alamat;

    public Pengguna(String id, String namaLengkap, String email, String password, String noTelp, String alamat) {
        super(id, namaLengkap, email, password, noTelp, "PENGGUNA");
        this.metodePembayaran = new ArrayList<>();
        this.alamat = alamat;
    }
}