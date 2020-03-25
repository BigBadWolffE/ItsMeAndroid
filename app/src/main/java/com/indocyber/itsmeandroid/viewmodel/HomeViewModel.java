package com.indocyber.itsmeandroid.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indocyber.itsmeandroid.di.DaggerApplicationComponent;
import com.indocyber.itsmeandroid.model.BlockAllCardModel;
import com.indocyber.itsmeandroid.model.ApiResponse;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.model.PromoItemModel;
import com.indocyber.itsmeandroid.model.PromoMenuModel;
import com.indocyber.itsmeandroid.services.database.AppDatabase;
import com.indocyber.itsmeandroid.services.database.dao.ImageCardDao;
import com.indocyber.itsmeandroid.services.database.typeconverter.ListStringTypeConverter;
import com.indocyber.itsmeandroid.services.network.ApiService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Muhammad Faisal
 * @version 1.0
 */

public class HomeViewModel extends ViewModel {

    ImageCardDao dao;
    ApiService service;

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<List<ImageCardModel>> cardList = new MutableLiveData<>();
    private MutableLiveData<List<String>> tagList = new MutableLiveData<>();
    private MutableLiveData<List<BlockAllCardModel>> blockAllCardList = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<List<PromoItemModel>> promoList = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public HomeViewModel(ImageCardDao dao, ApiService service) {
        this.service = service;
        this.dao = dao;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<List<ImageCardModel>> getCardList() {
        return cardList;
    }

    public MutableLiveData<List<String>> getTagList() {
        return tagList;
    }

    public MutableLiveData<List<BlockAllCardModel>> getBlockCardList() {
        return blockAllCardList;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public MutableLiveData<List<PromoItemModel>> getPromoList() {
        return promoList;
    }

    public void fetchAllCardList() {
        isLoading.setValue(true);
        disposable.add(
                dao.getAll()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(imageCardModels -> {
                            isLoading.setValue(false);
                            if (imageCardModels != null) cardList.setValue(imageCardModels);
                        }, e -> {
                            isLoading.setValue(false);
                            error.setValue(e.getMessage());

                        })
        );
    }

    public void fetchCardList() {
        isLoading.setValue(true);
        disposable.add(
                dao.getActiveCard()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<List<ImageCardModel>>() {
                            @Override
                            public void onNext(List<ImageCardModel> imageCardModels) {
                                isLoading.setValue(false);
                                if (imageCardModels != null) cardList.setValue(imageCardModels);
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

    public void fetchBlockAllCardList() {
        isLoading.setValue(true);
        disposable.add(
                dao.getActiveCard()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<List<ImageCardModel>>() {
                            @Override
                            public void onNext(List<ImageCardModel> imageCardModels) {
                                isLoading.setValue(false);

                                if (imageCardModels != null) {
                                 List<BlockAllCardModel> blockCardList = new ArrayList<>();
                                    for (int i = 0; i < imageCardModels.size(); i++) {
                                        blockCardList.add(new BlockAllCardModel(
                                                imageCardModels.get(i).getId(),
                                                imageCardModels.get(i).getImage(),
                                                imageCardModels.get(i).getNumberCard(),
                                                imageCardModels.get(i).getNameCard(),
                                                imageCardModels.get(i).getExpireCard(),
                                                imageCardModels.get(i).getCost(),
                                                imageCardModels.get(i).getPrintDate(),
                                                imageCardModels.get(i).getPrintDueDate(),
                                                imageCardModels.get(i).isBlockedCard()
                                        ));
                                    }
                                    blockAllCardList.setValue(blockCardList);
                                }
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

    public void fetchPromoList(String kategori) {
        isLoading.setValue(true);
        HashMap<String, String> body = new HashMap<>();
        body.put("category", kategori);
        body.put("latitude", "99");
        body.put("longitude", "99");
        disposable.add(
            service.getPromoList("ZXJ3YW5keS53aWpheWFAaW5kb2N5YmVyLmNvLmlkOnJhaGFzaWE=", body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ApiResponse<List<PromoItemModel>>>() {

                    @Override
                    public void onSuccess(ApiResponse<List<PromoItemModel>> listApiResponse) {
                        isLoading.setValue(false);
                        if (listApiResponse.getStatus() != 200) {
                            error.setValue(listApiResponse.getMessage());
                        }
                        promoList.setValue(listApiResponse.getContent());
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading.setValue(false);
                        error.setValue("Gagal menghubungkan ke server.");
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
                        .first(new ArrayList<>())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<String>>() {
//                            @Override
//                            public void onNext(List<String> tags) {
//
//                            }

                            @Override
                            public void onSuccess(List<String> tags) {
                                isLoading.setValue(false);
                                if (tags != null) {
                                    if (tags.size() > 0)
                                        tagList.setValue(tags);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                isLoading.setValue(false);
                                error.setValue(e.getMessage());
                            }
//
//                            @Override
//                            public void onComplete() {
//                                isLoading.setValue(false);
//                            }
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
                            cardList.setValue(model);
                        }, e -> {
                            Log.e("ContacCC",e.getMessage()+"");
                            error.setValue(e.getMessage());
                            e.printStackTrace();
                            isLoading.setValue(false);
                        })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
