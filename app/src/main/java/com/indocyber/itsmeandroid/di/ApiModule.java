package com.indocyber.itsmeandroid.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.indocyber.itsmeandroid.services.network.Api;
import com.indocyber.itsmeandroid.services.network.ApiService;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 *
 *@author Muhammad Faisal
 *@version 1.0
 */
@Module
public class ApiModule {

    private static final String BASE_URL = "http://10.100.10.251:5000/";

    Gson gson = new GsonBuilder().create();

    @Provides
    Api provideApi() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient
                .Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(loggingInterceptor);

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(clientBuilder.build())
                .build()
                .create(Api.class);
    }

    @Provides
    ApiService provideService() {
        return new ApiService();
    }
}
