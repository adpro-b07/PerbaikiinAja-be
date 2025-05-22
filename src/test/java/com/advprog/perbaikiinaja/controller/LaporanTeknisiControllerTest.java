package com.advprog.perbaikiinaja.controller;

import com.advprog.perbaikiinaja.model.LaporanTeknisi;
import com.advprog.perbaikiinaja.model.Pesanan;
import com.advprog.perbaikiinaja.service.LaporanTeknisiService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class LaporanTeknisiControllerTest {

    @Mock
    private LaporanTeknisiService laporanTeknisiService;

    @InjectMocks
    private LaporanTeknisiController laporanTeknisiController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private LaporanTeknisi dummyLaporan;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(laporanTeknisiController).build();
        objectMapper = new ObjectMapper();

        Pesanan pesanan = new Pesanan();
        pesanan.setId(1L);

        dummyLaporan = new LaporanTeknisi("Ganti motherboard dan bersihkan debu", pesanan);
        dummyLaporan.setId(1L);
    }

    @Test
    public void testCreateLaporanTeknisi() throws Exception {
        Map<String, String> request = new HashMap<>();
        request.put("deskripsi", "Ganti motherboard dan bersihkan debu");

        when(laporanTeknisiService.createLaporanTeknisi(eq(1L), eq("Ganti motherboard dan bersihkan debu")))
                .thenReturn(dummyLaporan);

        mockMvc.perform(post("/api/laporan-teknisi/create/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.deskripsi").value("Ganti motherboard dan bersihkan debu"));

        verify(laporanTeknisiService).createLaporanTeknisi(1L, "Ganti motherboard dan bersihkan debu");
    }
}
