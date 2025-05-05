package com.advprog.perbaikiinaja.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.advprog.perbaikiinaja.model.Kupon;

@Repository
public interface KuponRepository extends JpaRepository<Kupon, String> {
}
