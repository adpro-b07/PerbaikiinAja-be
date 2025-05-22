package com.advprog.perbaikiinaja.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LaporanTeknisiTest {

    @Test
    public void testLaporanTeknisiConstructorAndGetterSetter() {
        Pesanan pesanan = new Pesanan();
        pesanan.setId(123L);

        LaporanTeknisi laporan = new LaporanTeknisi();
        laporan.setId("abc-123");
        laporan.setDeskripsi("Ganti layar");
        laporan.setPesanan(pesanan);

        assertEquals("abc-123", laporan.getId());
        assertEquals("Ganti layar", laporan.getDeskripsi());
        assertEquals(123L, laporan.getPesanan().getId());
    }

    @Test
    public void testLaporanTeknisiAllArgsConstructor() {
        Pesanan pesanan = new Pesanan();
        pesanan.setId(999L);

        LaporanTeknisi laporan = new LaporanTeknisi("Perbaiki keyboard", pesanan);

        assertEquals("Perbaiki keyboard", laporan.getDeskripsi());
        assertEquals(999L, laporan.getPesanan().getId());
    }
}
