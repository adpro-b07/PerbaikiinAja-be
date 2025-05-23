package com.advprog.perbaikiinaja.event;

import com.advprog.perbaikiinaja.model.Pesanan;

public class PesananStatusChangedEvent {
    private final Pesanan pesanan;

    public PesananStatusChangedEvent(Pesanan pesanan) {
        this.pesanan = pesanan;
    }

    public Pesanan getPesanan() {
        return pesanan;
    }
}
