package com.advprog.perbaikiinaja.observer;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import com.advprog.perbaikiinaja.event.PesananStatusChangedEvent;
import com.advprog.perbaikiinaja.model.Pesanan;
public class UserNotifier implements PesananObserver {
    @Override
    public void update(Pesanan pesanan) {
        System.out.println("📢 Notifikasi: Status pesanan ID " + pesanan.getId() + " sekarang: " + pesanan.getStatusPesanan());
    }

    @Async
    @EventListener
    public void handleStatusChanged(PesananStatusChangedEvent event) {
        Pesanan pesanan = event.getPesanan();
        System.out.println("📢 [NOTIF via EDA] Pesanan ID " + pesanan.getId() + 
            " status: " + pesanan.getStatusPesanan());
    }
}
