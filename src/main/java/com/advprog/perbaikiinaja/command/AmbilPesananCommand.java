package com.advprog.perbaikiinaja.command;

import com.advprog.perbaikiinaja.model.Pesanan;
import com.advprog.perbaikiinaja.service.PesananService;

public class AmbilPesananCommand implements PesananCommand {
    private final PesananService service;
    private final long idPesanan;
    private final long estimasiHarga;
    private final int estimasiWaktu;

    public AmbilPesananCommand(PesananService service, long idPesanan, long estimasiHarga, int estimasiWaktu) {
        this.service = service;
        this.idPesanan = idPesanan;
        this.estimasiHarga = estimasiHarga;
        this.estimasiWaktu = estimasiWaktu;
    }

    @Override
    public Pesanan execute() {
        return service.ambilPesanan(idPesanan, estimasiHarga, estimasiWaktu);
    }
}
