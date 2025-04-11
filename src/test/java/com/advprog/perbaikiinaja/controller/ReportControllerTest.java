package com.advprog.perbaikiinaja.controller;

import com.advprog.perbaikiinaja.model.Report;
import com.advprog.perbaikiinaja.service.ReportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
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

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        reset(reportService);
    }

    @Test
    public void testGetAllReports() throws Exception {
        List<Report> dummyList = List.of(
                new Report("R1", "TECH1", "Kerusakan ringan", "WAITING", LocalDateTime.now()),
                new Report("R2", "TECH2", "Rusak parah", "DONE", LocalDateTime.now())
        );

        when(reportService.findAllReports()).thenReturn(dummyList);

        mockMvc.perform(get("/api/reports"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetReportById() throws Exception {
        Report report = new Report("RPT1", "TECH42", "Kipas bunyi keras", "WAITING", LocalDateTime.now());

        when(reportService.findReportById("RPT1")).thenReturn(Optional.of(report));

        mockMvc.perform(get("/api/reports/RPT1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.technicianId").value("TECH42"));
    }
}
