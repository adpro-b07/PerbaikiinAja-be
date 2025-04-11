package com.advprog.perbaikiinaja.repository;

import com.advprog.perbaikiinaja.model.Report;

import java.util.List;
import java.util.Optional;

public interface ReportRepository {
    Report save(Report report);
    Optional<Report> findById(String id);
    List<Report> findAll();
    void deleteById(String id);
}
