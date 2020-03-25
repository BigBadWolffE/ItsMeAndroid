package com.indocyber.itsmeandroid.model;

import com.google.gson.annotations.SerializedName;

public class PromoMenuModel {

    private String title;
    private int color;
    private int banner;

    public PromoMenuModel(String title , int color , int banner){
        this.title  = title;
        this.color = color;
        this.banner = banner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getBanner() {
        return banner;
    }

    public void setBanner(int banner) {
        this.banner = banner;
    }

}
