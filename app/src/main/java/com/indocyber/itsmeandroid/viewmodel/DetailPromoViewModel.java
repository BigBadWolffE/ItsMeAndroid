package com.indocyber.itsmeandroid.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indocyber.itsmeandroid.model.ApiResponse;
import com.indocyber.itsmeandroid.model.PromoItemModel;
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
public class DetailPromoViewModel extends ViewModel {

    ApiService service;

    @Inject
    public DetailPromoViewModel(ApiService service) {
        this.service = service;
    }

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<PromoItemModel> promo = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<PromoItemModel> getPromo() {
        return promo;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public void fetchPromo(String authKey, String id) {
        isLoading.setValue(true);
        disposable.add(
                service.getPromoDetail(authKey, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ApiResponse<PromoItemModel>>() {
                    @Override
                    public void onSuccess(ApiResponse<PromoItemModel> response) {
                        isLoading.setValue(false);
                        if (response.getStatus() != 200) error.setValue(response.getMessage());
                        else promo.setValue(response.getContent());
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading.setValue(false);
                        error.setValue("Gagal menghubungkan ke server");
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
