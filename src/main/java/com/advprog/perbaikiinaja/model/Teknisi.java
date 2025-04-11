package com.advprog.perbaikiinaja.model;

public class Teknisi extends User {
    public Teknisi(String id, String namaLengkap, String email, String password, String noTelp) {
        super(id, namaLengkap, email, password, noTelp, "Teknisi");
    }
}