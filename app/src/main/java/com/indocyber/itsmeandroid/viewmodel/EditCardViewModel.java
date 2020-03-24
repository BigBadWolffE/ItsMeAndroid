package com.indocyber.itsmeandroid.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.services.database.AppDatabase;
import com.indocyber.itsmeandroid.services.database.dao.ImageCardDao;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class EditCardViewModel extends ViewModel {

    ImageCardDao dao;

    @Inject
    public EditCardViewModel(ImageCardDao dao) {
        this.dao = dao;
    }

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> isSaved = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<ImageCardModel> data = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    public MutableLiveData<ImageCardModel> getData() {
        return data;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<Boolean> getIsSaved() {
        return isSaved;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public void getEditCard(int id) {
        isLoading.setValue(true);
        disposable.add(
                dao.getCardById(id)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(model -> {
                            isLoading.setValue(false);
                            data.setValue(model);
                        }, e -> {
                            error.setValue(e.getMessage());
                            e.printStackTrace();
                            isLoading.setValue(false);
                        })
        );

    }

    public void setIsSaved(ImageCardModel model) {
        isSaved.setValue(false);
        isLoading.setValue(true);
        disposable.add(
                dao.update(model)
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
                                error.setValue(e.getMessage());
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
