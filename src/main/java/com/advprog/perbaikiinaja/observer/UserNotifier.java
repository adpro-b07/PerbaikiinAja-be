package com.advprog.perbaikiinaja.observer;

import com.advprog.perbaikiinaja.event.PesananStatusChangedEvent;
import com.advprog.perbaikiinaja.model.Pesanan;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import java.util.concurrent.CountDownLatch;
import org.springframework.beans.factory.annotation.Autowired;
@Component
public class UserNotifier implements PesananObserver {

    private final CountDownLatch testLatch;

    public UserNotifier(@Autowired(required = false) CountDownLatch testLatch) {
        this.testLatch = testLatch;
    }

    @Override
    public void update(Pesanan pesanan) {
        // optional legacy
    }

    @Async
    @EventListener
    public void handleStatusChanged(PesananStatusChangedEvent event) {
        System.out.println("📢 [NOTIF via EDA] Pesanan ID " + event.getPesanan().getId() +
                " status: " + event.getPesanan().getStatusPesanan());

        // 🔧 Ini hanya akan terpanggil kalau test menyediakan bean CountDownLatch
        if (testLatch != null) {
            testLatch.countDown();
        }
    }
}
