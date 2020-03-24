package com.indocyber.itsmeandroid.viewremastered.promo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ItemPromoNearbyModel;
import com.indocyber.itsmeandroid.viewremastered.promo.Activity.DetailPromoActivity;
import com.indocyber.itsmeandroid.viewremastered.promo.Adapter.ItemPromoNearbyAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PromoTerdekatFragment extends Fragment implements ItemPromoNearbyAdapter.Listener {

    public PromoTerdekatFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.recyclerNearbyPromoList)
    RecyclerView mRecyclerNearbyPromo;

    private ItemPromoNearbyAdapter mPromoItemAdapter;

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

    private String[] jarak = {
            "0.3","1.8","2.2","2.7","3.3","4.5"
    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_promo_terdekat, container, false);
        ButterKnife.bind(this, view);
        mPromoItemAdapter = new ItemPromoNearbyAdapter(nearbyList(), getActivity(), this);
        GridLayoutManager horizontalLayourManager = new GridLayoutManager(getActivity(),2);
        mRecyclerNearbyPromo.setLayoutManager(horizontalLayourManager);
        mRecyclerNearbyPromo.setAdapter(mPromoItemAdapter);
        return view;
    }


    private  List<ItemPromoNearbyModel> nearbyList(){
        List<ItemPromoNearbyModel> nearbyModelList = new ArrayList<>();
        for (int i = 0; i <imgList.length ; i++) {
            nearbyModelList.add(new ItemPromoNearbyModel(titleList[i],descList[i],periodeList[i],imgList[i], jarak[i],""));
        }

        return nearbyModelList;
    }


    @Override
    public void ItemNearbyonClick(ItemPromoNearbyModel itemPromoNearbyModel) {
        Intent intent = new Intent(getActivity(), DetailPromoActivity.class);
        intent.putExtra("titlePromo", itemPromoNearbyModel.getTitle());
        intent.putExtra("imgPromo", itemPromoNearbyModel.getBanner());
        intent.putExtra("descPromo", itemPromoNearbyModel.getDesc());
        intent.putExtra("periodePromo", itemPromoNearbyModel.getPeriode());
        intent.putExtra("jarak", itemPromoNearbyModel.getJarak());
        startActivity(intent);
    }
}
