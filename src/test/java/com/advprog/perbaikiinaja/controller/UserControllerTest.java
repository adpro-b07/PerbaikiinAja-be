package com.advprog.perbaikiinaja.controller;

import com.advprog.perbaikiinaja.model.Pengguna;
import com.advprog.perbaikiinaja.model.Teknisi;
import com.advprog.perbaikiinaja.model.User;
import com.advprog.perbaikiinaja.service.PenggunaService;
import com.advprog.perbaikiinaja.service.TeknisiService;
import com.advprog.perbaikiinaja.service.UserService;
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
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {
    
    @Mock
    private UserService userService;
    
    @Mock
    private PenggunaService penggunaService;
    
    @Mock
    private TeknisiService teknisiService;
    
    @InjectMocks
    private UserController userController;
    
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private User dummyPengguna;
    private User dummyTeknisi;
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
        
        dummyPengguna = new Pengguna(
            UUID.randomUUID().toString(),
            "Test Pengguna",
            "pengguna@example.com",
            "password123",
            "08123456789",
            "Jalan Pengguna No. 123"
        );
        
        dummyTeknisi = new Teknisi(
            UUID.randomUUID().toString(),
            "Test Teknisi",
            "teknisi@example.com",
            "password456",
            "08987654321",
            "Jalan Teknisi No. 456"
        );
    }
    
    @Test
    public void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(Arrays.asList(dummyPengguna, dummyTeknisi));
        
        mockMvc.perform(get("/api/user"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].email").value("pengguna@example.com"))
            .andExpect(jsonPath("$[1].email").value("teknisi@example.com"));
        
        verify(userService).getAllUsers();
    }
    
    @Test
    public void testLogin() throws Exception {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", "pengguna@example.com");
        credentials.put("password", "password123");
        
        when(userService.getUserByEmailAndPassword("pengguna@example.com", "password123"))
            .thenReturn(dummyPengguna);
        
        mockMvc.perform(post("/api/user/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(credentials)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value("pengguna@example.com"))
            .andExpect(jsonPath("$.role").value("PENGGUNA"));
        
        verify(userService).getUserByEmailAndPassword("pengguna@example.com", "password123");
    }
    
    @Test
    public void testGetUserByEmail() throws Exception {
        when(userService.findByEmail("pengguna@example.com")).thenReturn(dummyPengguna);
        
        mockMvc.perform(get("/api/user/get/pengguna@example.com"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value("pengguna@example.com"));
        
        verify(userService).findByEmail("pengguna@example.com");
    }
    
    @Test
    public void testCreatePengguna() throws Exception {
        Map<String, String> userData = new HashMap<>();
        userData.put("namaLengkap", "New Pengguna");
        userData.put("email", "new.pengguna@example.com");
        userData.put("password", "newpassword");
        userData.put("noTelp", "081234567890");
        userData.put("alamat", "Jalan Baru No. 789");
        userData.put("role", "PENGGUNA");
        
        when(penggunaService.createPengguna(
            anyString(), anyString(), anyString(), anyString(), anyString()))
            .thenReturn(dummyPengguna);
        
        mockMvc.perform(post("/api/user/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userData)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.role").value("PENGGUNA"));
        
        verify(penggunaService).createPengguna(
            anyString(), anyString(), anyString(), anyString(), anyString());
    }
    
    @Test
    public void testCreateTeknisi() throws Exception {
        Map<String, String> userData = new HashMap<>();
        userData.put("namaLengkap", "New Teknisi");
        userData.put("email", "new.teknisi@example.com");
        userData.put("password", "newpassword");
        userData.put("noTelp", "089876543210");
        userData.put("alamat", "Jalan Teknisi Baru No. 789");
        userData.put("role", "TEKNISI");
        
        when(teknisiService.createTeknisi(
            anyString(), anyString(), anyString(), anyString(), anyString()))
            .thenReturn(dummyTeknisi);
        
        mockMvc.perform(post("/api/user/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userData)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.role").value("Teknisi"));
        
        verify(teknisiService).createTeknisi(
            anyString(), anyString(), anyString(), anyString(), anyString());
    }
    
    @Test
    public void testLoginInvalidCredentials() throws Exception {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", "wrong@example.com");
        credentials.put("password", "wrongpass");
        
        when(userService.getUserByEmailAndPassword("wrong@example.com", "wrongpass"))
            .thenThrow(new RuntimeException("User not found"));
        
        mockMvc.perform(post("/api/user/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(credentials)))
            .andExpect(status().isInternalServerError());
        
        verify(userService).getUserByEmailAndPassword("wrong@example.com", "wrongpass");
    }
    
    @Test
    public void testGetUserByEmailNotFound() throws Exception {
        when(userService.findByEmail("notfound@example.com")).thenReturn(null);
        
        mockMvc.perform(get("/api/user/get/notfound@example.com"))
            .andExpect(status().isNotFound());
        
        verify(userService).findByEmail("notfound@example.com");
    }
}