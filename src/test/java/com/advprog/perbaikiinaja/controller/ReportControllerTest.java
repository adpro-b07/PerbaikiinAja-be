package com.advprog.perbaikiinaja.controller;

import com.advprog.perbaikiinaja.model.Pesanan;
import com.advprog.perbaikiinaja.model.Report;
import com.advprog.perbaikiinaja.service.ReportService;
import com.advprog.perbaikiinaja.service.UserService;
import com.advprog.perbaikiinaja.model.User;
import com.advprog.perbaikiinaja.model.Pengguna;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ReportControllerTest {

    @Mock
    private ReportService reportService;

    @InjectMocks
    private ReportController reportController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Report dummyReport;
    private Pesanan dummyPesanan;
    private User dummyUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reportController).build();
        objectMapper = new ObjectMapper();

        dummyPesanan = new Pesanan();
        dummyPesanan.setId(1L);
        dummyPesanan.setEmailPengguna("pengguna@example.com");
        dummyPesanan.setEmailTeknisi("teknisi@example.com");

        dummyReport = new Report("Pelayanan bagus", 5, dummyPesanan);
        dummyReport.setId(1L);

        dummyUser = new Pengguna("1", "Pengguna", "pengguna@example.com", "password", "08123456789", "alamat");

        when(userService.findByEmail("pengguna@example.com")).thenReturn(dummyUser);
    }

    @Test
    public void testCreateReport() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("ulasan", "Pelayanan sangat baik");
        request.put("rating", 5);
        request.put("pesananId", 1L);
        request.put("emailPengguna", "pengguna@example.com");

        when(reportService.createReport(anyString(), anyInt(), anyLong())).thenReturn(dummyReport);

        mockMvc.perform(post("/api/report/create/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ulasan").value("Pelayanan bagus"))
                .andExpect(jsonPath("$.rating").value(5));

        verify(reportService).createReport(anyString(), anyInt(), anyLong());
    }

    @Test
    public void testUpdateReport() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("ulasan", "Pelayanan update");
        request.put("rating", 4);
        request.put("pesananId", 1L);

        Report updatedReport = new Report("Pelayanan update", 4, dummyPesanan);
        updatedReport.setId(1L);

        when(reportService.updateReport(anyLong(), anyString(), anyInt())).thenReturn(updatedReport);

        mockMvc.perform(put("/api/report/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ulasan").value("Pelayanan update"))
                .andExpect(jsonPath("$.rating").value(4));

        verify(reportService).updateReport(anyLong(), anyString(), anyInt());
    }

    @Test
    public void testGetAllReports() throws Exception {
        Report report2 = new Report("Bagus sekali", 4, dummyPesanan);
        report2.setId(2L);

        List<Report> reports = Arrays.asList(dummyReport, report2);

        when(reportService.getAllReports()).thenReturn(reports);

        mockMvc.perform(get("/api/report"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].ulasan").value("Pelayanan bagus"))
                .andExpect(jsonPath("$[1].ulasan").value("Bagus sekali"));

        verify(reportService).getAllReports();
    }

    @Test
    public void testGetReportsByTeknisi() throws Exception {
        Report report2 = new Report("Bagus sekali", 4, dummyPesanan);
        report2.setId(2L);

        List<Report> reports = Arrays.asList(dummyReport, report2);

        when(reportService.getReportsByTeknisi("teknisi@example.com")).thenReturn(reports);

        mockMvc.perform(get("/api/report/teknisi/teknisi@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].ulasan").value("Pelayanan bagus"))
                .andExpect(jsonPath("$[1].ulasan").value("Bagus sekali"));

        verify(reportService).getReportsByTeknisi("teknisi@example.com");
    }

    @Test
    public void testGetReportsByPengguna() throws Exception {
        List<Report> reports = Arrays.asList(dummyReport);

        when(reportService.getReportsByPengguna("pengguna@example.com")).thenReturn(reports);

        mockMvc.perform(get("/api/report/pengguna/pengguna@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].ulasan").value("Pelayanan bagus"));

        verify(reportService).getReportsByPengguna("pengguna@example.com");
    }

    @Test
    public void testCreateReportWithInvalidRating() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("ulasan", "Invalid rating");
        request.put("rating", 6);
        request.put("pesananId", 1L);

        when(reportService.createReport(anyString(), anyInt(), anyLong()))
            .thenThrow(new IllegalArgumentException("Rating must be between 1 and 5"));

        mockMvc.perform(post("/api/report/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetReportByNonExistentPesananId() throws Exception {
        when(reportService.getReportByPesananId(999L))
            .thenThrow(new RuntimeException("Report not found with pesanan ID: 999"));

        mockMvc.perform(get("/api/report/pesanan/999"))
                .andExpect(status().isNotFound());
    }
}