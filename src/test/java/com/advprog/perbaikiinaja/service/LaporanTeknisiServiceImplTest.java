package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.model.PaymentMethod;
import com.advprog.perbaikiinaja.model.Pesanan;
import com.advprog.perbaikiinaja.model.LaporanTeknisi;
import com.advprog.perbaikiinaja.repository.LaporanTeknisiRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LaporanTeknisiServiceImplTest {

    @Mock
    private LaporanTeknisiRepository laporanTeknisiRepository;

    @Mock
    private PesananService pesananService;

    @InjectMocks
    private LaporanTeknisiServiceImpl laporanTeknisiService;

    private Pesanan dummyPesanan;
    private LaporanTeknisi dummyLaporanTeknisi;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        PaymentMethod metodePembayaran = new PaymentMethod("Gopay");

        dummyPesanan = new Pesanan(
                "Laptop",
                "Rusak total",
                "DISKON10",
                "pengguna@email.com",
                "teknisi@email.com",
                metodePembayaran
        );
        dummyPesanan.setId(1L);
        
        dummyLaporanTeknisi = new LaporanTeknisi("Mengganti LCD dan motherboard", dummyPesanan);
    }

    @Test
    public void testCreateLaporanTeknisi_Success() {
        when(pesananService.findById(1L)).thenReturn(dummyPesanan);
        when(laporanTeknisiRepository.findByPesanan_Id(1L)).thenReturn(Optional.empty());
        when(laporanTeknisiRepository.save(any(LaporanTeknisi.class))).thenReturn(dummyLaporanTeknisi);

        LaporanTeknisi result = laporanTeknisiService.createLaporanTeknisi("Mengganti LCD dan motherboard", 1L);

        assertNotNull(result);
        assertEquals("Mengganti LCD dan motherboard", result.getLaporan());
        verify(laporanTeknisiRepository).save(any(LaporanTeknisi.class));
    }

    @Test
    public void testCreateLaporanTeknisi_LaporanTeknisiAlreadyExists() {
        when(pesananService.findById(1L)).thenReturn(dummyPesanan);
        when(laporanTeknisiRepository.findByPesanan_Id(1L)).thenReturn(Optional.of(dummyLaporanTeknisi));

        Exception exception = assertThrows(RuntimeException.class, () ->
                laporanTeknisiService.createLaporanTeknisi("Mengganti LCD dan motherboard", 1L)
        );
        assertEquals("Technician Report already exists for this pesanan", exception.getMessage());
    }

    @Test
    public void testUpdateLaporanteknisi_Success() {
        when(laporanTeknisiRepository.findByPesanan_Id(1L)).thenReturn(Optional.of(dummyLaporanTeknisi));
        when(laporanTeknisiRepository.save(any(LaporanTeknisi.class))).thenReturn(dummyLaporanTeknisi);

        LaporanTeknisi result = laporanTeknisiService.updateLaporanTeknisi(1L, "Mengganti LCD, motherboard, dan baterai");

        assertEquals("Mengganti LCD, motherboard, dan baterai", result.getLaporan());
        verify(laporanTeknisiRepository).save(any(LaporanTeknisi.class));
    }

    @Test
    public void testUpdateLaporanTeknisi_LaporanTeknisiNotFound() {
        when(laporanTeknisiRepository.findByPesanan_Id(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
            laporanTeknisiService.updateLaporanTeknisi(1L, "Laporan baru")
        );
        assertEquals("LaporanTeknisi not found with ID: 1", exception.getMessage());
    }

    @Test
    public void testGetLaporanTeknisiByPesananId_Success() {
        when(laporanTeknisiRepository.findByPesanan_Id(1L)).thenReturn(Optional.of(dummyLaporanTeknisi));

        LaporanTeknisi result = laporanTeknisiService.getLaporanTeknisiByPesananId(1L);
        assertEquals(dummyLaporanTeknisi, result);
        verify(laporanTeknisiRepository).findByPesanan_Id(1L);
    }

    @Test
    public void testGetAllLaporanTeknisi() {
        when(laporanTeknisiRepository.findAll()).thenReturn(List.of(dummyLaporanTeknisi));

        List<LaporanTeknisi> laporanTeknisi = laporanTeknisiService.getAllLaporanTeknisi();
        assertFalse(laporanTeknisi.isEmpty());
        assertEquals(1, laporanTeknisi.size());
        verify(laporanTeknisiRepository).findAll();
    }

    @Test
    public void testGetLaporanTeknisiByTeknisi() {
        when(laporanTeknisiRepository.findByPesanan_EmailTeknisi("teknisi@email.com"))
                .thenReturn(List.of(dummyLaporanTeknisi));

        List<LaporanTeknisi> laporanTeknisi = laporanTeknisiService.getLaporanTeknisiByTeknisi("teknisi@email.com");
        assertFalse(laporanTeknisi.isEmpty());
        assertEquals(1, laporanTeknisi.size());
        verify(laporanTeknisiRepository).findByPesanan_EmailTeknisi("teknisi@email.com");
    }

    @Test
    public void testGetLaporanTeknisiByPengguna() {
        when(laporanTeknisiRepository.findByPesanan_EmailPengguna("pengguna@email.com"))
                .thenReturn(List.of(dummyLaporanTeknisi));

        List<LaporanTeknisi> laporanTeknisi = laporanTeknisiService.getLaporanTeknisiByPengguna("pengguna@email.com");
        assertFalse(laporanTeknisi.isEmpty());
        assertEquals(1, laporanTeknisi.size());
        verify(laporanTeknisiRepository).findByPesanan_EmailPengguna("pengguna@email.com");
    }
}