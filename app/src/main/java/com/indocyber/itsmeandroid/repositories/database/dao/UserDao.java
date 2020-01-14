package com.indocyber.itsmeandroid.repositories.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.indocyber.itsmeandroid.model.User;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 *
 *
 *@author Muhammad Faisal
 *@version 1.0
 */

@Dao
public interface UserDao {

    @Insert
    Completable insert(User user);

    @Update
    Completable update(User user);

    @Query("Select * from User where email = :email and password = :password")
    Single<User> login(String email, String password);

    @Query("Select * from User where email = :email")
    Single<User> getUserData(String email);
}
