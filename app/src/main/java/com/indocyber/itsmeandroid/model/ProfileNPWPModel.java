package com.indocyber.itsmeandroid.model;

import com.google.gson.annotations.SerializedName;

public class ProfileNPWPModel {

    @SerializedName("npwpName")
    private String namaLengkap;
    @SerializedName("npwpNo")
    private String noNpwp;
    @SerializedName("npwpNik")
    private String nik;
    private String berlaku;
    @SerializedName("npwpAddress")
    private String alamat;
    @SerializedName("npwpKantorPajak")
    private String alamatKPP;
    @SerializedName("npwpImage")
    private String fotoNPWP;

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

    public String getFotoNPWP() {
        return fotoNPWP;
    }

    public void setFotoNPWP(String fotoNPWP) {
        this.fotoNPWP = fotoNPWP;
    }
}
