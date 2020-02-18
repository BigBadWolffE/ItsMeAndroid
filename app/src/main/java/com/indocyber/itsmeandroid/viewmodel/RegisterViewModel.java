package com.indocyber.itsmeandroid.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import android.app.Application;

import com.indocyber.itsmeandroid.model.ApiResponse;
import com.indocyber.itsmeandroid.model.SecretQuestion;
import com.indocyber.itsmeandroid.model.User;
import com.indocyber.itsmeandroid.repositories.SecretQuestionRepository;
import com.indocyber.itsmeandroid.repositories.UserRepository;

import java.util.List;

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
public class RegisterViewModel extends AndroidViewModel {

    SecretQuestionRepository secretQuestionRepository;
    UserRepository userRepository;

    private final String logtag = "REGISTER_VIEWMODEL";
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> isSaved = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<String> dataError = new MutableLiveData<>();
    private MutableLiveData<String> loginError = new MutableLiveData<>();
    private MutableLiveData<User> userData = new MutableLiveData<>();
    private MutableLiveData<List<SecretQuestion>> questionList = new MutableLiveData<>();

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        secretQuestionRepository = new SecretQuestionRepository(application);
        userRepository = new UserRepository(application);
    }

    public MutableLiveData<String> getDataError() {
        return dataError;
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

    public MutableLiveData<String> getLoginError() {
        return loginError;
    }

    public MutableLiveData<User> getUserData() {
        return userData;
    }

    public MutableLiveData<List<SecretQuestion>> getQuestionList() {
        return questionList;
    }


    public void register(User user, Integer newPin) {
        isLoading.setValue(true);
        disposable.add(
                userRepository.registerUser(user, newPin)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ApiResponse<String>>() {
                    @Override
                    public void onSuccess(ApiResponse<String> response) {
                        if (response.getCode() != 200) {
                            isLoading.setValue(false);
                            error.setValue(response.getMessage());
                            return;
                        }
                        isLoading.setValue(false);
                        isSaved.setValue(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading.setValue(false);
                        error.setValue(e.getMessage());
                    }
                })
        );
    }

    public void fetchQuestionList() {
        isLoading.setValue(true);
        disposable.add(
                secretQuestionRepository.fetchQuestionList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<SecretQuestion>>() {
                    @Override
                    public void onSuccess(List<SecretQuestion> secretQuestions) {
                        isLoading.setValue(false);
                        questionList.setValue(secretQuestions);
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading.setValue(false);
                        dataError.setValue(e.getMessage());
                    }
                })
        );
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
                            loginError.setValue(response.getMessage());
                            return;
                        }
                        userData.setValue(response.getContent());
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading.setValue(false);
                        loginError.setValue(e.getMessage());
                    }
                })
        );
    }


//    public void register(User user) {
//        isLoading.setValue(true);
//        disposable.add(
//                dao.checkIfEmailIsUsed(user.getEmail())
//                .flatMap(count -> {
//                    if (count > 0) {
//                        return Single.error(new Throwable("Email already used"));
//                    }
//                    return Single.just(0);
//                })
//                .flatMapCompletable(o -> dao.insert(user))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableCompletableObserver() {
//                    @Override
//                    public void onComplete() {
//                        isLoading.setValue(false);
//                        isSaved.setValue(true);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        isLoading.setValue(false);
//                        error.setValue(e.getMessage());
//                    }
//                })
//        );
//    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
