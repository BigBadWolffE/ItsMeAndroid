package com.indocyber.itsmeandroid.repositories;

import com.indocyber.itsmeandroid.model.ApiResponse;
import com.indocyber.itsmeandroid.model.ProfileKTPModel;
import com.indocyber.itsmeandroid.model.ProfileNPWPModel;
import com.indocyber.itsmeandroid.model.ProfilePassportModel;
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

    public Single<ApiResponse<User>> registerUser(User user) {
        return service.register(user);
    }

    public Single<ApiResponse<User>> getProfile(String authKey) {
        return service.getProfile(authKey);
    }

    public Single<ApiResponse<ProfileKTPModel>> getKtp(String authKey) {
        return service.getKtp(authKey);
    }

    public Single<ApiResponse<ProfileNPWPModel>> getNpwp(String authKey) {
        return service.getNpwp(authKey);
    }

    public Single<ApiResponse<ProfilePassportModel>> getPassport(String authKey) {
        return service.getPassport(authKey);
    }

    public Single<ApiResponse<String>> updateProfilePicture(String authKey, String base64, String extension) {
        return service.updateProfilePicture(authKey, base64, extension);
    }

    public Single<ApiResponse<String>> updateProfile(String authKey, User user) {
        return service.updateProfile(authKey, user);
    }

    public Single<ApiResponse<String>> updateKtp(String authKey, ProfileKTPModel model, String extension) {
        return service.updateKtp(authKey, model, extension);
    }

    public Single<ApiResponse<String>> updateNpwp(String authKey, ProfileNPWPModel model, String extension) {
        return service.updateNpwp(authKey, model, extension);
    }

    public Single<ApiResponse<String>> updatePassport(String authKey, ProfilePassportModel model, String extension) {
        return service.updatePassport(authKey, model, extension);
    }

    public Single<ApiResponse<User>> login(String authKey) {
        return service.login(authKey);
    }
}
