package com.indocyber.itsmeandroid.services.database.typeconverter;

import android.util.JsonReader;
import android.util.JsonWriter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.indocyber.itsmeandroid.model.User;

/**
 *
 *
 *@author Muhammad Faisal
 *@version 1.0
 */
public class UserPinTypeConverter {
//    @TypeConverter
//    public String fromUserpin(User.UserPin userPin) {
//        Gson gson = new Gson();
//        return userPin == null ? null : gson.toJson(userPin);
//    }
//
//    @TypeConverter
//    public User.UserPin toUserPin(String json) {
//        Gson gson = new Gson();
//        User.UserPin pin;
//        try {
//            pin = gson.fromJson(json, User.UserPin.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//            pin = null;
//        }
//        return pin;
//    }
}
