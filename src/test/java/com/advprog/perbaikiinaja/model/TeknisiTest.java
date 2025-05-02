package com.advprog.perbaikiinaja.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TeknisiTest {

    @Test
    public void testConstructor() {
        Teknisi teknisi = new Teknisi("T001", "Ani", "ani@mail.com", "teknisi123", "0897654321", "Surabaya");

        assertEquals("T001", teknisi.getId());
        assertEquals("Ani", teknisi.getNamaLengkap());
        assertEquals("ani@mail.com", teknisi.getEmail());
        assertEquals("teknisi123", teknisi.getPassword());
        assertEquals("0897654321", teknisi.getNoTelp());
        assertEquals("Surabaya", teknisi.getAlamat());
        assertEquals(0, teknisi.getTotalPekerjaanSelesai());
        assertEquals(0, teknisi.getTotalPenghasilan());
        assertEquals("Teknisi", teknisi.getRole());
    }

    @Test
    public void testSetters() {
        Teknisi teknisi = new Teknisi("T001", "Ani", "ani@mail.com", "teknisi123", "0897654321", "Surabaya");

        teknisi.setAlamat("Bekasi");
        teknisi.setTotalPekerjaanSelesai(5);
        teknisi.setTotalPenghasilan(1500000);

        assertEquals("Bekasi", teknisi.getAlamat());
        assertEquals(5, teknisi.getTotalPekerjaanSelesai());
        assertEquals(1500000, teknisi.getTotalPenghasilan());
    }
}
