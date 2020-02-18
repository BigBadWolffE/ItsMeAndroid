package com.indocyber.itsmeandroid.repositories.database.typeconverter;
import androidx.annotation.Nullable;
import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 *
 *@author Muhammad Faisal
 *@version 1.0
 */
public class ListStringTypeConverter {
    @TypeConverter
    public static List<String> fromString(@Nullable String data) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String fromList(List<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
