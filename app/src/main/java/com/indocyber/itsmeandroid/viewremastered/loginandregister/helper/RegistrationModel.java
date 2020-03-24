package com.indocyber.itsmeandroid.viewremastered.loginandregister.helper;

import com.google.gson.annotations.SerializedName;

public class RegistrationModel {
    @SerializedName("userFullName")
    private String userFullName;
    @SerializedName("userEmail")
    private String userEmail;
    @SerializedName("userPhone")
    private String  userPhone;
    @SerializedName("userPassword")
    private String userPassword;
    @SerializedName("userPin")
    private String userPin;
    @SerializedName("secretQuestionId")
    private String secretQuestionId;
    @SerializedName("secretAnswer")
    private String secretAnswer;

    public RegistrationModel(){}

    public RegistrationModel(String userFullName, String userEmail, String userPhone, String userPassword, String userPin, String secretQuestionId, String secretAnswer) {
        this.userFullName = userFullName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userPassword = userPassword;
        this.userPin = userPin;
        this.secretQuestionId = secretQuestionId;
        this.secretAnswer = secretAnswer;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPin() {
        return userPin;
    }

    public void setUserPin(String userPin) {
        this.userPin = userPin;
    }

    public String getSecretQuestionId() {
        return secretQuestionId;
    }

    public void setSecretQuestionId(String secretQuestionId) {
        this.secretQuestionId = secretQuestionId;
    }

    public String getSecretAnswer() {
        return secretAnswer;
    }

    public void setSecretAnswer(String secretAnswer) {
        this.secretAnswer = secretAnswer;
    }
}
