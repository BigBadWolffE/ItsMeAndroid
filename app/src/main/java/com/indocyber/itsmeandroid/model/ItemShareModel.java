package com.indocyber.itsmeandroid.model;

import android.graphics.drawable.Drawable;

public class ItemShareModel {
    public String app = "";
    public Drawable icon;

    public ItemShareModel(String name, Drawable drawable) {
        app = name;
        icon = drawable;
    }
}
