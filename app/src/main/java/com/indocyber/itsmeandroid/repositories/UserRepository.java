package com.indocyber.itsmeandroid.repositories;

import android.app.Application;

import com.indocyber.itsmeandroid.di.DaggerApplicationComponent;
import com.indocyber.itsmeandroid.di.DatabaseModule;
import com.indocyber.itsmeandroid.model.ApiResponse;
import com.indocyber.itsmeandroid.model.User;
import com.indocyber.itsmeandroid.services.network.ApiService;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 *
 *
 *@author Muhammad Faisal
 *@version 1.0
 */
public class UserRepository {

    ApiService service;

    @Inject
    public UserRepository(ApiService service) {
        this.service = service;
    }

    public Single<ApiResponse<String>> registerUser(User user, Integer newPin) {
        return service.register(user, newPin);
    }

    public Single<ApiResponse<User>> getProfile(String authKey) {
        return service.getProfile(authKey);
    }

    public Single<ApiResponse<String>> updateProfilePicture(String authKey, String base64) {
        return service.updateProfilePicture(authKey, base64);
    }

    public Single<ApiResponse<String>> updateProfile(String authKey, HashMap<String, String> body) {
        return service.updateProfile(authKey, body);
    }
}
