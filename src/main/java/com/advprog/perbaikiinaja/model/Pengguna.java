package com.advprog.perbaikiinaja.model;

public class Pengguna extends User {
    public Pengguna(String id, String namaLengkap, String email, String password, String noTelp) {
        super(id, namaLengkap, email, password, noTelp, "PENGGUNA");
    }
}