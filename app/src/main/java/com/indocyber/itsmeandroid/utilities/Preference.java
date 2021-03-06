package com.indocyber.itsmeandroid.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {

    private static final String PREFS_NAME = "ITSME";
    private static final String LOGIN_PREFS_NAME = "LOGIN_PREFS_NAME";
    public static final String LOGGED_USER_FULLNAME = "loggedUserFullName";
    public static final String LOGGED_USER_EMAIL = "loggedUserEmail";
    public static final String USER_AUTH = "userAuth";
    public static final String META_DATA = "meta";
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

    public void saveUserAuth(String key) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(USER_AUTH, key);
        editor.apply();
    }

    public void setLoggedUser(String userFullName, String email) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(LOGGED_USER_FULLNAME, userFullName);
        editor.putString(LOGGED_USER_EMAIL, email);
        editor.apply();
    }

    public void setMetaData(String metaData) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(META_DATA, metaData);
        editor.apply();
    }

    public void clearPref() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

    public String getLoggedUserFullname() {
        return prefs.getString(LOGGED_USER_FULLNAME, "");
    }
    public String getLoggedUserEmail() {
        return prefs.getString(LOGGED_USER_EMAIL, "");
    }
    public String getUserAuth() { return prefs.getString(USER_AUTH, ""); }
    public String getMetaData() { return prefs.getString(META_DATA, ""); }
}
