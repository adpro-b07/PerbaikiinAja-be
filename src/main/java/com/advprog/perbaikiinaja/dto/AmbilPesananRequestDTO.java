package com.advprog.perbaikiinaja.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AmbilPesananRequestDTO {

    @NotNull(message = "Estimasi harga tidak boleh kosong")
    @Min(value = 1000, message = "Harga minimal 1000")
    private Long estimasiHarga;

    @NotNull(message = "Estimasi waktu tidak boleh kosong")
    @Min(value = 1, message = "Estimasi waktu minimal 1 hari")
    private Integer estimasiWaktu;

    // Getter Setter
    public Long getEstimasiHarga() {
        return estimasiHarga;
    }

    public void setEstimasiHarga(Long estimasiHarga) {
        this.estimasiHarga = estimasiHarga;
    }

    public Integer getEstimasiWaktu() {
        return estimasiWaktu;
    }

    public void setEstimasiWaktu(Integer estimasiWaktu) {
        this.estimasiWaktu = estimasiWaktu;
    }
}
