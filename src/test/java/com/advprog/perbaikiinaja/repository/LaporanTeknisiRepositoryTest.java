package com.advprog.perbaikiinaja.repository;

import com.advprog.perbaikiinaja.model.LaporanTeknisi;
import com.advprog.perbaikiinaja.model.Pesanan;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class LaporanTeknisiRepositoryTest {

    @Autowired
    private LaporanTeknisiRepository laporanTeknisiRepository;

    @Autowired
    private PesananRepository pesananRepository;

    @Test
    public void testSaveAndFindLaporanTeknisi() {
        Pesanan pesanan = new Pesanan();
        pesanan.setNamaBarang("Laptop");
        pesanan.setKondisiBarang("Mati total");
        pesanan.setEmailPengguna("user@test.com");
        pesanan.setEmailTeknisi("teknisi@test.com");
        pesananRepository.save(pesanan);

        LaporanTeknisi laporan = new LaporanTeknisi("Ganti RAM dan PSU", pesanan);
        laporanTeknisiRepository.save(laporan);

        LaporanTeknisi found = laporanTeknisiRepository.findById(laporan.getId()).orElse(null);

        assertNotNull(found);
        assertEquals("Ganti RAM dan PSU", found.getDeskripsi());
        assertEquals(pesanan.getId(), found.getPesanan().getId());
    }
}
