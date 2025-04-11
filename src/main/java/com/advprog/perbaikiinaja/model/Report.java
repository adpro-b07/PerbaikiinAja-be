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
    }

}
