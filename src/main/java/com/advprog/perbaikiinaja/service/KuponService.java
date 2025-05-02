package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.model.Kupon;

public interface KuponService {
    Iterable<Kupon> getAllKupon();
    Iterable<Kupon> getActiveKupon();
    Iterable<Kupon> getInactiveKupon();
    Kupon getKuponByKode(String kodeKupon);
    Kupon createKupon(String kodeKupon, int diskon, int batasPemakaian);
    Kupon updateKupon(String kodeKupon, int newDiskon, int newBatasPemakaian);
    void deleteKupon(String kodeKupon);
    void decrementKuponUsage(String kodeKupon);
    void incrementKuponUsage(String kodeKupon);
    Kupon findByKodeKupon(String kodeKupon);
}