package com.advprog.perbaikiinaja.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdminTest {

    @Test
    public void testAdminConstructor() {
        Admin admin = new Admin("ADM001", "Admin Test", "admin@example.com", "admin123", "08123456789");

        assertEquals("ADM001", admin.getId());
        assertEquals("Admin Test", admin.getNamaLengkap());
        assertEquals("admin@example.com", admin.getEmail());
        assertEquals("admin123", admin.getPassword());
        assertEquals("08123456789", admin.getNoTelp());
        assertEquals("ADMIN", admin.getRole());
    }

    @Test
    public void testSetters() {
        Admin admin = new Admin("ADM001", "Admin Test", "admin@example.com", "admin123", "08123456789");
        admin.setNamaLengkap("Admin Updated");
        admin.setPassword("newpass");

        assertEquals("Admin Updated", admin.getNamaLengkap());
        assertEquals("newpass", admin.getPassword());
    }
}
