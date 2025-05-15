package com.advprog.perbaikiinaja.observer;

import java.util.ArrayList;
import java.util.List;
import com.advprog.perbaikiinaja.model.Pesanan;

public class PesananPublisher {
    private final List<PesananObserver> observers = new ArrayList<>();

    public void addObserver(PesananObserver observer) {
        observers.add(observer);
    }

    public void notifyAllObservers(Pesanan pesanan) {
        for (PesananObserver observer : observers) {
            observer.update(pesanan);
        }
    }
}