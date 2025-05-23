package com.advprog.perbaikiinaja.repository;

import com.advprog.perbaikiinaja.model.LaporanTeknisi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LaporanTeknisiRepository extends JpaRepository<LaporanTeknisi, Long> {
    Optional<LaporanTeknisi> findByPesanan_Id(Long pesananId);
    List<LaporanTeknisi> findByPesanan_EmailPengguna(String emailPengguna);
    List<LaporanTeknisi> findByPesanan_EmailTeknisi(String emailTeknisi);
}