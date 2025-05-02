package com.advprog.perbaikiinaja.enums;

public enum OrderStatus {
    WAITING_TEKNISI("Menunggu Konfirmasi Teknisi"),
    WAITING_PENGGUNA("Menunggu Konfirmasi Pengguna"),
    DIKERJAKAN("Sedang Dikerjakan"),
    SELESAI("Pesanan Selesai"),
    DIBATALKAN("Pesanan Dibatalkan");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
