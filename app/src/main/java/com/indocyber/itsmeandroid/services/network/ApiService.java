package com.indocyber.itsmeandroid.services.network;

import com.indocyber.itsmeandroid.di.DaggerApplicationComponent;
import com.indocyber.itsmeandroid.model.ApiResponse;
import com.indocyber.itsmeandroid.model.ProfileKTPModel;
import com.indocyber.itsmeandroid.model.ProfileNPWPModel;
import com.indocyber.itsmeandroid.model.ProfilePassportModel;
import com.indocyber.itsmeandroid.model.PromoMenuModel;
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
    public Single<ApiResponse<User>> getProfile(String authKey) {
        return api.getUserProfile(generateAuthenticationHeader(authKey));
    }

    public Single<ApiResponse<String>> updateProfile(String authKey, User user) {
        HashMap<String, String> body = new HashMap<>();
        body.put("userAddress", user.getAlamat());
        body.put("userPhone", user.getNoTelp());
        body.put("userPassword", user.getPassword());
        body.put("userPin", user.getPin());
        return api.updateProfile(generateAuthenticationHeader(authKey), body);
    }

    public Single<ApiResponse<String>> updateKtp(String authKey, ProfileKTPModel ktp, String extension) {
        HashMap<String, String> body = new HashMap<>();
        body.put("ktpName", ktp.getNamaLengkap());
        body.put("ktpNo", ktp.getNoKtp());
        body.put("ktpBirthDate", ktp.getTglLahir());
        body.put("ktpGender", ktp.getJenisKelamin());
        body.put("ktpReligion", ktp.getAgama());
        body.put("ktpMaritalStatus", ktp.getStatus());
        body.put("ktpJob", ktp.getPekerjaan());
        body.put("ktpCitizenship", ktp.getKwn());
        body.put("ktpAddress", ktp.getAlamat());
        body.put("ktpRt", ktp.getRt());
        body.put("ktpRw", ktp.getRw());
        body.put("ktpKelurahanId", ktp.getKelurahan());
        body.put("ktpKecamatanId", ktp.getKecamatan());
        body.put("imageBase64", extension);
        body.put("imageExtension", ktp.getFotoKTP());
        return api.updateKtp(generateAuthenticationHeader(authKey), body);
    }

    public Single<ApiResponse<String>> updateNpwp(String authKey, ProfileNPWPModel npwp, String extension) {
        HashMap<String, String> body = new HashMap<>();
        body.put("npwpName", npwp.getNamaLengkap());
        body.put("npwpNo", npwp.getNoNpwp());
        body.put("npwpNik", npwp.getNik());
        body.put("npwpAddress", npwp.getAlamat());
        body.put("npwpKantorPajak", npwp.getAlamatKPP());
        body.put("imageBase64", npwp.getFotoNPWP());
        body.put("imageExtension", extension);
        return api.updateNpwp(generateAuthenticationHeader(authKey), body);
    }

    public Single<ApiResponse<String>> updatePassport(String authKey, ProfilePassportModel passport, String extension) {
        HashMap<String, String> body = new HashMap<>();
        body.put("passportName", passport.getNamaLengkap());
        body.put("passportNo", passport.getNoPassport());
        body.put("passportCitizenship", passport.getKwn());
        body.put("passportBirthPlace", passport.getTempatLahir());
        body.put("passportBirthDate", passport.getTglLahir());
        body.put("passportExpired", passport.getBerlaku());
        body.put("imageBase64", passport.getFotoPassport());
        body.put("imageExtension", extension);
        return api.updateNpwp(generateAuthenticationHeader(authKey), body);
    }


    public Single<ApiResponse<ProfileKTPModel>> getKtp(String authKey) {
        return api.getUserKtp(generateAuthenticationHeader(authKey));
    }

    public Single<ApiResponse<ProfileNPWPModel>> getNpwp(String authKey) {
        return api.getUserNPWP(generateAuthenticationHeader(authKey));
    }

    public Single<ApiResponse<ProfilePassportModel>> getPassport(String authKey) {
        return api.getUserPassport(generateAuthenticationHeader(authKey));
    }

    public Single<ApiResponse<String>> updateProfilePicture(String authKey, String base64, String extension) {
        HashMap<String, String> body = new HashMap<>();
        body.put("imageBase64", base64);
        body.put("imageExtension", extension);
        return api.updateProfilePicture(generateAuthenticationHeader(authKey), body);
    }

    public Single<ApiResponse<String>> updateProfile(String authKey, HashMap<String, String> body) {
        return api.updateProfile(generateAuthenticationHeader(authKey), body);
    }

    public Single<ApiResponse<User>> login(String authKey) {
        return api.login(generateAuthenticationHeader(authKey));
    }

    public Single<ApiResponse<List<PromoMenuModel>>> getPromoList(String authKey, HashMap<String, String> body) {
        return api.getPromoList(generateAuthenticationHeader(authKey), body);
    }
}
