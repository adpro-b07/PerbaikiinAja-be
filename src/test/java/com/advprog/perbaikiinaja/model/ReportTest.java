package com.advprog.perbaikiinaja.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReportTest {

    @Test
    public void testConstructor() {
        PaymentMethod method = new PaymentMethod("Bank A");
        Pesanan pesanan = new Pesanan("Kulkas", "Tidak dingin", null, "u@mail.com", "t@mail.com", method);
        Report report = new Report("Bagus", 5, pesanan);

        assertEquals("Bagus", report.getUlasan());
        assertEquals(5, report.getRating());
        assertEquals(pesanan, report.getPesanan());
    }

    @Test
    public void testSetters() {
        PaymentMethod method = new PaymentMethod("Bank A");
        Pesanan pesanan = new Pesanan("Kulkas", "Tidak dingin", null, "u@mail.com", "t@mail.com", method);
        Report report = new Report("OK", 3, pesanan);

        report.setUlasan("Mantap");
        report.setRating(4);

        assertEquals("Mantap", report.getUlasan());
        assertEquals(4, report.getRating());
    }
}
