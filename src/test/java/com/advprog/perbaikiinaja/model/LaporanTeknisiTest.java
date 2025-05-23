package com.advprog.perbaikiinaja.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LaporanTeknisiTest {

    @Test
    public void testConstructor() {
        PaymentMethod method = new PaymentMethod("Bank A");
        Pesanan pesanan = new Pesanan("Kulkas", "Tidak dingin", null, "u@mail.com", "t@mail.com", method);
        LaporanTeknisi laporanTeknisi = new LaporanTeknisi("Ganti kompresor", pesanan);        
        
        assertEquals("Ganti kompresor", laporanTeknisi.getLaporan());
        assertEquals(pesanan, laporanTeknisi.getPesanan());
    }

    @Test
    public void testSetters() {
        PaymentMethod method = new PaymentMethod("Bank A");
        Pesanan pesanan = new Pesanan("Kulkas", "Tidak dingin", null, "u@mail.com", "t@mail.com", method);
        LaporanTeknisi laporanTeknisi = new LaporanTeknisi("Ganti kompresor", pesanan);    
        
        laporanTeknisi.setLaporan("Ganti filter");
        
        assertEquals("Ganti filter", laporanTeknisi.getLaporan());
        assertEquals(pesanan, laporanTeknisi.getPesanan());
    }
}
