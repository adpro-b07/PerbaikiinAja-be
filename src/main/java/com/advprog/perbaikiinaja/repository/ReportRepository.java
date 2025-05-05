package com.advprog.perbaikiinaja.repository;

import com.advprog.perbaikiinaja.model.Report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findByPesanan_Id(Long pesananId);
    List<Report> findByPesanan_EmailPengguna(String emailPengguna);
    List<Report> findByPesanan_EmailTeknisi(String emailTeknisi);
}