package com.advprog.perbaikiinaja.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KuponTest {

    @Test
    public void testConstructor() {
        Kupon kupon = new Kupon("DISKON50", 50000, 10);

        assertNotNull(kupon.getId());
        assertEquals("DISKON50", kupon.getKodeKupon());
        assertEquals(50000, kupon.getPotongan());
        assertEquals(10, kupon.getBatasPemakaian());
    }

    @Test
    public void testSettersAndGetters() {
        Kupon kupon = new Kupon("TEST", 10, 5);

        kupon.setKodeKupon("NEWCODE");
        kupon.setPotongan(30000);
        kupon.setBatasPemakaian(15);
        kupon.setId("NEWID");

        assertEquals("NEWCODE", kupon.getKodeKupon());
        assertEquals(30000, kupon.getPotongan());
        assertEquals(15, kupon.getBatasPemakaian());
        assertEquals("NEWID", kupon.getId());
    }
}
