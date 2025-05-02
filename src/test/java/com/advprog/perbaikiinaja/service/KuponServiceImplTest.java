package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.model.Kupon;
import com.advprog.perbaikiinaja.repository.KuponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

public class KuponServiceImplTest {

    @Mock
    private KuponRepository kuponRepository;

    @InjectMocks
    private KuponServiceImpl kuponService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateKuponSuccess() {
        when(kuponRepository.findAll()).thenReturn(List.of());
        when(kuponRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Kupon kupon = kuponService.createKupon("DISKON10", 10000, 5);
        assertEquals("DISKON10", kupon.getKodeKupon());
        assertEquals(10000, kupon.getPotongan());
        assertEquals(5, kupon.getBatasPemakaian());
    }

    @Test
    public void testCreateKuponDuplicate() {
        Kupon existing = new Kupon("DISKON10", 10000, 5);
        when(kuponRepository.findAll()).thenReturn(List.of(existing));

        assertThrows(RuntimeException.class, () ->
                kuponService.createKupon("DISKON10", 15000, 2));
    }

    @Test
    public void testCreateKuponInvalidDiskon() {
        assertThrows(IllegalArgumentException.class, () ->
                kuponService.createKupon("ABC", 0, 5));
    }

    @Test
    public void testUpdateKuponSuccess() {
        Kupon kupon = new Kupon("KODE", 10000, 5);
        when(kuponRepository.findAll()).thenReturn(List.of(kupon));
        when(kuponRepository.save(any())).thenReturn(kupon);

        Kupon updated = kuponService.updateKupon("KODE", 15000, 10);
        assertEquals(15000, updated.getPotongan());
        assertEquals(10, updated.getBatasPemakaian());
    }

    @Test
    public void testDeleteKuponSuccess() {
        Kupon kupon = new Kupon("KODE", 10000, 5);
        kupon.setId("123");
        when(kuponRepository.findAll()).thenReturn(List.of(kupon));

        kuponService.deleteKupon("KODE");

        verify(kuponRepository).deleteById("123");
    }

    @Test
    public void testDecrementKuponUsage() {
        Kupon kupon = new Kupon("KODE", 10000, 3);
        when(kuponRepository.findAll()).thenReturn(List.of(kupon));
        when(kuponRepository.save(any())).thenReturn(kupon);

        kuponService.decrementKuponUsage("KODE");

        assertEquals(2, kupon.getBatasPemakaian());
    }

    @Test
    public void testDecrementKuponUsageHabis() {
        Kupon kupon = new Kupon("KODE", 10000, 0);
        when(kuponRepository.findAll()).thenReturn(List.of(kupon));

        assertThrows(RuntimeException.class, () ->
                kuponService.decrementKuponUsage("KODE"));
    }

    @Test
    public void testIncrementKuponUsage() {
        Kupon kupon = new Kupon("KODE", 10000, 1);
        when(kuponRepository.findAll()).thenReturn(List.of(kupon));
        when(kuponRepository.save(any())).thenReturn(kupon);

        kuponService.incrementKuponUsage("KODE");

        assertEquals(2, kupon.getBatasPemakaian());
    }
}
