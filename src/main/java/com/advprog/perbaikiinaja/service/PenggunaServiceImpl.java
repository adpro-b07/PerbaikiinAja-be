package com.advprog.perbaikiinaja.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.advprog.perbaikiinaja.enums.OrderStatus;
import com.advprog.perbaikiinaja.model.Pesanan;
import com.advprog.perbaikiinaja.model.User;
import com.advprog.perbaikiinaja.repository.UserRepository;

@Service
public class PenggunaServiceImpl implements PenggunaService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PesananService pesananService;

    @Override
    public User createPengguna(String namaLengkap, String email, String password, String noTelp, String alamat) {
        User teknisi = User.createUser("Pengguna", UUID.randomUUID().toString(), namaLengkap, email, password, noTelp, alamat);
        return userRepository.save(teknisi);
    }

    @Override
    public List<Pesanan> getPesananMenungguKonfirmasiPengguna(String emailPengguna) {
        return pesananService.findByPenggunaMenungguPengguna(emailPengguna);
    }

    @Override
    public void terimaPesanan(long idPesanan) {
        Pesanan pesanan = pesananService.findById(idPesanan);
        pesanan.setStatusPesanan(OrderStatus.DIKERJAKAN.getStatus());
        pesananService.createPesanan(pesanan);
    }

    @Override
    public void tolakPesanan(long idPesanan) {
        pesananService.deleteByPesananId(idPesanan);
    }
}
