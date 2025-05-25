package com.advprog.perbaikiinaja.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateStatusRequestDTO {

    @NotBlank(message = "Status tidak boleh kosong")
    private String status;

    // Getter Setter
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
