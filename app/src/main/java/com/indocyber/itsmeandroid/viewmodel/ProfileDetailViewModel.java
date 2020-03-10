package com.indocyber.itsmeandroid.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indocyber.itsmeandroid.model.ApiResponse;
import com.indocyber.itsmeandroid.model.ProfileKTPModel;
import com.indocyber.itsmeandroid.model.ProfileNPWPModel;
import com.indocyber.itsmeandroid.model.ProfilePassportModel;
import com.indocyber.itsmeandroid.model.User;
import com.indocyber.itsmeandroid.repositories.UserRepository;

import java.util.HashMap;

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
public class ProfileDetailViewModel extends ViewModel {

    private UserRepository userRepository;
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<User> user = new MutableLiveData<>();
    private MutableLiveData<ProfileKTPModel> userKtp = new MutableLiveData<>();
    private MutableLiveData<ProfilePassportModel> userPassport = new MutableLiveData<>();
    private MutableLiveData<ProfileNPWPModel> userNpwp = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<Boolean> profileUpdated = new MutableLiveData<>();
    private MutableLiveData<Boolean> ktpUpdated = new MutableLiveData<>();
    private MutableLiveData<Boolean> npwpUpdated = new MutableLiveData<>();
    private MutableLiveData<Boolean> passportUpdated = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }
    public MutableLiveData<User> getUser() {
        return user;
    }
    public MutableLiveData<String> getError() {
        return error;
    }

    public MutableLiveData<ProfilePassportModel> getUserPassport() {
        return userPassport;
    }

    public MutableLiveData<ProfileNPWPModel> getUserNpwp() {
        return userNpwp;
    }

    public MutableLiveData<ProfileKTPModel> getUserKtp() {
        return userKtp;
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
                        if (response.getStatus() != 200) {
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

    public void updateProfilePicture(String authKey, String base64, String extension) {
        profileUpdated.setValue(false);
        isLoading.setValue(true);
        disposable.add(
                userRepository.updateProfilePicture(authKey, base64, extension)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ApiResponse<String>>() {
                    @Override
                    public void onSuccess(ApiResponse<String> response) {
                        isLoading.setValue(false);
                        if (response.getStatus() != 200) {
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

    public void updateProfile(String authKey, User user) {
        profileUpdated.setValue(false);
        isLoading.setValue(true);
        disposable.add(
                userRepository.updateProfile(authKey, user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ApiResponse<String>>() {
                    @Override
                    public void onSuccess(ApiResponse<String> response) {
                        isLoading.setValue(false);
                        if (response.getStatus() != 200) {
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

    public void updateKtp(String authKey, ProfileKTPModel ktp, String extension) {
        ktpUpdated.setValue(false);
        isLoading.setValue(true);
        disposable.add(userRepository.updateKtp(authKey, ktp, extension)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ApiResponse<String>>(){

                    @Override
                    public void onSuccess(ApiResponse<String> response) {
                        isLoading.setValue(false);
                        if (response.getStatus() != 200) {
                            error.setValue(response.getMessage());
                            return;
                        }
                        ktpUpdated.setValue(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        error.setValue(e.getMessage());
                    }
                })
        );
    }

    public void getProfileKtp(String authKey) {
        isLoading.setValue(true);
        disposable.add(
                userRepository.getKtp(authKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ApiResponse<ProfileKTPModel>>() {
                    @Override
                    public void onSuccess(ApiResponse<ProfileKTPModel> response) {
                        isLoading.setValue(false);
                        if (response.getStatus() != 200) {
                            error.setValue(response.getMessage());
                            return;
                        }
                        userKtp.setValue(response.getContent());
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading.setValue(false);
                        error.setValue(e.getMessage());
                    }
                })
        );
    }

    public void getProfileNpwp(String authKey) {
        isLoading.setValue(true);
        disposable.add(
                userRepository.getNpwp(authKey)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<ApiResponse<ProfileNPWPModel>>() {
                            @Override
                            public void onSuccess(ApiResponse<ProfileNPWPModel> response) {
                                isLoading.setValue(false);
                                if (response.getStatus() != 200) {
                                    error.setValue(response.getMessage());
                                    return;
                                }
                                userNpwp.setValue(response.getContent());
                            }

                            @Override
                            public void onError(Throwable e) {
                                isLoading.setValue(false);
                                error.setValue(e.getMessage());
                            }
                        })
        );
    }

    public void getProfilePassport(String authKey) {
        isLoading.setValue(true);
        disposable.add(
                userRepository.getPassport(authKey)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<ApiResponse<ProfilePassportModel>>() {
                            @Override
                            public void onSuccess(ApiResponse<ProfilePassportModel> response) {
                                isLoading.setValue(false);
                                if (response.getStatus() != 200) {
                                    error.setValue(response.getMessage());
                                    return;
                                }
                                userPassport.setValue(response.getContent());
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
