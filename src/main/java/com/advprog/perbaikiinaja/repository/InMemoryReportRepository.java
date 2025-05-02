package com.advprog.perbaikiinaja.repository;

import com.advprog.perbaikiinaja.model.Report;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryReportRepository extends ReportRepository {
    // Karena method di parent class sudah tepat, 
    // kita tidak perlu menimpa method lagi di sini
    // Class ini tetap diperlukan untuk pengujian
}
