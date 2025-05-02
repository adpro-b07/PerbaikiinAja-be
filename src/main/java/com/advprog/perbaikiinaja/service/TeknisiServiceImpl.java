package com.advprog.perbaikiinaja.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.advprog.perbaikiinaja.enums.OrderStatus;
import com.advprog.perbaikiinaja.model.Pesanan;
import com.advprog.perbaikiinaja.model.Report;
import com.advprog.perbaikiinaja.model.Teknisi;
import com.advprog.perbaikiinaja.model.User;
import com.advprog.perbaikiinaja.repository.UserRepository;

@Service
public class TeknisiServiceImpl implements TeknisiService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PesananService pesananService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private UserService userService;

    @Override
    public User createTeknisi(String namaLengkap, String email, String password, String noTelp, String alamat) {
        User teknisi = User.createUser("Teknisi", UUID.randomUUID().toString(), namaLengkap, email, password, noTelp, alamat);
        return userRepository.save(teknisi);
    }

    @Override
    public List<Pesanan> getAllPesananTeknisi(String emailTeknisi) {
        return pesananService.findByTeknisi(emailTeknisi);
    }

    @Override
    public List<Pesanan> getPesananMenungguKonfirmasiTeknisi(String emailTeknisi) {
        return pesananService.findByTeknisiMenungguTeknisi(emailTeknisi);
    }

    @Override
    public void selesaikanPesanan(long idPesanan) {
        Pesanan pesanan = pesananService.findById(idPesanan);
        pesanan.setStatusPesanan(OrderStatus.SELESAI.getStatus());
        User user = userService.findByEmail(pesanan.getEmailTeknisi());
        if (user instanceof Teknisi) {
            Teknisi teknisi = (Teknisi) user;
            teknisi.setTotalPekerjaanSelesai(teknisi.getTotalPekerjaanSelesai() + 1);
            teknisi.setTotalPenghasilan(teknisi.getTotalPenghasilan() + pesanan.getHarga());
        } else {
            throw new RuntimeException("User dengan email " + pesanan.getEmailTeknisi() + " bukan seorang Teknisi");
        }
    }

    @Override
    public int hitungRatingTeknisi(String emailTeknisi) {
        List<Report> reportList = reportService.findByTeknisi(emailTeknisi);
        int totalRating = 0;
        int count = 0;
        for (Report report : reportList) {
            totalRating += report.getRating();
            count++;
        }
        return count == 0 ? 0 : totalRating / count;
    }
}