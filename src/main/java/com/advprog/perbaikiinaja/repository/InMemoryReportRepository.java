package com.advprog.perbaikiinaja.repository;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryReportRepository extends ReportRepository {
    // Karena method di parent class sudah tepat, 
    // kita tidak perlu menimpa method lagi di sini
    // Class ini tetap diperlukan untuk pengujian
}
