package com.advprog.perbaikiinaja.observer;

import com.advprog.perbaikiinaja.model.Pesanan;

public class UserNotifier implements PesananObserver {
    @Override
    public void update(Pesanan pesanan) {
        System.out.println("📢 Notifikasi: Status pesanan ID " + pesanan.getId() + " sekarang: " + pesanan.getStatusPesanan());
    }
}