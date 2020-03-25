package com.indocyber.itsmeandroid.viewremastered.promo.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ItemPromoNearbyModel;
import com.indocyber.itsmeandroid.model.PromoItemModel;
import com.indocyber.itsmeandroid.view.BaseFragment;
import com.indocyber.itsmeandroid.viewmodel.HomeViewModel;
import com.indocyber.itsmeandroid.viewmodel.ViewModelFactory;
import com.indocyber.itsmeandroid.viewremastered.promo.Activity.DetailPromoActivity;
import com.indocyber.itsmeandroid.viewremastered.promo.Adapter.ItemPromoNearbyAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class PromoSpesialDiscountFragment extends BaseFragment implements ItemPromoNearbyAdapter.Listener {

    private HomeViewModel viewModel;
    @Inject
    ViewModelFactory factory;
    private AlertDialog loader;

    public PromoSpesialDiscountFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.recyclerDiskonPromoList)
    RecyclerView mrecyclerDiskon;

    private ItemPromoNearbyAdapter mPromoAdapter;
    private String[] titleList = {
            "Dining Exclusive Get 10% Discount off",
            "Bakmi GM promo Rp 20.200",
            "Geprek Bensu, September Bersama",
            "Makan Berdua Hemat Rp 108.000",
            "Bandar Jakarta Dining Exclusive and Get 10% Discount Off",
            "Starbucks Exclusive Promo Buy 1 get 1 Free Cappucino"
    };

    private int[] imgList = {
            R.drawable.img_promo_1,
            R.drawable.img_promo_2,
            R.drawable.img_promo_3,
            R.drawable.img_promo_4,
            R.drawable.img_promo_5,
            R.drawable.img_promo_6,
    };

    private String[] descList = {
            "Promo ini merupakan program promo kerjasama alacarte dengan Abuba Steak",
            "Promo ini merupakan program promo kerjasama alacarte dengan Bandar Jakarta",
            "Promo ini merupakan program promo kerjasama alacarte dengan Starbuck & Bank Mandiri",
            "Promo ini merupakan program promo kerjasama alacarte dengan Abuba Steak",
            "Promo ini merupakan program promo kerjasama alacarte dengan Bandar Jakarta",
            "Promo ini merupakan program promo kerjasama alacarte dengan Starbuck & Bank Mandiri"
    };

    private String[] periodeList = {
            "12 Apr - 20 Mei",
            "12 Feb - 10 Apr",
            "01 Mar - 20 Mei",
            "12 Apr - 30 Mei",
            "12 Apr - 30 Mei",
            "12 Apr - 30 Mei"
    };

    private String[] jarak = {
            "0.3", "1.8", "2.2", "2.7", "3.3", "4.5"
    };

    private  String[] diskon = {
            "70","50","40","30","20","10"
    };


    @Override
    protected int layoutRes() {
        return R.layout.fragment_promo_makanan;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(layoutRes(), container, false);
        ButterKnife.bind(this, view);
        viewModel = ViewModelProviders.of(getParentFragment().getActivity(), factory).get(HomeViewModel.class);
        mPromoAdapter = new ItemPromoNearbyAdapter(new ArrayList<>(), getActivity(),
                this, ItemPromoNearbyAdapter.SPECIAL_DISCOUNT);
        GridLayoutManager horizontalLayourManager = new GridLayoutManager(getActivity(), 2);
        mrecyclerDiskon.setLayoutManager(horizontalLayourManager);
        mrecyclerDiskon.setAdapter(mPromoAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loader = new SpotsDialog.Builder()
                .setCancelable(false)
                .setContext(getParentFragment().getActivity())
                .build();
        viewModel.fetchPromoList("Special Discount");
        observeViewModel();
    }

    private void observeViewModel(){
        viewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading) loader.show();
            else loader.dismiss();
        });

        viewModel.getPromoList().observe(this, promoMenuModels -> {
            mPromoAdapter.refreshList(promoMenuModels);
        });
    }

    private List<ItemPromoNearbyModel> diskonList() {
        List<ItemPromoNearbyModel> diskonModelList = new ArrayList<>();
        for (int i = 0; i < imgList.length; i++) {
            diskonModelList.add(new ItemPromoNearbyModel(titleList[i], descList[i], periodeList[i], imgList[i], jarak[i], diskon[i]));
        }

        return diskonModelList;
    }


    @Override
    public void ItemNearbyonClick(PromoItemModel itemPromoNearbyModel) {
        Intent intent = new Intent(getActivity(), DetailPromoActivity.class);
        intent.putExtra("idPromo", itemPromoNearbyModel.getId());
        intent.putExtra("titlePromo", itemPromoNearbyModel.getTitle());
        intent.putExtra("imgPromo", itemPromoNearbyModel.getBanner());
        intent.putExtra("descPromo", itemPromoNearbyModel.getDesc());
        intent.putExtra("periodePromo", itemPromoNearbyModel.getPeriode());
        intent.putExtra("jarak", "");
        intent.putExtra("diskon", itemPromoNearbyModel.getDistance());
        startActivity(intent);
    }
}
