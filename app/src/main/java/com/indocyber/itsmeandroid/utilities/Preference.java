package com.indocyber.itsmeandroid.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {

    private static final String PREFS_NAME = "ITSME";
    private static final String LOGIN_PREFS_NAME = "LOGIN_PREFS_NAME";
    private SharedPreferences prefs;

    public Preference(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setLoginFirstTime(Boolean firstTime) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(LOGIN_PREFS_NAME, firstTime);
        editor.apply();
    }

    public Boolean getLogin() {
        return prefs.getBoolean(LOGIN_PREFS_NAME, false);
    }
}
