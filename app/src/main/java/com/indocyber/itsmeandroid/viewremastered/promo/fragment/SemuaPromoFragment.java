package com.indocyber.itsmeandroid.viewremastered.promo.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.PromoItemModel;
import com.indocyber.itsmeandroid.view.promo.adapter.PromoItemAdapter;
import com.indocyber.itsmeandroid.viewremastered.promo.Activity.DetailPromoActivity;
import com.indocyber.itsmeandroid.viewremastered.promo.Adapter.ItemPromoAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SemuaPromoFragment extends Fragment implements ItemPromoAdapter.Listener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    @BindView(R.id.recyclerSemuaPromoList)
    RecyclerView mPromoItemRecyler;
    private List<PromoItemModel> mResourceList = new ArrayList<>();
    private ItemPromoAdapter mPromoItemAdapter;

    private  String[] titleList = {
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
            "Promo ini merupakan program promo kerjasama alacarte dengan Starbuck & Bank Mandiri" ,
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

    public SemuaPromoFragment() {
        // Required empty public constructor
    }

    public static  SemuaPromoFragment newInstance(String param1,String param2){
        SemuaPromoFragment fragment = new SemuaPromoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View semuaPromoFragment = inflater.inflate(R.layout.fragment_semua_promo, container, false);
        ButterKnife.bind(this, semuaPromoFragment);
        if (getArguments() != null){
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mPromoItemAdapter = new ItemPromoAdapter(initialize(), getActivity(), this);
        GridLayoutManager horizontalLayourManager = new GridLayoutManager(getActivity(),2);
        mPromoItemRecyler.setLayoutManager(horizontalLayourManager);
        mPromoItemRecyler.setAdapter(mPromoItemAdapter);

        return  semuaPromoFragment;
    }

    private  List<PromoItemModel> initialize(){
        List<PromoItemModel> semuaList = new ArrayList<>();
        for (int i = 0; i < imgList.length ; i++) {
            semuaList.add(new PromoItemModel(titleList[i],descList[i],periodeList[i],imgList[i]));
        }
        return semuaList;
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getActivity(), DetailPromoActivity.class);
//        intent.putExtra("titlePromo", titleList[position]);
//        intent.putExtra("imgPromo", imgList[position]);
//        intent.putExtra("descPromo", descList[position]);
//        intent.putExtra("periodePromo", periodeList[position]);
        startActivity(intent);
    }
}
