package com.indocyber.itsmeandroid.services.database;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.model.SecretQuestion;
import com.indocyber.itsmeandroid.model.User;
import com.indocyber.itsmeandroid.services.database.dao.ImageCardDao;
import com.indocyber.itsmeandroid.services.database.dao.SecretQuestionDao;
import com.indocyber.itsmeandroid.services.database.dao.UserDao;
import com.indocyber.itsmeandroid.services.database.typeconverter.ListStringTypeConverter;
import com.indocyber.itsmeandroid.services.database.typeconverter.UserPinTypeConverter;

/**
 *
 *
 *@author Muhammad Faisal
 *@version 1.0
 */

@Database(entities = {ImageCardModel.class, User.class, SecretQuestion.class}, version = 6, exportSchema = false)
@TypeConverters({ListStringTypeConverter.class, UserPinTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract ImageCardDao imageCardDao();
    public abstract UserDao userDao();
    public abstract SecretQuestionDao secretQuestionDao();

    public static AppDatabase getInstance(Application application) {
        if(instance == null) {
            return Room.databaseBuilder(application, AppDatabase.class, "ItsmeDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
