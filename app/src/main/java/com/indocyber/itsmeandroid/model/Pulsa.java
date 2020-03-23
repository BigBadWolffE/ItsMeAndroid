package com.indocyber.itsmeandroid.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Pulsa implements Parcelable {

    private String hargaPulsa;
    private String masaBerlaku;
    private String hargaBayar;
    private String operator;
    private String nomorHp;

    public Pulsa(String hargaPulsa, String masaBerlaku, String hargaBayar, String operator) {
        this.hargaPulsa = hargaPulsa;
        this.masaBerlaku = masaBerlaku;
        this.hargaBayar = hargaBayar;
        this.operator = operator;
    }

    protected Pulsa(Parcel in) {
        hargaPulsa = in.readString();
        masaBerlaku = in.readString();
        hargaBayar = in.readString();
        operator = in.readString();
        nomorHp = in.readString();
    }

    public static final Creator<Pulsa> CREATOR = new Creator<Pulsa>() {
        @Override
        public Pulsa createFromParcel(Parcel in) {
            return new Pulsa(in);
        }

        @Override
        public Pulsa[] newArray(int size) {
            return new Pulsa[size];
        }
    };

    public String getHargaPulsa() {
        return hargaPulsa;
    }

    public void setHargaPulsa(String hargaPulsa) {
        this.hargaPulsa = hargaPulsa;
    }

    public String getMasaBerlaku() {
        return masaBerlaku;
    }

    public void setMasaBerlaku(String masaBerlaku) {
        this.masaBerlaku = masaBerlaku;
    }

    public String getHargaBayar() {
        return hargaBayar;
    }

    public void setHargaBayar(String hargaBayar) {
        this.hargaBayar = hargaBayar;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getNomorHp() {
        return nomorHp;
    }

    public void setNomorHp(String nomorHp) {
        this.nomorHp = nomorHp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hargaPulsa);
        dest.writeString(masaBerlaku);
        dest.writeString(hargaBayar);
        dest.writeString(operator);
        dest.writeString(nomorHp);
    }
}
