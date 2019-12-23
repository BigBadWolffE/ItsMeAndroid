package com.indocyber.itsmeandroid.view.promo.fragment;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.PromoItemModel;
import com.indocyber.itsmeandroid.view.promo.adapter.PromoItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class NearbyPromoFragment extends Fragment implements PromoItemAdapter.Listener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView mPromoItemRecycler;
    private List<PromoItemModel> mResourceList = new ArrayList<>();
    private PromoItemAdapter mPromoItemAdapter;

    private String[] titleList = {
            "Abuba Steak Exclusive Promo and Get Cashback 20% Off",
            "Bandar Jakarta Dining Exclusive and Get 10% Discount Off",
            "Starbucks Exclusive Promo Buy 1 get 1 Free Cappucino"
    };

    private int[] imgList = {
            R.drawable.img_dummy_promoalacarte,
            R.drawable.img_dummy_promobandarjakarta,
            R.drawable.img_dummy_promostarbuck
    };

    private String[] descList = {
            "Promo ini merupakan program promo kerjasama alacarte dengan Abuba Steak",
            "Promo ini merupakan program promo kerjasama alacarte dengan Bandar Jakarta",
            "Promo ini merupakan program promo kerjasama alacarte dengan Starbuck & Bank Mandiri"
    };

    private String[] periodeList = {
            "10 - 31 Desember 2019",
            "1 - 25 Desember 2019",
            "10 - 31 Desember 2019"
    };

//    private OnFragmentInteractionListener mListener;

    public NearbyPromoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static NearbyPromoFragment newInstance(String param1, String param2) {
        NearbyPromoFragment fragment = new NearbyPromoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nearby_promo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mResourceList = initialize();
        mPromoItemRecycler = view.findViewById(R.id.recyclerNearbyPromoList);
        mPromoItemAdapter = new PromoItemAdapter(mResourceList, getActivity(), this);
        LinearLayoutManager horizontalLayoutManager =
                new LinearLayoutManager(getActivity());
        mPromoItemRecycler.setLayoutManager(horizontalLayoutManager);
        mPromoItemRecycler.setAdapter(mPromoItemAdapter);
    }

    private List<PromoItemModel> initialize() {
        List<PromoItemModel> allList = new ArrayList<>();
        for (int i=0 ; i<imgList.length ; i++) {
            allList.add(new PromoItemModel(titleList[i], descList[i], periodeList[i], imgList[i]));
        }
        return allList;
    }

    @Override
    public void onClick(int position) {

    }

    //    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
