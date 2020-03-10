package com.indocyber.itsmeandroid.model;

import com.google.gson.annotations.SerializedName;

public class ProfilePassportModel {

    @SerializedName("passportName")
    private String namaLengkap;
    @SerializedName("passportNo")
    private String noPassport;
    @SerializedName("passportCitizenship")
    private String kwn;
    @SerializedName("passportBirthPlace")
    private String tempatLahir;
    @SerializedName("passportBirthDate")
    private String tglLahir;
    @SerializedName("passportExpired")
    private String berlaku;
    private String fotoPassport;

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

    public String getFotoPassport() {
        return fotoPassport;
    }

    public void setFotoPassport(String fotoPassport) {
        this.fotoPassport = fotoPassport;
    }
}
