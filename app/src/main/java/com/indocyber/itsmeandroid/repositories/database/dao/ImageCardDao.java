package com.indocyber.itsmeandroid.repositories.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.indocyber.itsmeandroid.model.ImageCardModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 *
 *
 *@author Muhammad Faisal
 *@version 1.0
 */

@Dao
public interface ImageCardDao {

    @Insert
    Completable insert(ImageCardModel card);

    @Query("Select * from imageCardModel")
    Flowable<List<ImageCardModel>> getAll();

    @Query("Select * from imageCardModel where id = :id")
    Flowable<ImageCardModel> getCardById(int id);


    @Query("Delete from imagecardmodel where id = :id")
    Completable delete(int id);

    @Update()
    Completable update(ImageCardModel note);
}
