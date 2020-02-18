package com.indocyber.itsmeandroid.repositories;

import android.app.Application;
import android.util.Log;

import com.indocyber.itsmeandroid.di.ApplicationComponent;
import com.indocyber.itsmeandroid.di.DaggerApplicationComponent;
import com.indocyber.itsmeandroid.di.DatabaseModule;
import com.indocyber.itsmeandroid.model.SecretQuestion;
import com.indocyber.itsmeandroid.services.database.dao.SecretQuestionDao;
import com.indocyber.itsmeandroid.services.network.ApiService;
import com.indocyber.itsmeandroid.utilities.commonclass.NetworkException;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 *
 *
 *@author Muhammad Faisal
 *@version 1.0
 */
public class SecretQuestionRepository {

    private SecretQuestionDao dao;
    private ApiService service;
    private final static String logTag = "QUESTION_REPOSITORY";

    @Inject
    public SecretQuestionRepository(Application application) {
        ApplicationComponent component = DaggerApplicationComponent.builder()
                .databaseModule(new DatabaseModule(application))
                .build();
        this.service = component.apiService();
        this.dao = component.secretQuestionDao();
    }

    public Single<List<SecretQuestion>> fetchQuestionList(){
        return dao.getAllQuestion()     //Load question in database
                .flatMap(secretQuestions -> {
                    if (secretQuestions.size() < 1) {
                        return Single.error(new Exception("No Data"));
                    }
                    return Single.just(secretQuestions);
                })
                .onErrorResumeNext(     //if error occured or no data found
                        service.getSecretQuestions()    //get question from API
                        .flatMap(response -> {
                            if (response.getCode() == 200) {
                                return Single.just(response.getContent());
                            }
                            return Single.error(
                                    new NetworkException("Network error." + response.getMessage()));
                        }).map(secretQuestions -> {     //if data retrieved from api
                            dao.insert(secretQuestions)     //store it in database
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(Schedulers.io())
                                    .subscribe(() -> Log.d(logTag, "Data stored"),
                                            throwable -> Log.d(logTag, "Data stored"));
                            return secretQuestions;
                        })
                );
    }
}
