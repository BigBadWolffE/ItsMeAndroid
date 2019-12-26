package com.indocyber.itsmeandroid.model;

import android.graphics.Bitmap;

public class ProfileNPWPModel {

    private String namaLengkap;
    private String noNpwp;
    private String nik;
    private String berlaku;
    private String alamat;
    private String alamatKPP;
    private Bitmap fotoNPWP;

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getNoNpwp() {
        return noNpwp;
    }

    public void setNoNpwp(String noNpwp) {
        this.noNpwp = noNpwp;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getBerlaku() {
        return berlaku;
    }

    public void setBerlaku(String berlaku) {
        this.berlaku = berlaku;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getAlamatKPP() {
        return alamatKPP;
    }

    public void setAlamatKPP(String alamatKPP) {
        this.alamatKPP = alamatKPP;
    }

    public Bitmap getFotoNPWP() {
        return fotoNPWP;
    }

    public void setFotoNPWP(Bitmap fotoNPWP) {
        this.fotoNPWP = fotoNPWP;
    }
}
