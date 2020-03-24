package com.indocyber.itsmeandroid.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.services.database.dao.ImageCardDao;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/*
 *
 *
 *@Author
 *@Version
 */
public class TagKartuViewModel extends ViewModel {

    ImageCardDao dao;

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> isDone = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<ImageCardModel> cardData = new MutableLiveData<>();
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

    public MutableLiveData<ImageCardModel> getCardData() {
        return cardData;
    }

    @Inject
    public TagKartuViewModel(ImageCardDao dao) {
        this.dao = dao;
    }

    public void getCardById(int id) {
        isLoading.setValue(true);
        disposable.add(dao.getSingleCardObservable(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ImageCardModel>() {
                    @Override
                    public void onNext(ImageCardModel imageCardModel) {
                        isLoading.setValue(false);
                        cardData.setValue(imageCardModel);
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading.setValue(false);
                        error.setValue("Gagal mengambil data kartu");
                    }

                    @Override
                    public void onComplete() {
                        isLoading.setValue(false);
                    }
                })
        );
    }

    public void saveTag(int id, String tags) {
        isDone.setValue(false);
        isLoading.setValue(true);
        disposable.add(dao.updateCardTagList(id, tags)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onError(Throwable e) {
                        isLoading.setValue(false);
                        isDone.setValue(false);
                        error.setValue("Gagal mengambil data kartu");
                    }

                    @Override
                    public void onComplete() {
                        isLoading.setValue(false);
                        isDone.setValue(true);
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
