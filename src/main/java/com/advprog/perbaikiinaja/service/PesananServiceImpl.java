package com.advprog.perbaikiinaja.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.advprog.perbaikiinaja.enums.OrderStatus;
import com.advprog.perbaikiinaja.model.Kupon;
import com.advprog.perbaikiinaja.model.PaymentMethod;
import com.advprog.perbaikiinaja.model.Pesanan;
import com.advprog.perbaikiinaja.model.User;
import com.advprog.perbaikiinaja.repository.PesananRepository;

import com.advprog.perbaikiinaja.observer.PesananObserver;
import com.advprog.perbaikiinaja.observer.UserNotifier;
import com.advprog.perbaikiinaja.observer.PesananPublisher;

@Service
public class PesananServiceImpl implements PesananService {

    @Autowired
    private PesananRepository pesananRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Autowired
    private KuponService kuponService;

    @Autowired
    private PesananPublisher pesananPublisher;

    @Override
    public void setHarga(long idPesanan, long hargaBaru) {
        Pesanan pesanan = pesananRepository.findById(idPesanan)
                .orElseThrow(() -> new RuntimeException("Pesanan tidak ditemukan dengan ID: " + idPesanan));
        if (pesanan != null) {
            pesanan.setHarga(hargaBaru);
            pesananRepository.save(pesanan);
        } else {
            throw new RuntimeException("Pesanan tidak ditemukan dengan ID: " + idPesanan);
        }
    }
    
    @Override
    public Pesanan createPesanan(String namaBarang, String kondisiBarang, String kodeKupon, String emailPengguna, String metodePembayaran) {
        if (userService.findByEmail(emailPengguna) == null) {
            throw new RuntimeException("Pengguna tidak ditemukan dengan email: " + emailPengguna);
        }
        if (paymentMethodService.getPaymentMethodByName(metodePembayaran) == null) {
            throw new RuntimeException("Metode pembayaran tidak ditemukan: " + metodePembayaran);
        }
        if (userService.getRandomTeknisi() == null) {
            throw new RuntimeException("Teknisi tidak ditemukan");
        }
        if (kodeKupon != null && kuponService.findByKodeKupon(kodeKupon) == null) {
            throw new RuntimeException("Kupon tidak ditemukan: " + kodeKupon);
        }
        
        User teknisi = userService.getRandomTeknisi();
        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodByName(metodePembayaran);

        Pesanan pesanan = new Pesanan(namaBarang, kondisiBarang, kodeKupon, emailPengguna, teknisi.getEmail(),  paymentMethod);
        return pesananRepository.save(pesanan);
    }

    @Override
    public Pesanan createPesanan(Pesanan pesanan) {
        return pesananRepository.save(pesanan);
    }

    @Override
    public void deletePesanan(long idPesanan) {
        pesananRepository.deleteById(idPesanan);
    }

    @Override
    public Iterable<Pesanan> getAllPesanan() {
        return pesananRepository.findAll();
    }

    @Override
    public Pesanan getPesananById(long idPesanan) {
        Pesanan pesanan = pesananRepository.findById(idPesanan)
                .orElseThrow(() -> new RuntimeException("Pesanan tidak ditemukan dengan ID: " + idPesanan));
        if (pesanan == null) {
            throw new RuntimeException("Pesanan not found with ID: " + idPesanan);
        }
        return pesanan;
    }

    @Override
    public Pesanan updateStatusPesanan(long idPesanan, String status) {
        Pesanan pesanan = pesananRepository.findById(idPesanan)
                .orElseThrow(() -> new RuntimeException("Pesanan tidak ditemukan dengan ID: " + idPesanan));
        if (pesanan == null) {
            throw new RuntimeException("Pesanan not found with ID: " + idPesanan);
        }
        
        pesananPublisher.updateStatus(pesanan, status);
        pesananRepository.save(pesanan);

        return pesanan;
    }

    @Override
    public List<Pesanan> findByTeknisi(String emailTeknisi) {
        List<Pesanan> pesananList = new ArrayList<>();
        for (Pesanan pesanan : pesananRepository.findAll()) {
            if (pesanan.getEmailTeknisi().equals(emailTeknisi)) {
                pesananList.add(pesanan);
            }
        }
        return pesananList;
    }

    @Override
    public List<Pesanan> findByTeknisiMenungguTeknisi(String emailTeknisi) {
        List<Pesanan> pesananList = new ArrayList<>();
        for (Pesanan pesanan : pesananRepository.findAll()) {
            if (pesanan.getEmailTeknisi().equals(emailTeknisi) && pesanan.getStatusPesanan().equals(OrderStatus.WAITING_TEKNISI.getStatus())) {
                pesananList.add(pesanan);
            }
        }
        return pesananList;
    }

    @Override
    public Pesanan findById(long id) {
        return pesananRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pesanan tidak ditemukan dengan ID: " + id));
    }

    @Override
    public List<Pesanan> findByPenggunaMenungguPengguna(String emailPengguna) {
        List<Pesanan> pesananList = new ArrayList<>();
        for (Pesanan pesanan : pesananRepository.findAll()) {
            if (pesanan.getEmailPengguna().equals(emailPengguna) && pesanan.getStatusPesanan().equals(OrderStatus.WAITING_PENGGUNA.getStatus())) {
                pesananList.add(pesanan);
            }
        }
        return pesananList;
    }

    @Override
    public void deleteByPesananId(long idPesanan) {
        pesananRepository.deleteById(idPesanan);
    }

    @Override
    public Pesanan updateHargaPesanan(long idPesanan, long newHarga) {
        Pesanan pesanan = pesananRepository.findById(idPesanan)
                        .orElse(null);
        if (pesanan == null) {
            throw new RuntimeException("Pesanan not found with ID: " + idPesanan);
        }
        Kupon kupon = null;
        long hargaFinal = 0;
        if (pesanan.getKodeKupon() != null) {
            kupon = kuponService.findByKodeKupon(pesanan.getKodeKupon());
        }
        if (kupon != null) {
            kuponService.decrementKuponUsage(kupon.getKodeKupon());
            hargaFinal = newHarga - (newHarga * kupon.getPotongan() / 100);
        }
        else {
            hargaFinal = newHarga;
        }
        
        pesanan.setHarga(hargaFinal);
        return pesananRepository.save(pesanan);
    }

   @Override
    public Pesanan ambilPesanan(long idPesanan, long estimasiHarga, int estimasiWaktu) {
        Pesanan pesanan = pesananRepository.findById(idPesanan)
                .orElseThrow(() -> new RuntimeException("Pesanan tidak ditemukan dengan ID: " + idPesanan));
        
        pesanan.setHarga(estimasiHarga);
        pesanan.setTanggalSelesai(java.time.LocalDate.now().plusDays(estimasiWaktu).toString());
        String updatedStatus = OrderStatus.WAITING_PENGGUNA.getStatus();
        pesananPublisher.updateStatus(pesanan, updatedStatus);
        
        return pesananRepository.save(pesanan);
    }
}