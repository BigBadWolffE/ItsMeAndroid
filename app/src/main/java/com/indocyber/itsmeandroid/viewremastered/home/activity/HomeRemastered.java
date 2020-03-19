package com.indocyber.itsmeandroid.viewremastered.home.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.viewremastered.akun.AkunRemasteredFragment;
import com.indocyber.itsmeandroid.viewremastered.home.fragment.HomeRemasteredFragment;
import com.indocyber.itsmeandroid.viewremastered.kartuku.fragment.KartukuRemasteredFragment;
import com.indocyber.itsmeandroid.viewremastered.promo.fragment.PromoRemasteredFragment;

import static com.indocyber.itsmeandroid.utilities.core.Animations.fadeOutIn;
import static com.indocyber.itsmeandroid.utilities.core.Animations.showIn;
import static com.indocyber.itsmeandroid.utilities.core.Animations.showOut;

public class HomeRemastered extends AppCompatActivity {

    //    @Override
//    protected int layoutRes() {
//        return R.layout.activity_home_remastered;
//    }
    private boolean rotate = false;
    private BottomNavigationView mNavigation;
    private FloatingActionButton mFloatingActionButton;
    private LinearLayout mLinearPullUp, mLinearAddMembership,
            mLinearPersonalCard, mLinearAddCreditCard, mLinearBlockCC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_remastered);

        initView();

        if (savedInstanceState == null) {
            HomeRemasteredFragment fragment = new HomeRemasteredFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_main, fragment);
            fragmentTransaction.commit();
        }

        initComponent();
        initFab();
        initOnclick();


    }

    private void initView() {
        mFloatingActionButton = findViewById(R.id.floatingActionButton);
        mLinearPullUp = findViewById(R.id.linearPullUp);
        mLinearAddMembership = findViewById(R.id.linearAddMembership);
        mLinearPersonalCard = findViewById(R.id.linearPersonalCard);
        mLinearAddCreditCard = findViewById(R.id.linearAddCreditCard);
        mLinearBlockCC = findViewById(R.id.linearBlockCC);

        mFloatingActionButton.bringToFront();
        mLinearPullUp.setVisibility(View.GONE);
    }


    private void initFab() {
        mFloatingActionButton.setOnClickListener(v -> {
            if (rotate) {
                showOutPullUp();
            } else {
                showInPullUp();
            }

        });
    }

    private void showInPullUp() {
        mFloatingActionButton.setImageResource(R.drawable.ic_close);
        showIn(mLinearPullUp);
        rotate = true;
    }

    private void showOutPullUp() {
        mFloatingActionButton.setImageResource(R.drawable.ic_dehaze_white_24dp);
        showOut(mLinearPullUp);
        rotate = false;
    }

    private void showOutPullUpGone() {
        mFloatingActionButton.setImageResource(R.drawable.ic_dehaze_white_24dp);
        mLinearPullUp.setVisibility(View.GONE);
        rotate = false;
    }

    private void initOnclick() {
        mLinearAddMembership.setOnClickListener(v -> {

        });
        mLinearPersonalCard.setOnClickListener(v -> {

        });
        mLinearAddCreditCard.setOnClickListener(v -> {

        });
        mLinearBlockCC.setOnClickListener(v -> {

        });
    }

    private void initComponent() {
        mNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        FrameLayout content = findViewById(R.id.content_main);
        //navigation.findViewById(R.id.navigation_manpower).setVisibility(View.GONE);
        //navigation.getMenu().removeItem(R.id.navigation_manpower);


        mNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;

                int id = menuItem.getItemId();

                if (id == R.id.action_home) {
                    fadeOutIn(content);
                    fragment = new HomeRemasteredFragment();
                    showOutPullUpGone();
                } else if (id == R.id.action_promo) {
                    fadeOutIn(content);
                    fragment = new PromoRemasteredFragment();
                    showOutPullUpGone();
                } else if (id == R.id.action_kartuku) {
                    fadeOutIn(content);
                    fragment = new KartukuRemasteredFragment();
                    showOutPullUpGone();
                } else if (id == R.id.action_akun) {
                    fadeOutIn(content);
                    fragment = new AkunRemasteredFragment();
                    showOutPullUpGone();
                }
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment)
                            .commit();
                }
                return true;
            }
        });
    }


}
