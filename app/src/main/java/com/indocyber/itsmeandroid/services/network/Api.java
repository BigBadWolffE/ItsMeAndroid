package com.indocyber.itsmeandroid.services.network;

import com.indocyber.itsmeandroid.model.ApiResponse;
import com.indocyber.itsmeandroid.model.ProfileKTPModel;
import com.indocyber.itsmeandroid.model.ProfileNPWPModel;
import com.indocyber.itsmeandroid.model.ProfilePassportModel;
import com.indocyber.itsmeandroid.model.PromoItemModel;
import com.indocyber.itsmeandroid.model.PromoMenuModel;
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

    String BASE_URL = "http://34.87.100.8:7771/";
    String PROFILE_IMAGE = BASE_URL + "profile/getprofileimage/mobile";
    String KTP_IMAGE = "http://10.100.10.251:7771/profile/getktpimage/mobile";
    String NPWP_IMAGE = "http://10.100.10.251:7771/profile/getnpwpimage/mobile";
    String PASSPORT_IMAGE = "http://10.100.10.251:7771/profile/getpassportimage/mobile";
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
    @PATCH(value = "profile/updateprofileimage/mobile")
    Single<ApiResponse<String>> updateProfilePicture(@HeaderMap Map<String, String> authHeader, @Body Map<String, String> body);

    @PATCH(value = "profile/updateprofile/mobile")
    @Headers({"Accept: " + ACCEPT})
    Single<ApiResponse<String>> updateProfile(@HeaderMap Map<String, String> authHeader, @Body HashMap<String, String> body);

    @POST(value = "authentication/registration/mobile")
    @Headers({"Accept: " + ACCEPT})
    Single<ApiResponse<User>> register(@Body HashMap <String, Object> body);

    @GET(value = "profile/getprofile/mobile")
    @Headers({"Accept: " + ACCEPT})
    Single<ApiResponse<User>> getUserProfile(@HeaderMap Map<String, String> authHeader);

    @GET(value = "profile/getpassport/mobile")
    @Headers({"Accept: " + ACCEPT})
    Single<ApiResponse<ProfilePassportModel>> getUserPassport(@HeaderMap Map<String, String> authHeader);

    @PATCH(value = "profile/updatepassport/mobile")
    @Headers({"Accept: " + ACCEPT})
    Single<ApiResponse<String>> updatePassport(@HeaderMap Map<String, String> authHeader, @Body HashMap<String, String> body);

    @GET(value = "profile/getktp/mobile")
    @Headers({"Accept: " + ACCEPT})
    Single<ApiResponse<ProfileKTPModel>> getUserKtp(@HeaderMap Map<String, String> authHeader);

    @PATCH(value = "profile/updatektp/mobile")
    @Headers({"Accept: " + ACCEPT})
    Single<ApiResponse<String>> updateKtp(@HeaderMap Map<String, String> authHeader, @Body HashMap<String, String> body);

    @GET(value = "profile/getnpwp/mobile")
    @Headers({"Accept: " + ACCEPT})
    Single<ApiResponse<ProfileNPWPModel>> getUserNPWP(@HeaderMap Map<String, String> authHeader);

    @PATCH(value = "profile/updatenpwp/mobile")
    @Headers({"Accept: " + ACCEPT})
    Single<ApiResponse<String>> updateNpwp(@HeaderMap Map<String, String> authHeader, @Body HashMap<String, String> body);

    @POST(value = "authentication/login/mobile")
    @Headers({"Accept: " + ACCEPT})
    Single<ApiResponse<User>> login(@HeaderMap Map<String, String> authHeader);

    @POST(value = "promo/getpromolist/mobile")
    @Headers({"Accept: " + ACCEPT})
    Single<ApiResponse<List<PromoItemModel>>> getPromoList(@HeaderMap Map<String, String> authHeader, @Body HashMap<String, String> body);

    @POST(value = "promo/getpromodetail/mobile")
    @Headers({"Accept: " + ACCEPT})
    Single<ApiResponse<PromoItemModel>> getPromoDetail(@HeaderMap Map<String, String> authHeader, @Body HashMap<String, String> body);
}
