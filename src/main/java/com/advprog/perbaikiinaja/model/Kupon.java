package com.advprog.perbaikiinaja.model;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Kupon {
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
