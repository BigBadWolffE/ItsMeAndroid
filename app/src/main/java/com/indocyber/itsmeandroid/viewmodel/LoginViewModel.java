package com.indocyber.itsmeandroid.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indocyber.itsmeandroid.model.ApiResponse;
import com.indocyber.itsmeandroid.model.User;
import com.indocyber.itsmeandroid.repositories.UserRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 *
 *
 *@author Muhammad Faisal
 *@version 1.0
 */
public class LoginViewModel extends ViewModel {

    private UserRepository userRepository;
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<User> user = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    @Inject
    public LoginViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void login(String authKey) {
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
                                return;
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

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
