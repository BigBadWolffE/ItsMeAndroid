package com.indocyber.itsmeandroid.viewmodel;

import android.util.Log;

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
            userRepository.login(authKey)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<ApiResponse<User>>() {
                        @Override
                        public void onSuccess(ApiResponse<User> response) {
                            isLoading.setValue(false);
                            if (response.getStatus() != 200) {
                                error.setValue(response.getMessage());
                                return;
                            }
                            Log.e("ViewModelLogin",response.getStatus()+"");
                            user.setValue(response.getContent());
                        }

                        @Override
                        public void onError(Throwable e) {
                            isLoading.setValue(false);
                            error.setValue(e.getMessage());
                            Log.e("ViewModelLogin",e.getMessage());
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
