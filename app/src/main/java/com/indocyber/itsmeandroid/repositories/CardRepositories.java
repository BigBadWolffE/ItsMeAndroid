package com.indocyber.itsmeandroid.repositories;

import com.indocyber.itsmeandroid.di.DaggerApplicationComponent;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.services.database.dao.ImageCardDao;

import javax.inject.Inject;

import io.reactivex.Completable;

/**
 *
 *
 *@author Muhammad Faisal
 *@version 1.0
 */

public class CardRepositories {

    ImageCardDao cardDao;

    @Inject
    public CardRepositories(ImageCardDao dao) {
        this.cardDao = dao;
    }

    public Completable addCreditCard(ImageCardModel card) {
        return cardDao.insert(card);
    }
}
