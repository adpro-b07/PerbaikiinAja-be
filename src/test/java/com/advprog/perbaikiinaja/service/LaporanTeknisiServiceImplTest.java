package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.model.LaporanTeknisi;
import com.advprog.perbaikiinaja.model.Pesanan;
import com.advprog.perbaikiinaja.repository.LaporanTeknisiRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

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

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        dummyPesanan = new Pesanan();
        dummyPesanan.setId(1L);
    }

    @Test
    public void testCreateLaporanTeknisi() {
        String deskripsi = "Ganti layar dan cek hardware";
        LaporanTeknisi expectedLaporan = new LaporanTeknisi(deskripsi, dummyPesanan);

        when(pesananService.getPesananById(1L)).thenReturn(dummyPesanan);
        when(laporanTeknisiRepository.save(any(LaporanTeknisi.class))).thenReturn(expectedLaporan);

        LaporanTeknisi result = laporanTeknisiService.createLaporanTeknisi(1L, deskripsi);

        assertEquals(deskripsi, result.getDeskripsi());
        assertEquals(dummyPesanan, result.getPesanan());

        verify(pesananService).getPesananById(1L);
        verify(laporanTeknisiRepository).save(any(LaporanTeknisi.class));
    }
}
