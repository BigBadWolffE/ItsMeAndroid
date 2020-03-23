package com.indocyber.itsmeandroid.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.services.database.dao.ImageCardDao;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

/*
 *
 *
 *@Author
 *@Version
 */
public class PinActivityViewModel extends ViewModel {

    ImageCardDao dao;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> isDone = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<ImageCardModel> data = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<Boolean> getIsDone() {
        return isDone;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public MutableLiveData<ImageCardModel> getData() {
        return data;
    }

    @Inject
    public PinActivityViewModel(ImageCardDao dao) {
        this.dao = dao;
    }

    public void updateCard(ImageCardModel model) {
        isDone.setValue(false);
        isLoading.setValue(true);
        disposable.add(
                dao.update(model)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                isLoading.setValue(false);
                                isDone.setValue(true);
                            }

                            @Override
                            public void onError(Throwable e) {
                                error.setValue(e.getMessage());
                                isLoading.setValue(false);
                            }
                        })
        );
    }

    public void blockCard(int id) {
        isDone.setValue(false);
        isLoading.setValue(true);
        disposable.add(
                dao.blockCard(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                isLoading.setValue(false);
                                isDone.setValue(true);
                            }

                            @Override
                            public void onError(Throwable e) {
                                isLoading.setValue(false);
                                isDone.setValue(false);
                                error.setValue(e.getMessage());
                            }
                        })
        );
    }

//    public void updateTag() {
//        isDone.setValue(false);
//        isLoading.setValue(false);
//        disposable.add(dao.)
//    }


}
