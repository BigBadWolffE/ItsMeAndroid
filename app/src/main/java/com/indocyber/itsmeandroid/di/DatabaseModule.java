package com.indocyber.itsmeandroid.di;

import android.app.Application;

import androidx.room.Room;

import com.indocyber.itsmeandroid.services.database.AppDatabase;
import com.indocyber.itsmeandroid.services.database.dao.ImageCardDao;
import com.indocyber.itsmeandroid.services.database.dao.SecretQuestionDao;
import com.indocyber.itsmeandroid.services.database.dao.UserDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 *
 *
 *@author Muhammad Faisal
 *@version 1.0
 */

@Module
public class DatabaseModule {

    private static AppDatabase database;

    public DatabaseModule(Application application) {
        database = Room.databaseBuilder(application, AppDatabase.class, "ItsmeDatabase")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    static AppDatabase provideDatabase() {
        return database;
    }

    @Singleton
    @Provides
    static ImageCardDao imageCardDao() { return database.imageCardDao(); }

    @Singleton
    @Provides
    static UserDao userDao() { return database.userDao(); }

    @Singleton
    @Provides
    static SecretQuestionDao secretQuestionDao() { return database.secretQuestionDao(); }
}
