package com.advprog.perbaikiinaja.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Report {
    String ulasan;
    int rating;
    Pesanan pesanan;

    public Report(String ulasan, int rating, Pesanan pesanan) {
        this.ulasan = ulasan;
        this.rating = rating;
        this.pesanan = pesanan;
    }
}  
