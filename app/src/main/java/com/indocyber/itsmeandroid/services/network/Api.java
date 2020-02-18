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
import retrofit2.http.POST;

/**
 *
 *
 *@author Muhammad Faisal
 *@version 1.0
 */
public interface Api {

    @POST(value = "user/register")
    @Headers({"Accept: application/json", "x-api-key: yk0j1r7knnyl4e18ip6azbi10jyypc6k-hvmrv96d5487g1v4qpiippicopuw2f7x-ia1hl5fcjdrdif98tfsnunvnwfj4uf7q"})
    Single<ApiResponse<String>> register(@Body HashMap <String, Object> body);

    @GET(value = "user/question")
    @Headers({"Accept: application/json", "x-api-key: yk0j1r7knnyl4e18ip6azbi10jyypc6k-hvmrv96d5487g1v4qpiippicopuw2f7x-ia1hl5fcjdrdif98tfsnunvnwfj4uf7q"})
    Single<ApiResponse<List<SecretQuestion>>> getQuestionList();

    @GET(value = "user/profile")
    @Headers({"Accept: application/json", "x-api-key: yk0j1r7knnyl4e18ip6azbi10jyypc6k-hvmrv96d5487g1v4qpiippicopuw2f7x-ia1hl5fcjdrdif98tfsnunvnwfj4uf7q"})
    Single<ApiResponse<User>> getUserProfile(@HeaderMap Map<String, String> authHeader);

    @POST(value = "user/photo/base-64")
    @Headers({"Accept: application/json", "x-api-key: yk0j1r7knnyl4e18ip6azbi10jyypc6k-hvmrv96d5487g1v4qpiippicopuw2f7x-ia1hl5fcjdrdif98tfsnunvnwfj4uf7q"})
    Single<ApiResponse<String>> updateProfilePicture(@HeaderMap Map<String, String> authHeader, @Body Map<String, String> body);

}
