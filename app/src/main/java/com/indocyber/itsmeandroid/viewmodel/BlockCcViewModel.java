package com.indocyber.itsmeandroid.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.repositories.database.AppDatabase;
import com.indocyber.itsmeandroid.repositories.database.dao.ImageCardDao;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 *
 *
 *@author Muhammad Faisal
 *@version 1.0
 */
public class BlockCcViewModel extends AndroidViewModel {

    private ImageCardDao dao;
    private MutableLiveData<List<ImageCardModel>> cardList =  new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    public MutableLiveData<List<ImageCardModel>> getCardList() {
        return cardList;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public BlockCcViewModel(@NonNull Application application) {
        super(application);
        dao = AppDatabase.getInstance(application).imageCardDao();
    }

    public void fetchCardList() {
        isLoading.setValue(true);
        disposable.add(
                dao.getActiveCard()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<List<ImageCardModel>>() {
                            @Override
                            public void onNext(List<ImageCardModel> imageCardModels) {
                                isLoading.setValue(false);
                                if(imageCardModels != null) cardList.setValue(imageCardModels);
                            }

                            @Override
                            public void onError(Throwable e) {
                                isLoading.setValue(false);
                                error.setValue(e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                isLoading.setValue(false);
                            }
                        })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
