package com.indocyber.itsmeandroid.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.repositories.database.AppDatabase;
import com.indocyber.itsmeandroid.repositories.database.dao.ImageCardDao;
import com.indocyber.itsmeandroid.repositories.database.typeconverter.ListStringTypeConverter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 *
 *
 *@author
 *@version
 */

public class PromotionViewModel extends AndroidViewModel {
    private ImageCardDao dao;

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<List<ImageCardModel>> cardList = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<List<String>> tagList = new MutableLiveData<>();

    public PromotionViewModel(@NonNull Application application) {
        super(application);
        dao = AppDatabase.getInstance(application).imageCardDao();
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<List<ImageCardModel>> getCardList() {
        return cardList;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public MutableLiveData<List<String>> getTagList() {
        return tagList;
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

    public void getAllTaglist() {
        isLoading.setValue(true);
        disposable.add(
                dao.getAllTagList()
                        .map(strings -> {
                            List<String> tagList = new ArrayList<>();
                            for (String tag: strings) {
                                tagList.addAll(ListStringTypeConverter.fromString(tag));
                            }
                            return tagList;
                        })
                        .distinct()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<List<String>>() {
                            @Override
                            public void onNext(List<String> tags) {
                                isLoading.setValue(false);
                                tagList.setValue(tags);
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

    public void getCardByTag(String tag) {
        isLoading.setValue(true);
        disposable.add(
                dao.getCardByTag("%\"" + tag + "\"%")
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(model -> {
                            isLoading.setValue(false);
                            cardList.setValue(model);
                        }, e -> {
                            Log.e("ContacCC",e.getMessage()+"");
                            error.setValue(e.getMessage());
                            e.printStackTrace();
                            isLoading.setValue(false);
                        })
        );
    }
}
