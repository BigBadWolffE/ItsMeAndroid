package com.indocyber.itsmeandroid.view.home.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.media.Image;
import android.os.Bundle;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.model.PromoItemModel;
import com.indocyber.itsmeandroid.view.BaseActivity;
import com.indocyber.itsmeandroid.view.home.adapter.CardViewAdapter;
import com.indocyber.itsmeandroid.view.home.adapter.PromoPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeRemastered extends AppCompatActivity{

//    @Override
//    protected int layoutRes() {
//        return R.layout.activity_home_remastered;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_remastered);
        RecyclerView cardRecyclerView = findViewById(R.id.recCardView);
        RecyclerView promoRecyclerView = findViewById(R.id.recPromoView);
        cardRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        promoRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(cardRecyclerView);
        snapHelper.attachToRecyclerView(promoRecyclerView);
        CardViewAdapter adapter = new CardViewAdapter(new ArrayList<>(), this);
        PromoPagerAdapter promoAdapter = new PromoPagerAdapter(new ArrayList<>(), this);
        promoAdapter.refreshPromoList(generatePromoList());
        adapter.refreshCardList(generateCardList());
        cardRecyclerView.setAdapter(adapter);
        promoRecyclerView.setAdapter(promoAdapter);
    }

    private List<ImageCardModel> generateCardList() {
        List<ImageCardModel> cardList = new ArrayList<>();
        cardList.add(new ImageCardModel(1, "1234123412341234", "Johan Sundstein", "12/25", "Rp 15.000.000", "12/20", "12/21", false));
        cardList.add(new ImageCardModel(2, "4321432143214321", "Johan Sundstein", "12/25", "Rp 15.000.000", "12/20", "12/21", false));
        return cardList;
    }

    private List<PromoItemModel> generatePromoList() {
        List<PromoItemModel> promoList = new ArrayList<>();
        promoList.add(new PromoItemModel("Promo Starbuck", "Promo Starbuck", "27 Desember 2020",
                R.drawable.img_banner_starbuck));
        promoList.add(new PromoItemModel("Promo Starbuck2", "Promo Starbuck2", "27 Desember 2020",
                R.drawable.img_banner_starbuck));
        return promoList;
    }
}
