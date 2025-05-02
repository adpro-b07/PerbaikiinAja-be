package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.enums.OrderStatus;
import com.advprog.perbaikiinaja.model.Pesanan;
import com.advprog.perbaikiinaja.model.User;
import com.advprog.perbaikiinaja.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PenggunaServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PesananService pesananService;

    @InjectMocks
    private PenggunaServiceImpl penggunaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePengguna() {
        User dummyUser = mock(User.class);
        when(userRepository.save(any())).thenReturn(dummyUser);

        User result = penggunaService.createPengguna("Budi", "budi@gmail.com", "pass", "0812", "Jakarta");
        assertNotNull(result);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void testGetPesananMenungguKonfirmasiPengguna() {
        List<Pesanan> dummyList = List.of(new Pesanan("TV", "Rusak", null, "pengguna@mail.com", "teknisi@mail.com", null));
        when(pesananService.findByPenggunaMenungguPengguna("pengguna@mail.com")).thenReturn(dummyList);

        List<Pesanan> result = penggunaService.getPesananMenungguKonfirmasiPengguna("pengguna@mail.com");
        assertEquals(1, result.size());
    }

    @Test
    public void testTerimaPesanan() {
        Pesanan pesanan = new Pesanan("TV", "Rusak", null, "pengguna@mail.com", "teknisi@mail.com", null);
        when(pesananService.findById(1L)).thenReturn(pesanan);

        penggunaService.terimaPesanan(1L);

        assertEquals(OrderStatus.DIKERJAKAN.getStatus(), pesanan.getStatusPesanan());
        verify(pesananService).createPesanan(pesanan);
    }

    @Test
    public void testTolakPesanan() {
        penggunaService.tolakPesanan(99L);
        verify(pesananService).deleteByPesananId(99L);
    }
}
