package com.advprog.perbaikiinaja.model;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Kupon implements Serializable {
    @Id
    String id;
    String kodeKupon;
    int potongan;
    int batasPemakaian;

    public Kupon(String kodeKupon, int potongan, int batasPemakaian) {
        this.id = UUID.randomUUID().toString();
        this.kodeKupon = kodeKupon;
        this.potongan = potongan;
        this.batasPemakaian = batasPemakaian;
    }  
}
