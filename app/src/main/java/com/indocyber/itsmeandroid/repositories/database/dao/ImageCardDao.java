package com.indocyber.itsmeandroid.repositories.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.indocyber.itsmeandroid.model.ImageCardModel;

import java.util.List;

import io.reactivex.Completable;
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
    Observable<List<ImageCardModel>> getAll();

    @Query("Select * from imageCardModel where isBlockedCard = 0 ")
    Observable<List<ImageCardModel>> getActiveCard();

    @Query("Delete from imagecardmodel where id = :id")
    Completable delete(int id);
}
