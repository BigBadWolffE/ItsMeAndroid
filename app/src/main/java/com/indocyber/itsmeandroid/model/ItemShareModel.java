package com.indocyber.itsmeandroid.model;

import android.graphics.drawable.Drawable;

public class ItemShareModel {
    public String app = "";
    public String packageName = "";
    public Drawable icon;

    public ItemShareModel(String name,String packageName, Drawable drawable) {
        this.app = name;
        this.packageName = packageName;
        this.icon = drawable;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
