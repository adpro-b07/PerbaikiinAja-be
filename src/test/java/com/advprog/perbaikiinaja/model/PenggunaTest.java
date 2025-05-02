package com.advprog.perbaikiinaja.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PenggunaTest {

    @Test
    public void testConstructor() {
        Pengguna pengguna = new Pengguna("U001", "Budi", "budi@mail.com", "12345", "0891234567", "Jakarta");

        assertEquals("U001", pengguna.getId());
        assertEquals("PENGGUNA", pengguna.getRole());
        assertEquals("Jakarta", pengguna.getAlamat());
        assertNotNull(pengguna.getMetodePembayaran());
        assertTrue(pengguna.getMetodePembayaran().isEmpty());
    }

    @Test
    public void testSetters() {
        Pengguna pengguna = new Pengguna("U001", "Budi", "budi@mail.com", "12345", "0891234567", "Jakarta");

        pengguna.setAlamat("Bandung");
        pengguna.setNoTelp("081234567890");

        assertEquals("Bandung", pengguna.getAlamat());
        assertEquals("081234567890", pengguna.getNoTelp());
    }
}
