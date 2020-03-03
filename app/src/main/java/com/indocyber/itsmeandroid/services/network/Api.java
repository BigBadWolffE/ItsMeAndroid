package com.indocyber.itsmeandroid.services.network;

import com.indocyber.itsmeandroid.model.ApiResponse;
import com.indocyber.itsmeandroid.model.SecretQuestion;
import com.indocyber.itsmeandroid.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

/**
 *
 *
 *@author Muhammad Faisal
 *@version 1.0
 */
public interface Api {

    String BASE_URL = "http://10.100.10.251:5000/stream/file/";
    String XAPIKEY = "yk0j1r7knnyl4e18ip6azbi10jyypc6k-hvmrv96d5487g1v4qpiippicopuw2f7x-ia1hl5fcjdrdif98tfsnunvnwfj4uf7q";
    String ACCEPT = "application/json";

//    @POST(value = "user/register")
//    @Headers({"Accept: " + ACCEPT, "x-api-key: " + XAPIKEY})
//    Single<ApiResponse<String>> register(@Body HashMap <String, Object> body);
//
    @GET(value = "master/getallsecretquestion/mobile")
    @Headers({"Accept: " + ACCEPT})
    Single<ApiResponse<List<SecretQuestion>>> getQuestionList();
//    @GET(value = "user/profile")
//    @Headers({"Accept: " + ACCEPT, "x-api-key: " + XAPIKEY})
//    Single<ApiResponse<User>> getUserProfile(@HeaderMap Map<String, String> authHeader);
//
    @POST(value = "profile/updateprofileimage/mobile")
    @Headers({"Accept: " + ACCEPT})
    Single<ApiResponse<String>> updateProfilePicture(@HeaderMap Map<String, String> authHeader, @Body Map<String, String> body);

    @PATCH(value = "user/profile")
    @Headers({"Accept: " + ACCEPT, "x-api-key: " + XAPIKEY})
    Single<ApiResponse<String>> updateProfile(@HeaderMap Map<String, String> authHeader, @Body Map<String, String> body);

    @POST(value = "authentication/registration/mobile")
    @Headers({"Accept: " + ACCEPT})
    Single<ApiResponse<User>> register(@Body HashMap <String, Object> body);

    @GET(value = "profile/getprofile/mobile")
    @Headers({"Accept: " + ACCEPT})
    Single<ApiResponse<User>> getUserProfile(@HeaderMap Map<String, String> authHeader);

    @POST(value = "authentication/login/mobile")
    @Headers({"Accept: " + ACCEPT})
    Single<ApiResponse<User>> login(@HeaderMap Map<String, String> authHeader);
}
