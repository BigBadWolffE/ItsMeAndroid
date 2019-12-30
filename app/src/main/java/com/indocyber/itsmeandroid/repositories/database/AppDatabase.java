package com.indocyber.itsmeandroid.repositories.database;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.repositories.database.dao.ImageCardDao;
import com.indocyber.itsmeandroid.repositories.database.typeconverter.ListStringTypeConverter;

import java.util.ArrayList;

/**
 *
 *
 *@author Muhammad Faisal
 *@version 1.0
 */

@Database(entities = {ImageCardModel.class}, version = 2, exportSchema = false)
@TypeConverters({ListStringTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract ImageCardDao imageCardDao();

    public static AppDatabase getInstance(Application application) {
        if(instance == null) {
            return Room.databaseBuilder(application, AppDatabase.class, "ItsmeDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
