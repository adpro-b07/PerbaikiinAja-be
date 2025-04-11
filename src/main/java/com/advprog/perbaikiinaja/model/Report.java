package com.advprog.perbaikiinaja.model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Report {
    private String id;
    private String technicianId;  
    private String description;
    private String status;        
    private LocalDateTime createdAt;

    public Report(String id, String technicianId, String description, String status, LocalDateTime createdAt) {
    this.id = id;
    this.technicianId = technicianId;
    
    if (description == null || description.isEmpty()) {
        throw new IllegalArgumentException("Deskripsi tidak boleh kosong");
    }
    if (status == null) {
        throw new IllegalArgumentException("Status laporan tidak boleh null");
    }
    this.description = description;
    this.status = status;
    this.createdAt = createdAt;
    }

}
