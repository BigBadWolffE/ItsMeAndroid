package com.indocyber.itsmeandroid.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indocyber.itsmeandroid.di.DaggerApplicationComponent;
import com.indocyber.itsmeandroid.model.BlockAllCardModel;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.services.database.AppDatabase;
import com.indocyber.itsmeandroid.services.database.dao.ImageCardDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Muhammad Faisal
 * @version 1.0
 */

public class HomeViewModel extends ViewModel {

    ImageCardDao dao;

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<List<ImageCardModel>> cardList = new MutableLiveData<>();
    private MutableLiveData<List<BlockAllCardModel>> blockAllCardList = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public HomeViewModel(ImageCardDao dao) {
        this.dao = dao;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<List<ImageCardModel>> getCardList() {
        return cardList;
    }

    public MutableLiveData<List<BlockAllCardModel>> getBlockCardList() {
        return blockAllCardList;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public void fetchAllCardList() {
        isLoading.setValue(true);
        disposable.add(
                dao.getAll()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(imageCardModels -> {
                            isLoading.setValue(false);
                            if (imageCardModels != null) cardList.setValue(imageCardModels);
                        }, e -> {
                            isLoading.setValue(false);
                            error.setValue(e.getMessage());

                        })
        );
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
                                if (imageCardModels != null) cardList.setValue(imageCardModels);
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

    public void fetchBlockAllCardList() {
        isLoading.setValue(true);
        disposable.add(
                dao.getActiveCard()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<List<ImageCardModel>>() {
                            @Override
                            public void onNext(List<ImageCardModel> imageCardModels) {
                                isLoading.setValue(false);

                                if (imageCardModels != null) {
                                 List<BlockAllCardModel> blockCardList = new ArrayList<>();
                                    for (int i = 0; i < imageCardModels.size(); i++) {
                                        blockCardList.add(new BlockAllCardModel(
                                                imageCardModels.get(i).getId(),
                                                imageCardModels.get(i).getImage(),
                                                imageCardModels.get(i).getNumberCard(),
                                                imageCardModels.get(i).getNameCard(),
                                                imageCardModels.get(i).getExpireCard(),
                                                imageCardModels.get(i).getCost(),
                                                imageCardModels.get(i).getPrintDate(),
                                                imageCardModels.get(i).getPrintDueDate(),
                                                imageCardModels.get(i).isBlockedCard()
                                        ));
                                    }
                                    blockAllCardList.setValue(blockCardList);
                                }
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
