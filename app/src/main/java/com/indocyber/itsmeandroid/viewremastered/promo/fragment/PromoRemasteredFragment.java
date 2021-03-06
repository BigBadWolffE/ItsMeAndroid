package com.indocyber.itsmeandroid.viewremastered.promo.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.PromoMenuModel;
import com.indocyber.itsmeandroid.viewremastered.BaseFragment;
import com.indocyber.itsmeandroid.viewremastered.promo.Adapter.PromoMenuAdapter;
import com.indocyber.itsmeandroid.viewmodel.HomeViewModel;
import com.indocyber.itsmeandroid.viewmodel.ViewModelFactory;
import com.indocyber.itsmeandroid.viewremastered.promo.Adapter.MenuPromoAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PromoRemasteredFragment extends BaseFragment implements PromoMenuAdapter.Listener {
    @BindView(R.id.recyclerMenuPromoHorizontal)
    RecyclerView mPromoMenuRecycler;
    @BindView(R.id.layoutNoPromo)
    ConstraintLayout mLayoutNoPromo;
    @BindView(R.id.frameLayoutPromo)
    FrameLayout mLayoutPromo;
    private MenuPromoAdapter mPromoMenuAdapter, mNearbyMenuAdapter, mDinigMenuAdapter, mCollectionMenuAdapter;
    private Fragment mFragmentIndicator = null;
    private HomeViewModel viewModel;
    @Inject
    ViewModelFactory factory;
    private String[] titleList = {

    };


    @Override
    protected int layoutRes() {
        return R.layout.fragment_promo_remastered;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View promoRemasteredView = inflater.inflate(layoutRes(), container, false);
        ButterKnife.bind(this, promoRemasteredView);
        viewModel = ViewModelProviders.of(getActivity(), factory).get(HomeViewModel.class);
        mPromoMenuAdapter = new MenuPromoAdapter(initializeMenu(), getActivity(), this);
        mNearbyMenuAdapter = new MenuPromoAdapter(nearbyActive(), getActivity(), this);
        mDinigMenuAdapter = new MenuPromoAdapter(dinningActive(), getActivity(), this);
        mCollectionMenuAdapter = new MenuPromoAdapter(collectionActive(), getActivity(), this);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mPromoMenuRecycler.setLayoutManager(horizontalLayoutManager);
        mPromoMenuRecycler.setAdapter(mPromoMenuAdapter);

        loadFragment(mFragmentIndicator);
        getPromo();
        return promoRemasteredView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void getPromo() {
        if (titleList.length != 0) {
            mPromoMenuRecycler.setVisibility(View.GONE);
            mLayoutPromo.setVisibility(View.GONE);
            mLayoutNoPromo.setVisibility(View.VISIBLE);
        }
    }


    private List<PromoMenuModel> initializeMenu() {
        List<PromoMenuModel> allActiveList = new ArrayList<>();
        allActiveList.clear();
        allActiveList.add(new PromoMenuModel("Semua Promo", R.color.colorPrimary, R.drawable.button_coloraccent));
        allActiveList.add(new PromoMenuModel("Near By", R.color.grey2, R.drawable.button_border_grey));
        allActiveList.add(new PromoMenuModel("Collection", R.color.grey2, R.drawable.button_border_grey));
        allActiveList.add(new PromoMenuModel("Special Discount", R.color.grey2, R.drawable.button_border_grey));
        return allActiveList;
    }

    private List<PromoMenuModel> nearbyActive() {
        List<PromoMenuModel> nearbyActiveList = new ArrayList<>();
        nearbyActiveList.clear();
        nearbyActiveList.add(new PromoMenuModel("Semua Promo", R.color.grey2, R.drawable.button_border_grey));
        nearbyActiveList.add(new PromoMenuModel("Near By", R.color.colorPrimary, R.drawable.button_coloraccent));
        nearbyActiveList.add(new PromoMenuModel("Collection", R.color.grey2, R.drawable.button_border_grey));
        nearbyActiveList.add(new PromoMenuModel("Special Discount", R.color.grey2, R.drawable.button_border_grey));
        return nearbyActiveList;
    }

    private List<PromoMenuModel> dinningActive() {
        List<PromoMenuModel> dinningActiveList = new ArrayList<>();
        dinningActiveList.clear();
        dinningActiveList.add(new PromoMenuModel("Semua Promo", R.color.grey2, R.drawable.button_border_grey));
        dinningActiveList.add(new PromoMenuModel("Near By", R.color.grey2, R.drawable.button_border_grey));
        dinningActiveList.add(new PromoMenuModel("Collection", R.color.grey2, R.drawable.button_border_grey));
        dinningActiveList.add(new PromoMenuModel("Special Discount", R.color.colorPrimary, R.drawable.button_coloraccent));
        return dinningActiveList;
    }

    private List<PromoMenuModel> collectionActive() {
        List<PromoMenuModel> collectionActiveList = new ArrayList<>();
        collectionActiveList.clear();
        collectionActiveList.add(new PromoMenuModel("Semua Promo", R.color.grey2, R.drawable.button_border_grey));
        collectionActiveList.add(new PromoMenuModel("Near By", R.color.grey2, R.drawable.button_border_grey));
        collectionActiveList.add(new PromoMenuModel("Collection", R.color.colorPrimary, R.drawable.button_coloraccent));
        collectionActiveList.add(new PromoMenuModel("Special Discount", R.color.grey2, R.drawable.button_border_grey));
        return collectionActiveList;
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        if (fragment == null) {
            fragment = new SemuaPromoFragment();
        }
        fragmentTransaction.replace(R.id.frameLayoutPromo, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(int position) {
        if (position == 0) {
            loadFragment(new SemuaPromoFragment());
            mPromoMenuRecycler.swapAdapter(mPromoMenuAdapter, false);
        } else if (position == 1) {
            loadFragment(new PromoTerdekatFragment());
            mPromoMenuRecycler.swapAdapter(mNearbyMenuAdapter, false);
        } else if (position == 2) {
            loadFragment(new KoleksiPromoFragment());
            mPromoMenuRecycler.swapAdapter(mCollectionMenuAdapter, false);
        } else if (position == 3) {
            loadFragment(new PromoSpesialDiscountFragment());
            mPromoMenuRecycler.swapAdapter(mDinigMenuAdapter, false);
        }
    }
}
