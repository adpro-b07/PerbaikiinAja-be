package com.advprog.perbaikiinaja.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

public class CreateLaporanTeknisiRequestDTO {
    
    @NotBlank(message = "Laporan wajib diisi")
    private String laporan;
    
    @Email(message = "Format email teknisi tidak valid")
    private String emailTeknisi;
    
    // Getters & Setters
    public String getLaporan() {
        return laporan;
    }
    
    public void setLaporan(String laporan) {
        this.laporan = laporan;
    }
    
    public String getEmailTeknisi() {
        return emailTeknisi;
    }
    
    public void setEmailTeknisi(String emailTeknisi) {
        this.emailTeknisi = emailTeknisi;
    }
}