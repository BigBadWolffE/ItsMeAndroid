package com.indocyber.itsmeandroid.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indocyber.itsmeandroid.model.ApiResponse;
import com.indocyber.itsmeandroid.model.User;
import com.indocyber.itsmeandroid.services.network.ApiService;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/*
 *
 *
 *@Author
 *@Version
 */
public class EditProfileViewModel extends ViewModel {

    ApiService service;

    @Inject
    public EditProfileViewModel(ApiService service) {
        this.service = service;
    }

    private static final String TAG = "EditProfileViewModel";
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> isDone = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<User> userData = new MutableLiveData<>();
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

    public MutableLiveData<User> getUserData() {
        return userData;
    }

    public void fetchProfile(String authKey) {
        isLoading.setValue(true);
        disposable.add(
                service.getProfile(authKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ApiResponse<User>>() {
                    @Override
                    public void onSuccess(ApiResponse<User> response) {
                        isLoading.setValue(false);
                        if (response.getStatus() != 200) error.setValue(response.getMessage());
                        else userData.setValue(response.getContent());
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading.setValue(false);
                        error.setValue("Gagal Menghubungkan Ke Server.");
                    }
                })
        );
    }

    public void updateProfile(String auth, String email, String phone, String image, String ext) {
        isDone.setValue(false);
        isLoading.setValue(false);
        disposable.add(
                service.updateProfileNew(auth, email, phone, image, ext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ApiResponse<String>>() {
                    @Override
                    public void onSuccess(ApiResponse<String> response) {
                        isLoading.setValue(false);
                        if (response.getStatus() != 200) error.setValue(response.getMessage());
                        else isDone.setValue(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading.setValue(false);
                        isDone.setValue(false);
                        error.setValue("Gagal menghubungkan ke server");
                        Log.e(TAG, e.getMessage());
                    }
                })
        );
    }
}
