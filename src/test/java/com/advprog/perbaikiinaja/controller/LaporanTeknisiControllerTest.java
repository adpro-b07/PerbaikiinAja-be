package com.advprog.perbaikiinaja.controller;

import com.advprog.perbaikiinaja.model.Pesanan;
import com.advprog.perbaikiinaja.model.LaporanTeknisi;
import com.advprog.perbaikiinaja.service.LaporanTeknisiService;
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

public class LaporanTeknisiControllerTest {

    @Mock
    private LaporanTeknisiService laporanTeknisiService;

    @InjectMocks
    private LaporanTeknisiController LaporanTeknisiController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private LaporanTeknisi dummyLaporanTeknisi;
    private Pesanan dummyPesanan;
    private User dummyUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(LaporanTeknisiController).build();
        objectMapper = new ObjectMapper();

        dummyPesanan = new Pesanan();
        dummyPesanan.setId(1L);
        dummyPesanan.setEmailPengguna("pengguna@example.com");
        dummyPesanan.setEmailTeknisi("teknisi@example.com");

        dummyLaporanTeknisi = new LaporanTeknisi("Memperbaiki HP dengan mengganti LCD", dummyPesanan);
        dummyLaporanTeknisi.setId(1L);

        dummyUser = new Pengguna("1", "Pengguna", "pengguna@example.com", "password", "08123456789", "alamat");

        when(userService.findByEmail("teknisi@example.com")).thenReturn(dummyUser);
    }

    @Test
    public void testCreateLaporanTeknisi() throws Exception {
    // Change from Map<String, Object> to Map<String, String> to match controller parameter type
    Map<String, String> request = new HashMap<>();
    request.put("laporan", "Memperbaiki HP dengan mengganti LCD");
    request.put("emailTeknisi", "teknisi@example.com");
    
    // Update mock to return a user with role "pengguna" to pass the role check in controller
    Pengguna teknisiUser = new Pengguna("1", "Teknisi", "teknisi@example.com", "password", "08123456789", "alamat");
    teknisiUser.setRole("Teknisi"); // Controller checks for this role specifically
    when(userService.findByEmail("teknisi@example.com")).thenReturn(teknisiUser);
    
    // Make sure the mocked LaporanTeknisi has the correct pesanan with matching emailTeknisi
    Pesanan pesananWithCorrectEmail = new Pesanan();
    pesananWithCorrectEmail.setId(1L);
    pesananWithCorrectEmail.setEmailTeknisi("teknisi@example.com");
    
    LaporanTeknisi mockedLaporan = new LaporanTeknisi("Memperbaiki HP dengan mengganti LCD", pesananWithCorrectEmail);
    mockedLaporan.setId(1L);
    
    when(laporanTeknisiService.createLaporanTeknisi(anyString(), anyLong())).thenReturn(mockedLaporan);

    mockMvc.perform(post("/api/laporan-teknisi/create/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.laporan").value("Memperbaiki HP dengan mengganti LCD"));

    verify(laporanTeknisiService).createLaporanTeknisi(anyString(), anyLong());
}

    @Test
    public void testUpdateLaporanTeknisi() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("laporan", "Laporan Teknisi update");
        request.put("pesananId", 1L);

        LaporanTeknisi updatedLaporanTeknisi = new LaporanTeknisi("Laporan Teknisi update", dummyPesanan);
        updatedLaporanTeknisi.setId(1L);

        when(laporanTeknisiService.updateLaporanTeknisi(anyLong(), anyString())).thenReturn(updatedLaporanTeknisi);

        mockMvc.perform(put("/api/laporan-teknisi/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.laporan").value("Laporan Teknisi update"));

        verify(laporanTeknisiService).updateLaporanTeknisi(anyLong(), anyString());
    }

    @Test
    public void testGetAllLaporanTeknisi() throws Exception {
        LaporanTeknisi laporanTeknisi2 = new LaporanTeknisi("Mengganti prosesor kulkas", dummyPesanan);
        laporanTeknisi2.setId(2L);

        List<LaporanTeknisi> LaporanTeknisis = Arrays.asList(dummyLaporanTeknisi, laporanTeknisi2);

       when(laporanTeknisiService.getAllLaporanTeknisi()).thenReturn(LaporanTeknisis);

        mockMvc.perform(get("/api/laporan-teknisi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                // Fix the expected value to match the first item in the list
                .andExpect(jsonPath("$[0].laporan").value("Memperbaiki HP dengan mengganti LCD"));

        verify(laporanTeknisiService).getAllLaporanTeknisi();
    }

    @Test
    public void testGetLaporanTeknisiByTeknisi() throws Exception {
        LaporanTeknisi laporanTeknisi2 =  new LaporanTeknisi("Memperbaiki HP dengan mengganti LCD", dummyPesanan);
        laporanTeknisi2.setId(2L);

        List<LaporanTeknisi> laporanTeknisi = Arrays.asList(dummyLaporanTeknisi, laporanTeknisi2);

        when(laporanTeknisiService.getLaporanTeknisiByTeknisi("teknisi@example.com")).thenReturn(laporanTeknisi);

        mockMvc.perform(get("/api/laporan-teknisi/teknisi/teknisi@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                // Fix the expected value to match the setup data
                .andExpect(jsonPath("$[0].laporan").value("Memperbaiki HP dengan mengganti LCD"));

        verify(laporanTeknisiService).getLaporanTeknisiByTeknisi("teknisi@example.com");
    }

    @Test
    public void testGetLaporanTeknisiByPengguna() throws Exception {
        List<LaporanTeknisi> LaporanTeknisis = Arrays.asList(dummyLaporanTeknisi);

        when(laporanTeknisiService.getLaporanTeknisiByPengguna("pengguna@example.com")).thenReturn(LaporanTeknisis);

        mockMvc.perform(get("/api/laporan-teknisi/pengguna/pengguna@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].laporan").value("Memperbaiki HP dengan mengganti LCD"));

        verify(laporanTeknisiService).getLaporanTeknisiByPengguna("pengguna@example.com");
    }

    @Test
    public void testGetLaporanTeknisiByNonExistentPesananId() throws Exception {
        when(laporanTeknisiService.getLaporanTeknisiByPesananId(999L))
            .thenThrow(new RuntimeException("LaporanTeknisi not found with pesanan ID: 999"));

        mockMvc.perform(get("/api/LaporanTeknisi/pesanan/999"))
                .andExpect(status().isNotFound());
    }
}