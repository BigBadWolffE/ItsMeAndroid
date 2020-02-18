package com.indocyber.itsmeandroid.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.services.database.AppDatabase;
import com.indocyber.itsmeandroid.services.database.dao.ImageCardDao;
import com.indocyber.itsmeandroid.services.database.typeconverter.ListStringTypeConverter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ContactCcViewModel extends AndroidViewModel {

    private ImageCardDao dao;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> isSaved = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<List<ImageCardModel>> data = new MutableLiveData<>();
    private MutableLiveData<List<String>> tagList = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    public ContactCcViewModel(@NonNull Application application) {
        super(application);
        dao = AppDatabase.getInstance(application).imageCardDao();
    }

    public MutableLiveData<List<ImageCardModel>> getAlldata() {
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

    public MutableLiveData<List<String>> getTagList() {
        return tagList;
    }

    public void getAll() {
        isLoading.setValue(true);
        disposable.add(
                dao.getAll()
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(model -> {
                            isLoading.setValue(false);
                            data.setValue(model);
                        }, e -> {
                            Log.e("ContacCC",e.getMessage()+"");
                            error.setValue(e.getMessage());
                            e.printStackTrace();
                            isLoading.setValue(false);
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
                            data.setValue(model);
                        }, e -> {
                            Log.e("ContacCC",e.getMessage()+"");
                            error.setValue(e.getMessage());
                            e.printStackTrace();
                            isLoading.setValue(false);
                        })
        );
    }

    public void insertNewTag(ImageCardModel model) {
        isLoading.setValue(true);
        disposable.add(
                dao.update(model)
                .andThen(dao.getAll())
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<ImageCardModel>>() {
                    @Override
                    public void onNext(List<ImageCardModel> cardList) {
                        isLoading.setValue(false);
                        data.setValue(cardList);
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

    public void getAllTaglist() {
        isLoading.setValue(true);
        disposable.add(
                dao.getAllTagList()
                .map(strings -> {
                    List<String> tagList = new ArrayList<>();
                    for (String tag: strings) {
                        if (!tag.equals("null")) {
                            tagList.addAll(ListStringTypeConverter.fromString(tag));
                        }
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


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
