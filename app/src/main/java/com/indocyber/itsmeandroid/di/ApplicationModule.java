package com.indocyber.itsmeandroid.di;

import android.app.Application;

import androidx.room.Room;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.indocyber.itsmeandroid.services.database.AppDatabase;
import com.indocyber.itsmeandroid.services.database.dao.ImageCardDao;
import com.indocyber.itsmeandroid.services.database.dao.SecretQuestionDao;
import com.indocyber.itsmeandroid.services.database.dao.UserDao;
import com.indocyber.itsmeandroid.services.network.Api;
import com.indocyber.itsmeandroid.services.network.ApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 *
 *
 *@Author
 *@Version
 */
@Module(includes = ViewModelModule.class)
public class ApplicationModule {

//    private static final String BASE_URL = "http://10.100.10.251:5000/";
//    private static final String BASE_URL = "http://10.100.10.251:7771/";
    private static final String BASE_URL = "http://34.87.100.8:7771/";

    @Provides
    Api provideApi() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient
                .Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(loggingInterceptor);

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(clientBuilder.build())
                .build()
                .create(Api.class);
    }

    @Provides
    ApiService provideService(Api api) {
        return new ApiService(api);
    }

    @Singleton
    @Provides
    static AppDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application, AppDatabase.class, "ItsmeDatabase")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    static ImageCardDao imageCardDao(AppDatabase database) {
        return database.imageCardDao();
    }

    @Singleton
    @Provides
    static UserDao userDao(AppDatabase database) {
        return database.userDao();
    }

    @Singleton
    @Provides
    static SecretQuestionDao secretQuestionDao(AppDatabase database) {
        return database.secretQuestionDao();
    }
}
