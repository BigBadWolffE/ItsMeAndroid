package com.indocyber.itsmeandroid.services.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.indocyber.itsmeandroid.model.User;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 *
 *
 *@author Muhammad Faisal
 *@version 1.0
 */

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insert(User user);

    @Update
    Completable update(User user);

    @Query("Select * from User where email = :email and password = :password")
    Single<User> login(String email, String password);

    @Query("Select * from User where email = :email")
    Single<User> getUserData(String email);

    @Query("Select Count(0) from User where email = :email")
    Single<Integer> checkIfEmailIsUsed(String email);
}
