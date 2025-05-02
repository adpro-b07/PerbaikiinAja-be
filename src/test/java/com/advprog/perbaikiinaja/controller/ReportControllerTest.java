package com.advprog.perbaikiinaja.controller;

import com.advprog.perbaikiinaja.model.Report;
import com.advprog.perbaikiinaja.service.ReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReportController.class)
public class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        reset(reportService);
    }

    @Test
    public void testGetAllReports() throws Exception {
        // Membuat Report menggunakan setter bukan konstruktor
        Report report1 = new Report();
        report1.setOrderId("R1");
        report1.setTechnicianId("TECH1");
        report1.setDescription("Kerusakan ringan");
        
        Report report2 = new Report();
        report2.setOrderId("R2");
        report2.setTechnicianId("TECH2");
        report2.setDescription("Rusak parah");
        
        List<Report> dummyList = new ArrayList<>();
        dummyList.add(report1);
        dummyList.add(report2);

        // Menggunakan method getReports() yang baru
        when(reportService.getReports()).thenReturn(dummyList);

        mockMvc.perform(get("/api/reports"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetReportById() throws Exception {
        // Membuat Report menggunakan setter bukan konstruktor
        Report report = new Report();
        report.setOrderId("RPT1");
        report.setTechnicianId("TECH42");
        report.setDescription("Kipas bunyi keras");

        // Menggunakan method findByOrderId() yang baru
        when(reportService.findByOrderId("RPT1")).thenReturn(Optional.of(report));

        mockMvc.perform(get("/api/reports/RPT1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.technicianId").value("TECH42"));
    }
}
