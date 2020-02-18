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
    @SerializedName("secretquestionID")
    private int secretQuestionId;
    @SerializedName("question")
    private String question;

    public SecretQuestion(int secretQuestionId, String question) {
        this.secretQuestionId = secretQuestionId;
        this.question = question;
    }

    public int getSecretQuestionId() {
        return secretQuestionId;
    }

    public void setSecretQuestionId(int secretQuestionId) {
        this.secretQuestionId = secretQuestionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public long getId() {
        return secretQuestionId;
    }

    @Override
    public String getName() {
        return question;
    }
}
