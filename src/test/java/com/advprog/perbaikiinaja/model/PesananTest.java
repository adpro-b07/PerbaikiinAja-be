package com.advprog.perbaikiinaja.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PesananTest {

    @Test
    public void testConstructor() {
        PaymentMethod method = new PaymentMethod("Bank A");
        Pesanan pesanan = new Pesanan("Laptop", "Rusak total", "KUPON123", "pengguna@mail.com", "teknisi@mail.com", method);

        assertEquals("Laptop", pesanan.getNamaBarang());
        assertEquals("Rusak total", pesanan.getKondisiBarang());
        assertEquals("KUPON123", pesanan.getKodeKupon());
        assertEquals("pengguna@mail.com", pesanan.getEmailPengguna());
        assertEquals("teknisi@mail.com", pesanan.getEmailTeknisi());
        assertEquals("Menunggu Konfirmasi Teknisi", pesanan.getStatusPesanan());
        assertEquals(-1, pesanan.getHarga());
        assertNotNull(pesanan.getTanggalServis());
        assertEquals(method, pesanan.getMetodePembayaran());
    }

    @Test
    public void testSetters() {
        PaymentMethod method = new PaymentMethod("Bank A");
        Pesanan pesanan = new Pesanan("Laptop", "Rusak total", "KUPON123", "pengguna@mail.com", "teknisi@mail.com", method);

        pesanan.setHarga(300000);
        pesanan.setTanggalSelesai("2025-05-02");

        assertEquals(300000, pesanan.getHarga());
        assertEquals("2025-05-02", pesanan.getTanggalSelesai());
    }
}
