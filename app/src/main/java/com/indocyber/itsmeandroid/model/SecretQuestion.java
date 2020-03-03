package com.indocyber.itsmeandroid.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import com.indocyber.itsmeandroid.utilities.commonclass.SpinnerItem;

/**
 *
 *
 *@author Muhammad Faisal
 *@version 1.0
 */

@Entity
public class SecretQuestion implements SpinnerItem {

    @PrimaryKey
    @SerializedName("secretQuestionId")
    @NonNull
    private String secretQuestionId;
    @SerializedName("secretQuestionValue")
    private String question;

    public SecretQuestion(String secretQuestionId, String question) {
        this.secretQuestionId = secretQuestionId;
        this.question = question;
    }

    public String getSecretQuestionId() {
        return secretQuestionId;
    }

    public void setSecretQuestionId(String secretQuestionId) {
        this.secretQuestionId = secretQuestionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public String getId() {
        return secretQuestionId;
    }

    @Override
    public String getName() {
        return question;
    }
}
