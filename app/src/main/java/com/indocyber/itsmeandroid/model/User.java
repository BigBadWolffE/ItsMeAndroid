package com.indocyber.itsmeandroid.model;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class User {

    @SerializedName("fullname")
    private String namaLengkap;
    @PrimaryKey
    @NonNull
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String noTelp;
    @SerializedName("password")
    private String password;
    @SerializedName("pin")
    private UserPin pin;
    @SerializedName("address")
    @Expose(serialize = false)
    private String alamat;
    @SerializedName("secretQuestionID")
    private int secretQuestionId;
    @SerializedName("answerQuestion")
    private String secretAnswer;
    @SerializedName("profilePictureMetadata")
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

    public UserPin getPin() {
        return pin;
    }

    public int getSecretQuestionId() {
        return secretQuestionId;
    }

    public void setSecretQuestionId(int secretQuestionId) {
        this.secretQuestionId = secretQuestionId;
    }

    public void setPin(UserPin pin) {
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

    public class UserPin {
        @SerializedName("algorithm")
        String algorithm;
        @SerializedName("pinValue")
        String pinValue;

        public UserPin(String algorithm, String pinValue) {
            this.algorithm = algorithm;
            this.pinValue = pinValue;
        }

        public String getAlgorithm() {
            return algorithm;
        }

        public void setAlgorithm(String algorithm) {
            this.algorithm = algorithm;
        }

        public String getPinValue() {
            return pinValue;
        }

        public void setPinValue(String pinValue) {
            this.pinValue = pinValue;
        }
    }


}
