package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.enums.OrderStatus;
import com.advprog.perbaikiinaja.model.*;
import com.advprog.perbaikiinaja.repository.PesananRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PesananServiceImplTest {

    @Mock private PesananRepository pesananRepository;
    @Mock private UserService userService;
    @Mock private PaymentMethodService paymentMethodService;
    @Mock private KuponService kuponService;

    @InjectMocks private PesananServiceImpl pesananService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePesananSuccess() {
        when(userService.findByEmail("pengguna@mail.com")).thenReturn(mock(User.class));
        when(paymentMethodService.getPaymentMethodByName("Bank")).thenReturn(new PaymentMethod("1", "Bank"));
        when(userService.getRandomTeknisi()).thenReturn(new Admin("1", "Teknisi", "t@mail.com", "123", "0812"));
        when(pesananRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Pesanan p = pesananService.createPesanan("TV", "Rusak", null, "pengguna@mail.com", "Bank");

        assertEquals("TV", p.getNamaBarang());
        verify(pesananRepository).save(any());
    }

    @Test
    public void testSetHargaSuccess() {
        Pesanan p = new Pesanan("TV", "Rusak", null, "u@mail.com", "t@mail.com", null);
        when(pesananRepository.findById(1L)).thenReturn(p);

        pesananService.setHarga(1L, 50000);

        assertEquals(50000, p.getHarga());
        verify(pesananRepository).save(p);
    }

    @Test
    public void testUpdateStatusPesananSuccess() {
        Pesanan pesanan = new Pesanan("TV", "Rusak", null, "user@mail.com", "teknisi@mail.com", null);
        when(pesananRepository.findById(1L)).thenReturn(pesanan);
        when(pesananRepository.save(any())).thenReturn(pesanan);

        Pesanan updated = pesananService.updateStatusPesanan(1L, OrderStatus.SELESAI.getStatus());

        assertNotNull(updated);
        assertEquals(OrderStatus.SELESAI.getStatus(), updated.getStatusPesanan());
        verify(pesananRepository).save(pesanan);
    }

    @Test
    public void testSetHargaWithNullPesanan() {
        when(pesananRepository.findById(1L)).thenReturn(null);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            pesananService.setHarga(1L, 50000);
        });

        assertEquals("Pesanan tidak ditemukan dengan ID: 1", exception.getMessage());
    }

    @Test
    public void testAmbilPesananSuccess() {
        Pesanan pesanan = new Pesanan("TV", "Rusak", null, "user@mail.com", "teknisi@mail.com", null);
        when(pesananRepository.findById(1L)).thenReturn(pesanan);
        when(pesananRepository.save(any())).thenReturn(pesanan);

        Pesanan result = pesananService.ambilPesanan(1L, 150000, 3);

        assertNotNull(result);
        assertEquals(150000, result.getHarga());
        assertEquals(OrderStatus.WAITING_PENGGUNA.getStatus(), result.getStatusPesanan());
        verify(pesananRepository).save(pesanan);
    }

    @Test
    public void testDeletePesananSuccess() {
        doNothing().when(pesananRepository).deleteById(1L);

        pesananService.deletePesanan(1L);

        verify(pesananRepository).deleteById(1L);
    }

    @Test
    public void testFindByTeknisiSuccess() {
        Pesanan pesanan1 = new Pesanan("TV", "Rusak", null, "user@mail.com", "teknisi@mail.com", null);
        Pesanan pesanan2 = new Pesanan("Laptop", "Rusak", null, "user2@mail.com", "teknisi@mail.com", null);
        when(pesananRepository.findAll()).thenReturn(List.of(pesanan1, pesanan2));

        List<Pesanan> result = pesananService.findByTeknisi("teknisi@mail.com");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("TV", result.get(0).getNamaBarang());
        assertEquals("Laptop", result.get(1).getNamaBarang());
    }

    @Test
    public void testDeletePesanan() {
        pesananService.deletePesanan(1L);
        verify(pesananRepository).deleteById(1L);
    }

    @Test
    public void testAmbilPesanan() {
        Pesanan pesanan = new Pesanan("TV", "Rusak", null, "u@mail.com", "t@mail.com", null);
        when(pesananRepository.findById(1L)).thenReturn(pesanan);
        when(pesananRepository.save(any())).thenReturn(pesanan);

        Pesanan result = pesananService.ambilPesanan(1L, 100000, 2);
        assertEquals(100000, result.getHarga());
        assertEquals(OrderStatus.WAITING_PENGGUNA.getStatus(), result.getStatusPesanan());
    }
}
