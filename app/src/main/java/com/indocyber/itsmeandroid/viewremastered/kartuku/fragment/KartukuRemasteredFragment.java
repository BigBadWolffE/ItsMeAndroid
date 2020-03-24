package com.indocyber.itsmeandroid.viewremastered.kartuku.fragment;

import android.app.Person;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.view.BaseFragment;
import com.indocyber.itsmeandroid.view.profile.adapter.TabAdapter;
import com.indocyber.itsmeandroid.view.profile.fragment.DetailProfileFragment;
import com.indocyber.itsmeandroid.view.profile.fragment.ProfileKTPFragment;
import com.indocyber.itsmeandroid.view.profile.fragment.ProfileNPWPFragment;
import com.indocyber.itsmeandroid.view.profile.fragment.ProfilePassportFragment;
import com.indocyber.itsmeandroid.viewmodel.HomeViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class KartukuRemasteredFragment extends BaseFragment {

    private HomeViewModel viewModel;
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
        viewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
        mViewPager.setAdapter(mTabAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
