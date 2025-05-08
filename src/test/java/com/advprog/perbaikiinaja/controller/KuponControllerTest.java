package com.advprog.perbaikiinaja.controller;

import com.advprog.perbaikiinaja.model.Kupon;
import com.advprog.perbaikiinaja.service.KuponService;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class KuponControllerTest {
    
    @Mock
    private KuponService kuponService;
    
    @InjectMocks
    private KuponController kuponController;
    
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Kupon dummyKupon;
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(kuponController).build();
        objectMapper = new ObjectMapper();
        
        dummyKupon = new Kupon("DISKON10", 10, 5);
        dummyKupon.setId("f47ac10b-58cc-4372-a567-0e02b2c3d479");
    }
    
    @Test
    public void testGetAllKupon() throws Exception {
        List<Kupon> kuponList = Arrays.asList(
            dummyKupon,
            new Kupon("DISKON20", 20, 3)
        );
        
        when(kuponService.getAllKupon()).thenReturn(kuponList);
        
        mockMvc.perform(get("/api/kupon"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].kodeKupon").value("DISKON10"))
            .andExpect(jsonPath("$[1].kodeKupon").value("DISKON20"));
        
        verify(kuponService).getAllKupon();
    }
    
    @Test
    public void testGetKuponByKode() throws Exception {
        when(kuponService.getKuponByKode("DISKON10")).thenReturn(dummyKupon);
        
        mockMvc.perform(get("/api/kupon/DISKON10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.kodeKupon").value("DISKON10"))
            .andExpect(jsonPath("$.potongan").value(10))
            .andExpect(jsonPath("$.batasPemakaian").value(5));
        
        verify(kuponService).getKuponByKode("DISKON10");
    }
    
    @Test
    public void testGetKuponByKodeNotFound() throws Exception {
        when(kuponService.getKuponByKode("NONEXISTENT"))
            .thenThrow(new RuntimeException("Kupon dengan kode NONEXISTENT tidak ditemukan"));
        
        mockMvc.perform(get("/api/kupon/NONEXISTENT"))
            .andExpect(status().isNotFound());
        
        verify(kuponService).getKuponByKode("NONEXISTENT");
    }
    
    @Test
    public void testCreateKupon() throws Exception {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("kodeKupon", "NEWDISKON");
        requestBody.put("potongan", 15);
        requestBody.put("batasPemakaian", 10);
        
        when(kuponService.createKupon("NEWDISKON", 15, 10)).thenReturn(dummyKupon);
        
        mockMvc.perform(post("/api/kupon/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.kodeKupon").value("DISKON10"));
        
        verify(kuponService).createKupon("NEWDISKON", 15, 10);
    }
    
    @Test
    public void testCreateKuponInvalidInput() throws Exception {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("kodeKupon", "INVALID");
        requestBody.put("potongan", -5);
        requestBody.put("batasPemakaian", 10);
        
        when(kuponService.createKupon(anyString(), anyInt(), anyInt()))
            .thenThrow(new IllegalArgumentException("Diskon harus lebih dari 0"));
        
        mockMvc.perform(post("/api/kupon/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    public void testUpdateKupon() throws Exception {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("potongan", 25);
        requestBody.put("batasPemakaian", 8);
        
        Kupon updatedKupon = new Kupon("DISKON10", 25, 8);
        updatedKupon.setId(dummyKupon.getId());
        
        when(kuponService.updateKupon("DISKON10", 25, 8)).thenReturn(updatedKupon);
        
        mockMvc.perform(put("/api/kupon/update/DISKON10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.potongan").value(25))
            .andExpect(jsonPath("$.batasPemakaian").value(8));
        
        verify(kuponService).updateKupon("DISKON10", 25, 8);
    }
    
    @Test
    public void testUpdateKuponNotFound() throws Exception {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("potongan", 25);
        requestBody.put("batasPemakaian", 8);
        
        when(kuponService.updateKupon("NONEXISTENT", 25, 8))
            .thenThrow(new RuntimeException("Kupon dengan kode NONEXISTENT tidak ditemukan"));
        
        mockMvc.perform(put("/api/kupon/update/NONEXISTENT")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
            .andExpect(status().isNotFound());
    }
    
    @Test
    public void testDeleteKupon() throws Exception {
        doNothing().when(kuponService).deleteKupon("DISKON10");
        
        mockMvc.perform(delete("/api/kupon/delete/DISKON10"))
            .andExpect(status().isOk());
        
        verify(kuponService).deleteKupon("DISKON10");
    }
    
    @Test
    public void testDeleteKuponNotFound() throws Exception {
        doThrow(new RuntimeException("Kupon dengan kode NONEXISTENT tidak ditemukan"))
            .when(kuponService).deleteKupon("NONEXISTENT");
        
        mockMvc.perform(delete("/api/kupon/delete/NONEXISTENT"))
            .andExpect(status().isNotFound());
        
        verify(kuponService).deleteKupon("NONEXISTENT");
    }
    
    @Test
    public void testGetActiveKupon() throws Exception {
        List<Kupon> activeKupons = Arrays.asList(dummyKupon);
        
        when(kuponService.getActiveKupon()).thenReturn(activeKupons);
        
        mockMvc.perform(get("/api/kupon/active"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].kodeKupon").value("DISKON10"));
        
        verify(kuponService).getActiveKupon();
    }
    
    @Test
    public void testGetInactiveKupon() throws Exception {
        Kupon inactiveKupon = new Kupon("EXPIRED", 5, 0);
        List<Kupon> inactiveKupons = Arrays.asList(inactiveKupon);
        
        when(kuponService.getInactiveKupon()).thenReturn(inactiveKupons);
        
        mockMvc.perform(get("/api/kupon/inactive"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].kodeKupon").value("EXPIRED"));
        
        verify(kuponService).getInactiveKupon();
    }
}