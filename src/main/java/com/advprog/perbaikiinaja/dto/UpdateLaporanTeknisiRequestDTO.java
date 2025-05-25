package com.advprog.perbaikiinaja.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateLaporanTeknisiRequestDTO {
    
    @NotBlank(message = "Laporan wajib diisi")
    private String laporan;
    
    // Getters & Setters
    public String getLaporan() {
        return laporan;
    }
    
    public void setLaporan(String laporan) {
        this.laporan = laporan;
    }
}