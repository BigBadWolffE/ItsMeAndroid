package com.indocyber.itsmeandroid.viewremastered.home.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.view.BaseActivity;
import com.indocyber.itsmeandroid.view.addcc.AddCcActivity;
import com.indocyber.itsmeandroid.viewmodel.HomeViewModel;
import com.indocyber.itsmeandroid.viewmodel.ViewModelFactory;
import com.indocyber.itsmeandroid.viewremastered.AddKartuPersonal.AddKartuPersonal;
import com.indocyber.itsmeandroid.viewremastered.addkartumember.AddKartuMember;
import com.indocyber.itsmeandroid.viewremastered.akun.AkunRemasteredFragment;
import com.indocyber.itsmeandroid.viewremastered.home.fragment.HomeRemasteredFragment;
import com.indocyber.itsmeandroid.viewremastered.kartuku.fragment.KartukuRemasteredFragment;
import com.indocyber.itsmeandroid.viewremastered.loginandregister.LoginActivityRemastered;
import com.indocyber.itsmeandroid.viewremastered.loginandregister.LoginAuthActivityRemastered;
import com.indocyber.itsmeandroid.viewremastered.promo.fragment.PromoRemasteredFragment;

import javax.inject.Inject;

import static com.indocyber.itsmeandroid.utilities.core.Animations.fadeOutIn;
import static com.indocyber.itsmeandroid.utilities.core.Animations.showIn;
import static com.indocyber.itsmeandroid.utilities.core.Animations.showOut;

public class HomeRemastered extends BaseActivity {

    //    @Override
//    protected int layoutRes() {
//        return R.layout.activity_home_remastered;
//    }
    private boolean rotate = false;
    private BottomNavigationView mNavigation;
    private FloatingActionButton mFloatingActionButton;
    private LinearLayout mLinearPullUp, mLinearAddMembership,
            mLinearPersonalCard, mLinearAddCreditCard, mLinearBlockCC;
    @Inject
    ViewModelFactory factory;
    private HomeViewModel viewModel;

    @Override
    protected int layoutRes() {
        return R.layout.activity_home_remastered;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutRes());
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel.class);
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
            Intent intent = new Intent(this, AddKartuMember.class);
            startActivity(intent);
        });
        mLinearPersonalCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddKartuPersonal.class);
            startActivity(intent);
        });
        mLinearAddCreditCard.setOnClickListener(v -> {
                Intent intent = new Intent(this, AddCcActivity.class);
                startActivity(intent);
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
                    //showOutPullUpGone();
                } else if (id == R.id.action_promo) {
                    fadeOutIn(content);
                    fragment = new PromoRemasteredFragment();
                    //showOutPullUpGone();
                } else if (id == R.id.action_kartuku) {
                    fadeOutIn(content);
                    fragment = new KartukuRemasteredFragment();
                    //showOutPullUpGone();
                } else if (id == R.id.action_akun) {
                    fadeOutIn(content);
                    fragment = new AkunRemasteredFragment();
                    //showOutPullUpGone();
                }
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment)
                            .commit();
                }
                return true;
            }
        });
    }

    public void onClickPromo(){
        mNavigation.getMenu().findItem(R.id.action_promo).setChecked(true);
        Fragment fragment = null;
        fragment = new PromoRemasteredFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder ab = new AlertDialog.Builder(HomeRemastered.this);
        ab.setTitle("Its Me!");
        ab.setMessage("Apakah anda yakin untuk keluar?");
        ab.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //if you want to kill app . from other then your main avtivity.(Launcher)
//                android.os.Process.killProcess(android.os.Process.myPid());
//                System.exit(1);
                LoginAuthActivityRemastered.etusernameauth.setText("");
                Intent intent = new Intent(HomeRemastered.this, LoginAuthActivityRemastered.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                //if you want to finish just current activity

                HomeRemastered.this.finish();
            }
        });
        ab.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        ab.show();
    }

}
