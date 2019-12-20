package com.indocyber.itsmeandroid.model;

public class PromoItemModel {
    private String title;
    private String desc;
    private String periode;
    private int banner;

    public PromoItemModel() {
    }

    public PromoItemModel(String title, String desc, String periode, int banner) {
        this.title = title;
        this.desc = desc;
        this.periode = periode;
        this.banner = banner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public int getBanner() {
        return banner;
    }

    public void setBanner(int banner) {
        this.banner = banner;
    }
}
