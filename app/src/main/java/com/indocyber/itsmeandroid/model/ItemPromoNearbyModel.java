package com.indocyber.itsmeandroid.model;

public class ItemPromoNearbyModel extends PromoItemModel  {
    private String jarak;
    private String diskon;

    public String getDiskon() {
        return diskon;
    }

    public void setDiskon(String diskon) {
        this.diskon = diskon;
    }

    public ItemPromoNearbyModel(String title, String desc, String periode, int banner, String jarak, String diskon){
        super(title, desc, periode, banner);
        this.jarak = jarak;
        this.diskon = diskon;
    }

    public String getJarak() {
        return jarak;
    }

    public void setJarak( String jarak) {
        this.jarak = jarak;


    }
}
