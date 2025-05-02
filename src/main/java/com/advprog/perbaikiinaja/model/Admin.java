package com.advprog.perbaikiinaja.model;

public class Admin extends User {
    public Admin(String id, String namaLengkap, String email, String password, String noTelp) {
        super(id, namaLengkap, email, password, noTelp, "ADMIN");
    }
}