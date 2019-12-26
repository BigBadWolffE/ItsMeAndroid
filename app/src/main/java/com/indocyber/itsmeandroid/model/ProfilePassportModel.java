package com.indocyber.itsmeandroid.model;

import android.graphics.Bitmap;

public class ProfilePassportModel {

    private String namaLengkap;
    private String noPassport;
    private String kwn;
    private String tempatLahir;
    private String tglLahir;
    private String berlaku;
    private Bitmap fotoPassport;

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getNoPassport() {
        return noPassport;
    }

    public void setNoPassport(String noPassport) {
        this.noPassport = noPassport;
    }

    public String getKwn() {
        return kwn;
    }

    public void setKwn(String kwn) {
        this.kwn = kwn;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }

    public String getTglLahir() {
        return tglLahir;
    }

    public void setTglLahir(String tglLahir) {
        this.tglLahir = tglLahir;
    }

    public String getBerlaku() {
        return berlaku;
    }

    public void setBerlaku(String berlaku) {
        this.berlaku = berlaku;
    }

    public Bitmap getFotoPassport() {
        return fotoPassport;
    }

    public void setFotoPassport(Bitmap fotoPassport) {
        this.fotoPassport = fotoPassport;
    }
}
