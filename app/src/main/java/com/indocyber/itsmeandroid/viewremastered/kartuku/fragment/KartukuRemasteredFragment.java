package com.indocyber.itsmeandroid.viewremastered.kartuku.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.viewremastered.BaseFragment;
import com.indocyber.itsmeandroid.view.profile.adapter.TabAdapter;
import com.indocyber.itsmeandroid.viewmodel.HomeViewModel;
import com.indocyber.itsmeandroid.viewmodel.ViewModelFactory;

import javax.inject.Inject;

import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class KartukuRemasteredFragment extends BaseFragment {

    private HomeViewModel viewModel;
    private AlertDialog loader;
    private LinearLayout cardLayout;
    private LinearLayout emptyLayout;
    @Inject
    ViewModelFactory factory;
    public KartukuRemasteredFragment() {
        // Required empty public constructor
    }


    @Override
    protected int layoutRes() {
        return R.layout.fragment_kartuku_remastered;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(layoutRes(), container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabAdapter mTabAdapter = new TabAdapter(getChildFragmentManager());
        mTabAdapter.addFragment(new CreditCardList(), "Kartu Kredit");
        mTabAdapter.addFragment(new MemberCardList(), "Kartu Member");
        mTabAdapter.addFragment(new PersonalCardList(), "Kartu Personal");
        ViewPager mViewPager = view.findViewById(R.id.viewPagerKartuku);
        TabLayout mTabLayout = view.findViewById(R.id.tabLayoutKartuku);
        emptyLayout = view.findViewById(R.id.emptyCard);
        cardLayout = view.findViewById(R.id.cardContent);
        viewModel = ViewModelProviders.of(getActivity(), factory).get(HomeViewModel.class);
        loader = new SpotsDialog.Builder().setCancelable(false).setContext(getActivity()).build();
        viewModel.countCard();
        mViewPager.setAdapter(mTabAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
        observeViewModel();
    }

    public void observeViewModel() {
        viewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                loader.show();
            } else {
                loader.dismiss();
            }
        });

        viewModel.getCardCount().observe(this, integer -> {
            if (integer > 0) {
                emptyLayout.setVisibility(View.GONE);
                cardLayout.setVisibility(View.VISIBLE);
            } else {
                emptyLayout.setVisibility(View.VISIBLE);
                cardLayout.setVisibility(View.GONE);
            }
        });
    }
}
