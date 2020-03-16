package com.indocyber.itsmeandroid.viewremastered.home.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.viewremastered.akun.AkunRemasteredFragment;
import com.indocyber.itsmeandroid.viewremastered.home.fragment.HomeRemasteredFragment;
import com.indocyber.itsmeandroid.viewremastered.kartuku.fragment.KartukuRemasteredFragment;
import com.indocyber.itsmeandroid.viewremastered.promo.fragment.PromoRemasteredFragment;

import static com.indocyber.itsmeandroid.utilities.core.Animations.fadeOutIn;

public class HomeRemastered extends AppCompatActivity{

//    @Override
//    protected int layoutRes() {
//        return R.layout.activity_home_remastered;
//    }

    private BottomNavigationView mNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_remastered);

        if (savedInstanceState == null) {
            HomeRemasteredFragment fragment = new HomeRemasteredFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_main, fragment);
            fragmentTransaction.commit();
        }

        initComponent();

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
                } else if (id == R.id.action_promo) {
                    fadeOutIn(content);
                    fragment = new PromoRemasteredFragment();
                } else if (id == R.id.action_kartuku) {
                    fadeOutIn(content);
                    fragment = new KartukuRemasteredFragment();
                } else if (id == R.id.action_akun) {
                    fadeOutIn(content);
                    fragment = new AkunRemasteredFragment();
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
