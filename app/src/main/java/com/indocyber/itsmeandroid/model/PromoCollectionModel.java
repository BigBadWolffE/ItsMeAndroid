package com.indocyber.itsmeandroid.model;

public class PromoCollectionModel {

    private Integer cardType;
    private String cardNumber;
    private String cardHolder;
    private String cardExpiry;

    public PromoCollectionModel(Integer cardType, String cardNumber, String cardHolder, String cardExpiry) {
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.cardExpiry = cardExpiry;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getCardExpiry() {
        return cardExpiry;
    }

    public void setCardExpiry(String cardExpiry) {
        this.cardExpiry = cardExpiry;
    }
}
