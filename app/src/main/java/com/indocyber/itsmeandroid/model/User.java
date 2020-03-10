package com.indocyber.itsmeandroid.model;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class User {

    @SerializedName("userFullName")
    private String namaLengkap;
    @PrimaryKey
    @NonNull
    @SerializedName("userEmail")
    private String email;
    @SerializedName("userPhone")
    private String noTelp;
    @SerializedName("userPassword")
    private String password;
    @SerializedName("userPin")
    private String pin;
    @SerializedName("userAddress")
    private String alamat;
    @SerializedName("userSecretQuestion")
    private String secretQuestionId;
    @SerializedName("userSecretAnswer")
    private String secretAnswer;
    @SerializedName("userImage")
    private String pictureMetaData;

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPin() {
        return pin;
    }

    public String getSecretQuestionId() {
        return secretQuestionId;
    }

    public void setSecretQuestionId(String secretQuestionId) {
        this.secretQuestionId = secretQuestionId;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getSecretAnswer() {
        return secretAnswer;
    }

    public void setSecretAnswer(String secretAnswer) {
        this.secretAnswer = secretAnswer;
    }

    public String getPictureMetaData() {
        return pictureMetaData;
    }

    public void setPictureMetaData(String pictureMetaData) {
        this.pictureMetaData = pictureMetaData;
    }

//    public class UserPin {
//        @SerializedName("algorithm")
//        String algorithm;
//        @SerializedName("pinValue")
//        String pinValue;
//
//        public UserPin(String algorithm, String pinValue) {
//            this.algorithm = algorithm;
//            this.pinValue = pinValue;
//        }
//
//        public String getAlgorithm() {
//            return algorithm;
//        }
//
//        public void setAlgorithm(String algorithm) {
//            this.algorithm = algorithm;
//        }
//
//        public String getPinValue() {
//            return pinValue;
//        }
//
//        public void setPinValue(String pinValue) {
//            this.pinValue = pinValue;
//        }
//    }


}
