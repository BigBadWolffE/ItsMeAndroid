package com.indocyber.itsmeandroid.services.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.indocyber.itsmeandroid.model.SecretQuestion;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 *
 *
 *@author Muhammad Faisal
 *@version 1.0
 */

@Dao
public interface SecretQuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(SecretQuestion question);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(List<SecretQuestion> questions);

    @Query("SELECT * FROM secretquestion WHERE secretQuestionId = :id")
    Single<SecretQuestion> getQuestionById(int id);

    @Query("SELECT * FROM secretquestion")
    Single<List<SecretQuestion>> getAllQuestion();
}
