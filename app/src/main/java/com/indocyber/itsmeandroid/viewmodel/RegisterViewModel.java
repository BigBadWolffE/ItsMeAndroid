package com.indocyber.itsmeandroid.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.indocyber.itsmeandroid.model.User;
import com.indocyber.itsmeandroid.repositories.database.AppDatabase;
import com.indocyber.itsmeandroid.repositories.database.dao.UserDao;

import io.reactivex.Single;
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
public class RegisterViewModel extends AndroidViewModel {

    private UserDao dao;
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> isSaved = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<Boolean> getIsSaved() {
        return isSaved;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        dao = AppDatabase.getInstance(application).userDao();
    }

    public void register(User user) {
        isLoading.setValue(true);
        disposable.add(
                dao.checkIfEmailIsUsed(user.getEmail())
                .flatMap(count -> {
                    if (count > 0) {
                        return Single.error(new Throwable("Email already used"));
                    }
                    return Single.just(0);
                })
                .flatMapCompletable(o -> dao.insert(user))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        isLoading.setValue(false);
                        isSaved.setValue(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading.setValue(false);
                        error.setValue(e.getMessage());
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
