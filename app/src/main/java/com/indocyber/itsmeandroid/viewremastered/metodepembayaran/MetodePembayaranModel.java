package com.indocyber.itsmeandroid.viewremastered.metodepembayaran;

public class MetodePembayaranModel {

    private String bankNames;
    private String cardId;
    public MetodePembayaranModel(String bankNames, String cardId){
        this.bankNames = bankNames;
        this.cardId = cardId;
    }

    public String getBankNames() {
        return bankNames;
    }

    public void setBankNames(String bankNames) {
        this.bankNames = bankNames;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
