package com.advprog.perbaikiinaja.observer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import com.advprog.perbaikiinaja.event.PesananStatusChangedEvent;
import com.advprog.perbaikiinaja.model.Pesanan;

public class PesananPublisher {
    private final List<PesananObserver> observers = new ArrayList<>();

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public void addObserver(PesananObserver observer) {
        observers.add(observer);
    }

    @Deprecated
    public void notifyAllObservers(Pesanan pesanan) {
        for (PesananObserver observer : observers) {
            observer.update(pesanan);
        }
    }
    
    public void updateStatus(Pesanan pesanan, String newStatus) {
        // Update the status
        pesanan.setStatusPesanan(newStatus);
        eventPublisher.publishEvent(new PesananStatusChangedEvent(pesanan));
    }
}