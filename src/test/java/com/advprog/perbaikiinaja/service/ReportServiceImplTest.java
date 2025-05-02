package com.advprog.perbaikiinaja.service;
 
 import com.advprog.perbaikiinaja.model.PaymentMethod;
 import com.advprog.perbaikiinaja.model.Pesanan;
 import com.advprog.perbaikiinaja.model.Report;
 import com.advprog.perbaikiinaja.repository.ReportRepository;
 import org.junit.jupiter.api.BeforeEach;
 import org.junit.jupiter.api.Test;
 import org.mockito.InjectMocks;
 import org.mockito.Mock;
 import org.mockito.MockitoAnnotations;
 
 import static org.junit.jupiter.api.Assertions.*;
 import static org.mockito.Mockito.*;
 
 public class ReportServiceImplTest {
 
     @Mock
     private ReportRepository reportRepository;
 
     @Mock
     private PesananService pesananService;
 
     @InjectMocks
     private ReportServiceImpl reportService;
 
     private Pesanan dummyPesanan;
     private Report dummyReport;
 
     @BeforeEach
     public void setup() {
         MockitoAnnotations.openMocks(this);
         PaymentMethod metodePembayaran = new PaymentMethod("1", "Gopay");
 
         // Membuat dummy Pesanan
         dummyPesanan = new Pesanan(
                 "Laptop",                    // namaBarang
                 "Rusak total",              // kondisiBarang
                 "DISKON10",                 // kodeKupon
                 "pengguna@email.com",       // emailPengguna
                 "teknisi@email.com",
                 metodePembayaran// emailTeknisi
         );
         dummyPesanan.setId(1L);
 
         // Membuat dummy Report
         dummyReport = new Report("Bagus", 5, dummyPesanan);
     }
 
     @Test
     public void testCreateReport_Success() {
         when(pesananService.findById(1L)).thenReturn(dummyPesanan);
         when(reportRepository.findByPesananId(1L)).thenReturn(null); // Tidak ada laporan yang ditemukan sebelumnya
         when(reportRepository.save(any(Report.class))).thenReturn(dummyReport);
 
         Report result = reportService.createReport("Bagus", 5, 1L);
 
         assertNotNull(result);
         assertEquals("Bagus", result.getUlasan());
         assertEquals(5, result.getRating());
     }
 
     @Test
     public void testCreateReport_ReportAlreadyExists() {
         when(pesananService.findById(1L)).thenReturn(dummyPesanan);
         when(reportRepository.findByPesananId(1L)).thenReturn(dummyReport); // Laporan sudah ada
 
         RuntimeException exception = assertThrows(RuntimeException.class, () ->
                 reportService.createReport("Bagus", 5, 1L)
         );
         assertEquals("Report already exists for this pesanan", exception.getMessage());
     }
 
     @Test   
     public void testUpdateReport_Success() {
         when(reportRepository.findByPesananId(1L)).thenReturn(dummyReport);
         when(reportRepository.save(any(Report.class))).thenReturn(dummyReport);
 
         Report result = reportService.updateReport(1L, "Sangat bagus", 4);
 
         assertEquals("Sangat bagus", result.getUlasan());
         assertEquals(4, result.getRating());
     }
 
     @Test
     public void testUpdateReport_ReportNotFound() {
         when(reportRepository.findByPesananId(1L)).thenReturn(null); // Laporan tidak ditemukan
 
         RuntimeException exception = assertThrows(RuntimeException.class, () ->
                 reportService.updateReport(1L, "Ulasan baru", 3)
         );
         assertEquals("Report not found with ID: 1", exception.getMessage());
     }
 
     @Test
     public void testGetReportByPesananId_Success() {
         when(reportRepository.findByPesananId(1L)).thenReturn(dummyReport);
 
         Report result = reportService.getReportByPesananId(1L);
         assertEquals(dummyReport, result);
     }
 
     @Test
     public void testGetReportByPesananId_NotFound() {
         when(reportRepository.findByPesananId(1L)).thenReturn(null); // Laporan tidak ditemukan
 
         RuntimeException exception = assertThrows(RuntimeException.class, () ->
                 reportService.getReportByPesananId(1L)
         );
         assertEquals("Report not found for pesanan ID: 1", exception.getMessage());
     }
 }