package com.indocyber.itsmeandroid.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Pulsa implements Parcelable {

    private String hargaPulsa;
    private String masaBerlaku;
    private String hargaBayar;
    private String operator;

    public Pulsa(String hargaPulsa, String masaBerlaku, String hargaBayar, String operator) {
        this.hargaPulsa = hargaPulsa;
        this.masaBerlaku = masaBerlaku;
        this.hargaBayar = hargaBayar;
        this.operator = operator;
    }

    private Pulsa(Parcel in) {
        hargaPulsa = in.readString();
        masaBerlaku = in.readString();
        hargaBayar = in.readString();
        operator = in.readString();
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

    public String getMasaBerlaku() {
        return masaBerlaku;
    }

    public String getHargaBayar() {
        return hargaBayar;
    }

    public String getOperator() {
        return operator;
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
    }
}
