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
public class Report implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String ulasan;
    int rating;

    @OneToOne(mappedBy = "report")
    Pesanan pesanan;

    public Report(String ulasan, int rating, Pesanan pesanan) {
        this.ulasan = ulasan;
        this.rating = rating;
        this.pesanan = pesanan;
    }
}