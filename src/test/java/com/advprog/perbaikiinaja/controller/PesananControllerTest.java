package com.advprog.perbaikiinaja.controller;

import com.advprog.perbaikiinaja.enums.OrderStatus;
import com.advprog.perbaikiinaja.model.PaymentMethod;
import com.advprog.perbaikiinaja.model.Pesanan;
import com.advprog.perbaikiinaja.service.PesananService;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PesananControllerTest {

    @Mock
    private PesananService pesananService;

    @InjectMocks
    private PesananController pesananController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Pesanan dummyPesanan;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pesananController).build();
        objectMapper = new ObjectMapper();

        PaymentMethod paymentMethod = new PaymentMethod("Transfer Bank");
        paymentMethod.setId(1L);

        dummyPesanan = new Pesanan(
                "Laptop", 
                "Tidak menyala", 
                "DISKON10", 
                "pengguna@example.com", 
                "teknisi@example.com", 
                paymentMethod);
        dummyPesanan.setId(1L);
        dummyPesanan.setStatusPesanan(OrderStatus.WAITING_TEKNISI.getStatus());
        dummyPesanan.setHarga(150000);
    }

    @Test
    public void testCreatePesanan() throws Exception {
        Map<String, String> request = new HashMap<>();
        request.put("namaBarang", "Laptop");
        request.put("kondisiBarang", "Tidak menyala");
        request.put("kodeKupon", "DISKON10");
        request.put("emailPengguna", "pengguna@example.com");
        request.put("metodePembayaran", "Transfer Bank");

        when(pesananService.createPesanan(
                anyString(), anyString(), anyString(), anyString(), anyString())
        ).thenReturn(dummyPesanan);

        mockMvc.perform(post("/api/pesanan/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.namaBarang").value("Laptop"))
                .andExpect(jsonPath("$.kondisiBarang").value("Tidak menyala"));

        verify(pesananService).createPesanan(
                anyString(), anyString(), anyString(), anyString(), anyString());
    }

    @Test
    public void testGetAllPesanan() throws Exception {
        Pesanan pesanan2 = new Pesanan();
        pesanan2.setId(2L);
        pesanan2.setNamaBarang("Handphone");
        pesanan2.setKondisiBarang("Layar rusak");

        List<Pesanan> pesananList = Arrays.asList(dummyPesanan, pesanan2);

        when(pesananService.getAllPesanan()).thenReturn(pesananList);

        mockMvc.perform(get("/api/pesanan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].namaBarang").value("Laptop"))
                .andExpect(jsonPath("$[1].namaBarang").value("Handphone"));

        verify(pesananService).getAllPesanan();
    }

    @Test
    public void testGetPesananById() throws Exception {
        when(pesananService.getPesananById(1L)).thenReturn(dummyPesanan);

        mockMvc.perform(get("/api/pesanan/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.namaBarang").value("Laptop"))
                .andExpect(jsonPath("$.kondisiBarang").value("Tidak menyala"));

        verify(pesananService).getPesananById(1L);
    }

    @Test
    public void testUpdateStatusPesanan() throws Exception {
        Map<String, String> request = new HashMap<>();
        request.put("status", OrderStatus.DIKERJAKAN.name());

        Pesanan updatedPesanan = new Pesanan();
        updatedPesanan.setId(1L);
        updatedPesanan.setStatusPesanan(OrderStatus.DIKERJAKAN.getStatus());

        when(pesananService.updateStatusPesanan(
                anyLong(), anyString())
        ).thenReturn(updatedPesanan);

        mockMvc.perform(put("/api/pesanan/update-status/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusPesanan").value(OrderStatus.DIKERJAKAN.getStatus()));

        verify(pesananService).updateStatusPesanan(eq(1L), anyString());
    }

    @Test
    public void testDeletePesanan_Success() throws Exception {
        mockMvc.perform(delete("/api/pesanan/delete/1"))
                .andExpect(status().isOk());
        verify(pesananService).deletePesanan(1L);
    }

    @Test
    public void testDeletePesanan_NotFound() throws Exception {
        doThrow(new RuntimeException()).when(pesananService).deletePesanan(99L);
        mockMvc.perform(delete("/api/pesanan/delete/99"))
                .andExpect(status().isNotFound());
        verify(pesananService).deletePesanan(99L);
    }

    @Test
    public void testAmbilPesanan_Success() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("estimasiHarga", 200000L);
        request.put("estimasiWaktu", 3);

        when(pesananService.ambilPesanan(1L, 200000L, 3)).thenReturn(dummyPesanan);

        mockMvc.perform(post("/api/pesanan/ambil-pesanan/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.namaBarang").value("Laptop"));

        verify(pesananService).ambilPesanan(1L, 200000L, 3);
    }

    @Test
    public void testAmbilPesanan_BadRequest_InvalidNumber() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("estimasiHarga", "invalid");
        request.put("estimasiWaktu", 3);

        mockMvc.perform(post("/api/pesanan/ambil-pesanan/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAmbilPesanan_BadRequest_RuntimeException() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("estimasiHarga", 200000L);
        request.put("estimasiWaktu", 3);

        when(pesananService.ambilPesanan(1L, 200000L, 3)).thenThrow(new RuntimeException());

        mockMvc.perform(post("/api/pesanan/ambil-pesanan/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}