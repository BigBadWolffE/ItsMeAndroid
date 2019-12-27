package com.indocyber.itsmeandroid.view.promo.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.view.promo.adapter.PromoCollectionAdapter;
import com.indocyber.itsmeandroid.viewmodel.HomeViewModel;

import dmax.dialog.SpotsDialog;

public class CollectionPromoFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView mPromoCollectionRecycler;
    private PromoCollectionAdapter mPromoCollectionAdapter;
    private HomeViewModel viewModel;
    private AlertDialog loader;
    private View mViewOnCreated;

//    private OnFragmentInteractionListener mListener;

    public CollectionPromoFragment() {
        // Required empty public constructor
    }

    public static CollectionPromoFragment newInstance(String param1, String param2) {
        CollectionPromoFragment fragment = new CollectionPromoFragment();
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
        return inflater.inflate(R.layout.fragment_collection_promo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewOnCreated = view;

        viewModel = ViewModelProviders.of((FragmentActivity) getActivity()).get(HomeViewModel.class);
        viewModel.fetchAllCardList();
        observeViewModel();
        
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void observeViewModel() {
        viewModel.getIsLoading().observe((LifecycleOwner) getActivity(), isLoading -> {
            if (isLoading) {
                if (loader == null) {
                    loader = new SpotsDialog.Builder()
                            .setCancelable(false)
                            .setContext(getContext())
                            .build();
                }
                loader.show();
            } else {
                loader.dismiss();
            }
        });

        viewModel.getCardList().observe((LifecycleOwner) getActivity(), list -> {
            mPromoCollectionRecycler = mViewOnCreated.findViewById(R.id.recyclerCollectionPromoList);
            mPromoCollectionAdapter = new PromoCollectionAdapter(list, getActivity());
            LinearLayoutManager horizontalLayoutManager =
                    new LinearLayoutManager(getActivity());
            mPromoCollectionRecycler.setLayoutManager(horizontalLayoutManager);
            mPromoCollectionRecycler.setAdapter(mPromoCollectionAdapter);
        });
    }

}
