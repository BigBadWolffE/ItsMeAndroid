package com.indocyber.itsmeandroid.repositories.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.indocyber.itsmeandroid.model.ImageCardModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 *
 *
 *@author Muhammad Faisal, Nafsan
 *@version 1.0
 */

@Dao
public interface ImageCardDao {

    @Insert
    Completable insert(ImageCardModel card);

    @Query("Select * from imageCardModel order by id")
    Flowable<List<ImageCardModel>> getAll();

    @Query("Select * from imageCardModel where id = :id")
    Flowable<ImageCardModel> getCardById(int id);

    @Update()
    Completable update(ImageCardModel card);

    @Query("Select * from imageCardModel where isBlockedCard = 0 ")
    Observable<List<ImageCardModel>> getActiveCard();

    @Query("Delete from imagecardmodel where id = :id")
    Completable delete(int id);

    @Query("Update imagecardmodel set isBlockedCard = 1 where id = :id")
    Completable blockCard(int id);

    @Query("Update ImageCardModel set isBlockedCard = 1 where isBlockedCard = 0")
    Completable blockAllCard();

    @Query("Select tagList from ImageCardModel where id = :id")
    Single<List<String>> getTaglistByCardId(int id);

    @Query("Update ImageCardModel set tagList = :newTagList where id = :id")
    Completable updateCardTagList(int id, String newTagList);

    @Query("Select tagList from ImageCardModel where tagList is not null")
    Observable<List<String>> getAllTagList();

    @Query("Select * from ImageCardModel where id = :id")
    Observable<ImageCardModel> getSingleCardObservable(int id);

    @Query("Select * from ImageCardModel A where tagList like :tag")
    Observable<List<ImageCardModel>> getCardByTag(String tag);
}
