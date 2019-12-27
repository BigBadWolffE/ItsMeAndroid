package com.indocyber.itsmeandroid.viewmodel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.repositories.database.AppDatabase;
import com.indocyber.itsmeandroid.repositories.database.dao.ImageCardDao;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ContactCcViewModel extends AndroidViewModel {

    public ContactCcViewModel(@NonNull Application application) {
        super(application);
        dao = AppDatabase.getInstance(application).imageCardDao();
    }

    private ImageCardDao dao;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData();
    private MutableLiveData<Boolean> isSaved = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<List<ImageCardModel>> data = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    public void getAll() {
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

    public MutableLiveData<List<ImageCardModel>> getAlldata() {
        return data;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
