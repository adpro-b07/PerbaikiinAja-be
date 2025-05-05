package com.advprog.perbaikiinaja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.advprog.perbaikiinaja.model.Pesanan;

@Repository
public interface PesananRepository extends JpaRepository<Pesanan, Long> {
} 
