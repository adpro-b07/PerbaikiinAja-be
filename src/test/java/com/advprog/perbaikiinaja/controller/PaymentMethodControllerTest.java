package com.advprog.perbaikiinaja.controller;

import com.advprog.perbaikiinaja.model.PaymentMethod;
import com.advprog.perbaikiinaja.service.PaymentMethodService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PaymentMethodControllerTest {
    
    @Mock
    private PaymentMethodService paymentMethodService;
    
    @InjectMocks
    private PaymentMethodController paymentMethodController;
    
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private PaymentMethod dummyPaymentMethod;
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentMethodController).build();
        objectMapper = new ObjectMapper();
        
        dummyPaymentMethod = new PaymentMethod("Bank Transfer");
        dummyPaymentMethod.setId(1L);
    }
    
    @Test
    public void testGetAllPaymentMethods() throws Exception {
        List<PaymentMethod> paymentMethods = Arrays.asList(
            dummyPaymentMethod,
            new PaymentMethod("OVO")
        );
        
        when(paymentMethodService.getAllPaymentMethods()).thenReturn(paymentMethods);
        
        mockMvc.perform(get("/api/payment"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].name").value("Bank Transfer"))
            .andExpect(jsonPath("$[1].name").value("OVO"));
        
        verify(paymentMethodService).getAllPaymentMethods();
    }
    
    
    @Test
    public void testCreatePaymentMethod() throws Exception {
        Map<String, String> request = new HashMap<>();
        request.put("name", "GoPay");
        
        when(paymentMethodService.createPaymentMethod("GoPay")).thenReturn(dummyPaymentMethod);
        
        mockMvc.perform(post("/api/payment/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Bank Transfer"));
        
        verify(paymentMethodService).createPaymentMethod("GoPay");
    }
    
    @Test
    public void testCreatePaymentMethod_AlreadyExists() throws Exception {
        Map<String, String> request = new HashMap<>();
        request.put("name", "GoPay");
        
        when(paymentMethodService.createPaymentMethod("GoPay"))
            .thenThrow(new RuntimeException("Payment method with name GoPay already exists."));
        
        mockMvc.perform(post("/api/payment/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
        
        verify(paymentMethodService).createPaymentMethod("GoPay");
    }
    
    
    @Test
    public void testDeletePaymentMethod() throws Exception {
        doNothing().when(paymentMethodService).deletePaymentMethod("Bank Transfer");
        
        mockMvc.perform(delete("/api/payment/delete/Bank Transfer"))
            .andExpect(status().isOk());
        
        verify(paymentMethodService).deletePaymentMethod("Bank Transfer");
    }
    
    @Test
    public void testDeletePaymentMethod_NotFound() throws Exception {
        doThrow(new RuntimeException("Payment method not found with Name: NonExistent"))
            .when(paymentMethodService).deletePaymentMethod("NonExistent");
        
        mockMvc.perform(delete("/api/payment/delete/NonExistent"))
            .andExpect(status().isNotFound());
        
        verify(paymentMethodService).deletePaymentMethod("NonExistent");
    }
}