package com.indocyber.itsmeandroid.viewremastered.home.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.model.PromoItemModel;
import com.indocyber.itsmeandroid.view.home.adapter.CardViewAdapter;
import com.indocyber.itsmeandroid.view.home.adapter.PromoPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeRemasteredFragment extends Fragment {

    public HomeRemasteredFragment() {
        // Required empty public constructor
    }

    private RecyclerView mCardRecyclerView;
    private RecyclerView mPromoRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_remastered, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCardRecyclerView = view.findViewById(R.id.recCardView);
        mPromoRecyclerView = view.findViewById(R.id.recPromoView);


    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initCard();
        initPromo();
    }
    private void initCard(){
        mCardRecyclerView.setLayoutManager( new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        CardViewAdapter adapter = new CardViewAdapter(new ArrayList<>(), getActivity());
        adapter.refreshCardList(generateCardList());
        mCardRecyclerView.setAdapter(adapter);
    }

    private void initPromo(){
        mPromoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        PromoPagerAdapter promoAdapter = new PromoPagerAdapter(new ArrayList<>(), getActivity());
        promoAdapter.refreshPromoList(generatePromoList());

        mPromoRecyclerView.setAdapter(promoAdapter);
    }
    private List<ImageCardModel> generateCardList() {
        List<ImageCardModel> cardList = new ArrayList<>();
        cardList.add(new ImageCardModel(R.drawable.img_bca_card_template, "1234123412341234", "Johan Sundstein", "12/25", "Rp 15.000.000", "12/20", "12/21", false));
//        cardList.add(new ImageCardModel(2, "4321432143214321", "Johan Sundstein", "12/25", "Rp 15.000.000", "12/20", "12/21", false));
        return cardList;
    }

    private List<PromoItemModel> generatePromoList() {
        List<PromoItemModel> promoList = new ArrayList<>();
        promoList.add(new PromoItemModel("Promo Starbuck", "Promo Starbuck", "27 Desember 2020", R.drawable.img_banner_starbuck));
        promoList.add(new PromoItemModel("Promo Starbuck2", "Promo Starbuck2", "27 Desember 2020", R.drawable.img_banner_starbuck));
        return promoList;
    }
}
