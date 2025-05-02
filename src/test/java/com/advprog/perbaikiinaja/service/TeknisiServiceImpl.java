package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.enums.OrderStatus;
import com.advprog.perbaikiinaja.model.Admin;
import com.advprog.perbaikiinaja.model.Pesanan;
import com.advprog.perbaikiinaja.model.Report;
import com.advprog.perbaikiinaja.model.Teknisi;
import com.advprog.perbaikiinaja.model.User;
import com.advprog.perbaikiinaja.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeknisiServiceImplTest {

    @InjectMocks
    private TeknisiServiceImpl teknisiService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PesananService pesananService;

    @Mock
    private ReportService reportService;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTeknisi() {
        String nama = "Teknisi A", email = "teknisi@example.com", password = "pass", noTelp = "0812", alamat = "Jl. ABC";
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = teknisiService.createTeknisi(nama, email, password, noTelp, alamat);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        assertEquals("Teknisi", result.getRole());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetAllPesananTeknisi() {
        String emailTeknisi = "teknisi@example.com";
        List<Pesanan> pesananList = Arrays.asList(mock(Pesanan.class), mock(Pesanan.class));
        when(pesananService.findByTeknisi(emailTeknisi)).thenReturn(pesananList);

        List<Pesanan> result = teknisiService.getAllPesananTeknisi(emailTeknisi);
        assertEquals(2, result.size());
        verify(pesananService, times(1)).findByTeknisi(emailTeknisi);
    }

    @Test
    void testGetPesananMenungguKonfirmasiTeknisi() {
        String email = "teknisi@example.com";
        when(pesananService.findByTeknisiMenungguTeknisi(email)).thenReturn(List.of(mock(Pesanan.class)));

        List<Pesanan> result = teknisiService.getPesananMenungguKonfirmasiTeknisi(email);
        assertEquals(1, result.size());
    }

    @Test
    void testSelesaikanPesanan() {
        long idPesanan = 1L;
        Pesanan pesanan = mock(Pesanan.class);
        when(pesanan.getEmailTeknisi()).thenReturn("teknisi@example.com");
        when(pesanan.getHarga()).thenReturn(100L);
        when(pesananService.findById(idPesanan)).thenReturn(pesanan);

        Teknisi teknisi = new Teknisi(
            UUID.randomUUID().toString(),
            "Teknisi Test",
            "teknisi@example.com",
            "password",
            "08123456789",
            "Jl. Test"
        );
        teknisi.setTotalPekerjaanSelesai(1);
        teknisi.setTotalPenghasilan(200);
        when(userService.findByEmail("teknisi@example.com")).thenReturn(teknisi);

        teknisiService.selesaikanPesanan(idPesanan);

        assertEquals(2, teknisi.getTotalPekerjaanSelesai());
        assertEquals(300, teknisi.getTotalPenghasilan());
        verify(pesanan).setStatusPesanan(OrderStatus.SELESAI.getStatus());
    }

    @Test
    void testSelesaikanPesananNotTeknisiShouldThrow() {
        long idPesanan = 2L;
        Pesanan pesanan = mock(Pesanan.class);
        when(pesanan.getEmailTeknisi()).thenReturn("user@example.com");
        when(pesananService.findById(idPesanan)).thenReturn(pesanan);
        
        // Use Admin instead of abstract User class
        User nonTeknisiUser = new Admin(
            UUID.randomUUID().toString(),
            "Admin Test", 
            "user@example.com", 
            "password", 
            "08123456789"
        );
        
        when(userService.findByEmail("user@example.com")).thenReturn(nonTeknisiUser);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            teknisiService.selesaikanPesanan(idPesanan);
        });

        assertTrue(ex.getMessage().contains("bukan seorang Teknisi"));
    }

    @Test
    void testHitungRatingTeknisi() {
        String email = "teknisi@example.com";
        
        // Create mocked objects for Reports with proper initialization
        Pesanan pesanan1 = mock(Pesanan.class);
        Pesanan pesanan2 = mock(Pesanan.class);
        
        Report r1 = new Report("Good service", 4, pesanan1); 
        Report r2 = new Report("Excellent work", 5, pesanan2);
        
        when(reportService.findByTeknisi(email)).thenReturn(List.of(r1, r2));

        int rating = teknisiService.hitungRatingTeknisi(email);
        assertEquals(4, rating); // (4+5)/2 = 4.5 dibulatkan ke 4
    }

    @Test
    void testHitungRatingTeknisiKosong() {
        String email = "kosong@example.com";
        when(reportService.findByTeknisi(email)).thenReturn(List.of());

        int rating = teknisiService.hitungRatingTeknisi(email);
        assertEquals(0, rating);
    }
}