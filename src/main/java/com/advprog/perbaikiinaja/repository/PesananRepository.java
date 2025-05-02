package com.advprog.perbaikiinaja.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;
import com.advprog.perbaikiinaja.model.Pesanan;

@Repository
public class PesananRepository {
    private final Map<Long, Pesanan> listPesanan = new ConcurrentHashMap<>();

    public Pesanan save(Pesanan pesanan) {
        listPesanan.put(pesanan.getId(), pesanan);
        return pesanan;
    }

    public List<Pesanan> findAll() {
        return new ArrayList<>(listPesanan.values());
    }

    public Pesanan findById(long id) {
        return listPesanan.get(id);
    }

    public List<Pesanan> findByPengguna(String emailPengguna) {
        List<Pesanan> pesananList = new ArrayList<>();
        for (Pesanan pesanan : listPesanan.values()) {
            if (pesanan.getEmailPengguna().equals(emailPengguna)) {
                pesananList.add(pesanan);
            }
        }
        return pesananList;
    }

    public void deleteById(long id) {
        listPesanan.remove(id);
    }
}
