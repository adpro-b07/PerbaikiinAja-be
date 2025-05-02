package com.advprog.perbaikiinaja.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderStatusTest {

    @Test
    public void testEnumValues() {
        assertEquals("Menunggu Konfirmasi Teknisi", OrderStatus.WAITING_TEKNISI.getStatus());
        assertEquals("Menunggu Konfirmasi Pengguna", OrderStatus.WAITING_PENGGUNA.getStatus());
        assertEquals("Sedang Dikerjakan", OrderStatus.DIKERJAKAN.getStatus());
        assertEquals("Pesanan Selesai", OrderStatus.SELESAI.getStatus());
        assertEquals("Pesanan Dibatalkan", OrderStatus.DIBATALKAN.getStatus());
    }

    @Test
    public void testAllEnumNotNull() {
        for (OrderStatus status : OrderStatus.values()) {
            assertNotNull(status.getStatus());
        }
    }
}
