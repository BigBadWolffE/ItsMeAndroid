package com.indocyber.itsmeandroid.model;

import com.google.gson.annotations.SerializedName;

public class PromoItemModel {
    @SerializedName("id")
    private String id;
    @SerializedName("category")
    private String category;
    @SerializedName("description")
    private String desc;
    @SerializedName("startDate")
    private String startDate;
    @SerializedName("endDate")
    private String endDate;
    @SerializedName("fileName")
    private String url;
    @SerializedName("promoName")
    private String title;
    @SerializedName("distance")
    private String distance;
    @SerializedName("promoSK")
    private String syaratKetentuan;
    public String periode;
    public int banner;


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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getSyaratKetentuan() {
        return syaratKetentuan;
    }

    public void setSyaratKetentuan(String syaratKetentuan) {
        this.syaratKetentuan = syaratKetentuan;
    }
}
