package com.advprog.perbaikiinaja.repository;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.advprog.perbaikiinaja.model.Kupon;

@Repository
public class KuponRepository {
    private final Map<String, Kupon> kuponMap = new ConcurrentHashMap<>();

    public Kupon save(Kupon kupon) {
        kuponMap.put(kupon.getId(), kupon);
        return kupon;
    }

    public Kupon findById(String id) {
        return kuponMap.get(id);
    }

    public List<Kupon> findAll() {
        return new ArrayList<>(kuponMap.values());
    }

    public void deleteById(String id) {
        kuponMap.remove(id);
    }

    public List<Kupon> getActiveKupon() {
        List<Kupon> activeKuponList = new ArrayList<>();
        for (Kupon kupon : kuponMap.values()) {
            if (kupon.getBatasPemakaian() > 0) {
                activeKuponList.add(kupon);
            }
        }
        return activeKuponList;
    }

    public List<Kupon> getInactiveKupon() {
        List<Kupon> inactiveKuponList = new ArrayList<>();
        for (Kupon kupon : kuponMap.values()) {
            if (kupon.getBatasPemakaian() <= 0) {
                inactiveKuponList.add(kupon);
            }
        }
        return inactiveKuponList;
    }

    public List<Kupon> getAllKupon() {
        List<Kupon> allKuponList = new ArrayList<>();
        for (Kupon kupon : kuponMap.values()) {
            allKuponList.add(kupon);
        }
        return allKuponList;
    }
}
