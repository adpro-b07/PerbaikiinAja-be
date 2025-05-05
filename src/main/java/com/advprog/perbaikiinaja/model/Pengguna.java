package com.advprog.perbaikiinaja.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Pengguna extends User {
    @ManyToMany
    @JoinTable(
        name = "pengguna_payment_methods",
        joinColumns = @JoinColumn(name = "pengguna_id"),
        inverseJoinColumns = @JoinColumn(name = "payment_method_id")
    )
    List<PaymentMethod> metodePembayaran;
    
    String alamat;

    public Pengguna(String id, String namaLengkap, String email, String password, String noTelp, String alamat) {
        super(id, namaLengkap, email, password, noTelp, "PENGGUNA");
        this.metodePembayaran = new ArrayList<>();
        this.alamat = alamat;
    }
}