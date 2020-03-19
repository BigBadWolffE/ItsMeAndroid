package com.indocyber.itsmeandroid.viewremastered.historytransaction;

public class HistoryTransactionModel {
    private String tanggal;
    private String title;
    private String detail;

    public HistoryTransactionModel(String tanggal, String title, String detail){
        this.tanggal = tanggal;
        this.title = title;
        this.detail = detail;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
