package com.indocyber.itsmeandroid.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {

    private static final String PREFS_NAME = "ITSME";
    private static final String LOGIN_PREFS_NAME = "LOGIN_PREFS_NAME";
    public static final String LOGGED_USER_FULLNAME = "loggedUserFullName";
    public static final String LOGGED_USER_EMAIL = "loggedUserEmail";
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

    public void setLoggedUser(String userFullName, String email) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(LOGGED_USER_FULLNAME, userFullName);
        editor.putString(LOGGED_USER_EMAIL, email);
        editor.apply();
    }

    public String getLoggedUserFullname() {
        return prefs.getString(LOGGED_USER_FULLNAME, "");
    }
    public String getLoggedUserEmail() {
        return prefs.getString(LOGGED_USER_EMAIL, "");
    }
}
