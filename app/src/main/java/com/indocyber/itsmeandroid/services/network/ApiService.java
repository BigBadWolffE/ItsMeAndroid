package com.indocyber.itsmeandroid.services.network;

import com.indocyber.itsmeandroid.di.DaggerApplicationComponent;
import com.indocyber.itsmeandroid.model.ApiResponse;
import com.indocyber.itsmeandroid.model.SecretQuestion;
import com.indocyber.itsmeandroid.model.User;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 *
 *
 *@author Muhammad Faisal
 *@Version
 */

public class ApiService {

    Api api;

    @Inject
    public ApiService(Api api) {
        this.api = api;
    }

    private HashMap<String, String> generateAuthenticationHeader(String authKey) {
        HashMap<String, String> authHeader = new HashMap<>();
        authHeader.put("Authorization", "Basic " + authKey);
        return authHeader;
    }

    public Single<ApiResponse<User>> register(User user) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("userEmail", user.getEmail());
        body.put("userPhone", user.getNoTelp());
        body.put("userPassword", user.getPassword());
        body.put("userFullName", user.getNamaLengkap());
        body.put("userPin", user.getPin());
        body.put("secretQuestionId", user.getSecretQuestionId());
        body.put("secretAnswer", user.getSecretAnswer());
        return api.register(body);
    }

    public Single<ApiResponse<List<SecretQuestion>>> getSecretQuestions() {
        return api.getQuestionList();
    }
//
    public Single<ApiResponse<User>> getProfile(String authKey) {
        return api.getUserProfile(generateAuthenticationHeader(authKey));
    }
//
    public Single<ApiResponse<String>> updateProfilePicture(String authKey, String base64) {
        HashMap<String, String> body = new HashMap<>();
        body.put("photo", base64);
        return api.updateProfilePicture(generateAuthenticationHeader(authKey), body);
    }

    public Single<ApiResponse<String>> updateProfile(String authKey, HashMap<String, String> body) {
        return api.updateProfile(generateAuthenticationHeader(authKey), body);
    }

    public Single<ApiResponse<User>> login(String authKey) {
        return api.login(generateAuthenticationHeader(authKey));
    }
}
