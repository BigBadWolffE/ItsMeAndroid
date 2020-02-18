package com.indocyber.itsmeandroid.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.indocyber.itsmeandroid.services.database.AppDatabase;
import com.indocyber.itsmeandroid.services.database.dao.ImageCardDao;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 *
 *
 *@author Muhammad Faisal
 *@version 1.0
 */
public class BlockAllConfirmationViewModel extends AndroidViewModel {
    private ImageCardDao dao;
    private MutableLiveData<Boolean> IsSuccess =  new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    public MutableLiveData<Boolean> getIsSuccess() {
        return IsSuccess;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public BlockAllConfirmationViewModel(@NonNull Application application) {
        super(application);
        dao = AppDatabase.getInstance(application).imageCardDao();
    }

    public void blockAllCard() {
        isLoading.setValue(true);
        disposable.add(
                dao.blockAllCard()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                isLoading.setValue(false);
                                IsSuccess.setValue(true);
                            }

                            @Override
                            public void onError(Throwable e) {
                                isLoading.setValue(false);
                                IsSuccess.setValue(false);
                                error.setValue(e.getMessage());
                            }
                        })
        );
    }

}
