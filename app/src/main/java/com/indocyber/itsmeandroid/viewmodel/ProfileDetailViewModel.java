package com.indocyber.itsmeandroid.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indocyber.itsmeandroid.model.ApiResponse;
import com.indocyber.itsmeandroid.model.User;
import com.indocyber.itsmeandroid.repositories.UserRepository;
import com.indocyber.itsmeandroid.services.database.dao.UserDao;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 *
 *
 *@author Muhammad Faisal
 *@version 1.0
 */
public class ProfileDetailViewModel extends ViewModel {

    private UserRepository userRepository;
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<User> user = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<Boolean> profileUpdated = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }
    public MutableLiveData<User> getUser() {
        return user;
    }
    public MutableLiveData<String> getError() {
        return error;
    }

    public MutableLiveData<Boolean> getProfileUpdated() {
        return profileUpdated;
    }

    @Inject
    public ProfileDetailViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void getUserData(String authKey) {
        isLoading.setValue(true);
        disposable.add(
                userRepository.getProfile(authKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ApiResponse<User>>() {
                    @Override
                    public void onSuccess(ApiResponse<User> response) {
                        isLoading.setValue(false);
                        if (response.getCode() != 200) {
                            error.setValue(response.getMessage());
                        }
                        user.setValue(response.getContent());
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading.setValue(false);
                        error.setValue(e.getMessage());
                    }
                })
        );
    }

//    public void updateUserProfile(User user) {
//        isLoading.setValue(true);
//        disposable.add(
//                dao.update(user)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(new DisposableCompletableObserver() {
//
//                            @Override
//                            public void onComplete() {
//                                isLoading.setValue(false);
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                isLoading.setValue(false);
//                                error.setValue(e.getMessage());
//                            }
//                        })
//        );
//    }

    public void updateProfilePicture(String authKey, String base64) {
        profileUpdated.setValue(false);
        isLoading.setValue(true);
        disposable.add(
                userRepository.updateProfilePicture(authKey, base64)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ApiResponse<String>>() {
                    @Override
                    public void onSuccess(ApiResponse<String> response) {
                        isLoading.setValue(false);
                        if (response.getCode() != 200) {
                            error.setValue(response.getMessage());
                            return;
                        }
                        profileUpdated.setValue(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading.setValue(false);
                        error.setValue(e.getMessage());
                    }
                })
        );
    }

    public void updateProfile(String authKey, HashMap<String, String> body) {
        profileUpdated.setValue(false);
        isLoading.setValue(true);
        disposable.add(
                userRepository.updateProfile(authKey, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ApiResponse<String>>() {
                    @Override
                    public void onSuccess(ApiResponse<String> response) {
                        isLoading.setValue(false);
                        if (response.getCode() != 200) {
                            error.setValue(response.getMessage());
                            return;
                        }
                        profileUpdated.setValue(true);
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
