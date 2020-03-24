package com.indocyber.itsmeandroid.viewremastered.loginandregister.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SavePref {

    //Name
    public static void saveName(Activity activity, String name) {
        SharedPreferences sharedPref = activity.getSharedPreferences(Api.TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("name", name);
        editor.commit();
    }
    public static String readName(Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences(Api.TAG, Context.MODE_PRIVATE);
        return sharedPref.getString("name", null);
    }
    //Email
    public static void saveEmail(Activity activity, String email) {
        SharedPreferences sharedPref = activity.getSharedPreferences(Api.TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("email", email);
        editor.commit();
    }
    public static String readEmail(Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences(Api.TAG, Context.MODE_PRIVATE);
        return sharedPref.getString("email", null);
    }
    //Phone Number
    public static void savePhone(Activity activity, String phone) {
        SharedPreferences sharedPref = activity.getSharedPreferences(Api.TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("phone", phone);
        editor.commit();
    }

    public static String readPhone(Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences(Api.TAG, Context.MODE_PRIVATE);
        return sharedPref.getString("name", null);
    }
    //pass
    public static void savePass(Activity activity, String pass) {
        SharedPreferences sharedPref = activity.getSharedPreferences(Api.TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("pass", pass);
        editor.commit();
    }
    public static String readPass(Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences(Api.TAG, Context.MODE_PRIVATE);
        return sharedPref.getString("pass", null);
    }
    //Pin
    public static void savePin(Activity activity, String pin) {
        SharedPreferences sharedPref = activity.getSharedPreferences(Api.TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("pin", pin);
        editor.commit();
    }
    public static String readPin(Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences(Api.TAG, Context.MODE_PRIVATE);
        return sharedPref.getString("pin", null);
    }
    //Secret Answer
    public static void saveSecretAnswer(Activity activity, String scAnswer) {
        SharedPreferences sharedPref = activity.getSharedPreferences(Api.TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("secretAnswer", scAnswer);
        editor.commit();
    }
    public static String readSecretAnswer(Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences(Api.TAG, Context.MODE_PRIVATE);
        return sharedPref.getString("secretAnswer", null);
    }
    //login auth
    public static void saveLoginUser(Activity activity, String loginUser) {
        SharedPreferences sharedPref = activity.getSharedPreferences(Api.TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("loginUser", loginUser);
        editor.commit();
    }
    public static String readLoginUser(Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences(Api.TAG, Context.MODE_PRIVATE);
        return sharedPref.getString("loginUser", null);
    }
}
