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
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 *
 *
 *@author Muhammad Faisal
 *@version 1.0
 */

public class BlockConfirmationPinViewModel extends AndroidViewModel {

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

    public BlockConfirmationPinViewModel(@NonNull Application application) {
        super(application);
        dao = AppDatabase.getInstance(application).imageCardDao();
    }

    public void blockCard(int id) {
        isLoading.setValue(true);
        disposable.add(
                dao.blockCard(id)
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
