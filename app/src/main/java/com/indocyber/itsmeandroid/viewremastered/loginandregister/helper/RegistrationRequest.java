package com.indocyber.itsmeandroid.viewremastered.loginandregister.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;

import com.google.gson.Gson;
import com.indocyber.itsmeandroid.viewremastered.loginandregister.PopUp.PopUpRegisterSucceedRemastered;
import com.indocyber.itsmeandroid.viewremastered.loginandregister.SetPinActivityRemastered;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import dmax.dialog.SpotsDialog;


public class RegistrationRequest  {
    public static String message;
    public static String responseData;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    public static void postRegistrationData(final Activity activity, RegistrationModel registrationModel){
        String name = SavePref.readName(activity);
        String email = SavePref.readEmail(activity);
        String phone = SavePref.readPhone(activity);
        String password = SavePref.readPass(activity);
        String pin = SetPinActivityRemastered.firstPinView.getText().toString();
        String secretQuestionId = SavePref.readSecretQuestion(activity);
        String answer = SavePref.readSecretAnswer(activity);
        AlertDialog dialog = new SpotsDialog.Builder().setCancelable(false).setContext(activity).build();
        registrationModel.setUserFullName(name);
        registrationModel.setUserEmail(email);
        registrationModel.setUserPhone(phone);
        registrationModel.setUserPassword(password);
        registrationModel.setUserPin(pin);
        registrationModel.setSecretQuestionId(secretQuestionId);
        registrationModel.setSecretAnswer(answer);

        String postBody = new Gson().toJson(registrationModel);
        Log.e("POST BODY", postBody);

        OkHttpClient client = new OkHttpClient();


        RequestBody body = RequestBody.create(JSON, postBody);
        Log.e("REQUEST BODY",body.toString());

        Request requestHttp = new Request.Builder()
                .url(Api.Registration)
                .post(body)
                .build();
        Log.e("URL",Api.Registration);

        client.newCall(requestHttp).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("Error",e.toString());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                responseData = response.body().string();
                Log.e("RESPONSE BODY", responseData);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.show();
                        try {
                            JSONObject json = new JSONObject(responseData);
                            if(response.code() == 200){
                                dialog.dismiss();
                                PopUpRegisterSucceedRemastered.showDialog(activity);
//                                activity.finish();
                            }else {
                                dialog.dismiss();
                                SetPinActivityRemastered.alertWrong(activity);
                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                    }
                });

            }
        });


    }


}
