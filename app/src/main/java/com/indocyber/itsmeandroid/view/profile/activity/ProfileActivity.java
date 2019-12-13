package com.indocyber.itsmeandroid.view.profile.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.view.profile.adapter.TabAdapter;
import com.indocyber.itsmeandroid.view.profile.fragment.DetailProfileFragment;
import com.indocyber.itsmeandroid.view.profile.fragment.ProfileKTPFragment;
import com.indocyber.itsmeandroid.view.profile.fragment.ProfileNPWPFragment;
import com.indocyber.itsmeandroid.view.profile.fragment.ProfilePassportFragment;

public class ProfileActivity extends AppCompatActivity {

    private TabAdapter mTabAdapter;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Profile");
            getSupportActionBar().setElevation(0f);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mViewPager = findViewById(R.id.viewPagerProfile);
        mTabLayout = findViewById(R.id.tabLayoutProfile);
        mTabAdapter = new TabAdapter(getSupportFragmentManager());
        mTabAdapter.addFragment(new DetailProfileFragment(), "Detail Profile");
        mTabAdapter.addFragment(new ProfileKTPFragment(), "KTP");
        mTabAdapter.addFragment(new ProfileNPWPFragment(), "NPWP");
        mTabAdapter.addFragment(new ProfilePassportFragment(), "Passport");
        mViewPager.setAdapter(mTabAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
