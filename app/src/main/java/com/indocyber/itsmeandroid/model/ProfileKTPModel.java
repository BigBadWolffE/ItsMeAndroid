package com.indocyber.itsmeandroid.model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.google.gson.annotations.SerializedName;

public class ProfileKTPModel {

    @SerializedName("ktpName")
    private String namaLengkap;
    @SerializedName("ktpNo")
    private String noKtp;
    private String berlaku;
    @SerializedName("ktpBirthDate")
    private String tglLahir;
    @SerializedName("ktpGender")
    private String jenisKelamin;
    @SerializedName("ktpReligion")
    private String agama;
    @SerializedName("ktpMaritalStatus")
    private String status;
    @SerializedName("ktpJob")
    private String pekerjaan;
    @SerializedName("ktpCitizenship")
    private String kwn;
    @SerializedName("ktpAddress")
    private String alamat;
    @SerializedName("ktpRt")
    private String rt;
    @SerializedName("ktpRw")
    private String rw;
    @SerializedName("ktpKelurahanId")
    private String kelurahan;
    @SerializedName("ktpKecamatanId")
    private String kecamatan;
    @SerializedName("ktpImage")
    private String fotoKTP;

    public String getFotoKTP() {
        return fotoKTP;
    }

    public void setFotoKTP(String fotoKTP) {
        this.fotoKTP = fotoKTP;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getNoKtp() {
        return noKtp;
    }

    public void setNoKtp(String noKtp) {
        this.noKtp = noKtp;
    }

    public String getBerlaku() {
        return berlaku;
    }

    public void setBerlaku(String berlaku) {
        this.berlaku = berlaku;
    }

    public String getTglLahir() {
        return tglLahir;
    }

    public void setTglLahir(String tglLahir) {
        this.tglLahir = tglLahir;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getKwn() {
        return kwn;
    }

    public void setKwn(String kwn) {
        this.kwn = kwn;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public String getRw() {
        return rw;
    }

    public void setRw(String rw) {
        this.rw = rw;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }
}
