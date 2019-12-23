package com.indocyber.itsmeandroid.view.home.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;


import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.content.Intent;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.view.blockcc.fragment.BlockCCFragment;
import com.indocyber.itsmeandroid.view.contactcc.ContactCCFragment;
import com.indocyber.itsmeandroid.view.home.fragment.HomeFragment;
import com.indocyber.itsmeandroid.view.promo.activity.PromoActivity;
import com.indocyber.itsmeandroid.view.promo.fragment.AllPromoFragment;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mToggle;
    private TextView mToolbarText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
         mToolbarText = (TextView) findViewById(R.id.txtToolbar);
        if (getSupportActionBar() != null) {

            if (mToolbarText != null) {
                mToolbarText.setText("Welcome back, Jordan");
            }
        }

        mDrawer = findViewById(R.id.drawerLayout);


        NavigationView navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            Fragment currentFragment = new HomeFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content_main, currentFragment)
                    .commit();
        }
    }

    public void getFragmentHome(){
        Fragment currentFragment = new HomeFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_main, currentFragment)
                .commit();
        //setmToolbarHomeFragment();
    }
    public void getFragmentBlock(){
        Fragment currentFragment = new BlockCCFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content_main, currentFragment)
                .addToBackStack(null)
                .commit();
        if (mToolbarText != null) {
           mToolbarText.setText("Block");
        }
    }
    public void setmToolbarBlockFragment(){
        if (mToolbarText != null) {
            mToolbarText.setText("Block");
        }
    }

    public void setmToolbarContactFragment(){
        if (mToolbarText != null) {
            mToolbarText.setText("Contact");
        }
    }

    public void setmToolbarHomeFragment(){
        if (mToolbarText != null) {
            mToolbarText.setText("Welcome back, Jordan");
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        mToggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mDrawer.removeDrawerListener(mToggle);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        Fragment fragment = null;

        String title = "";

        if (id == R.id.navHome) {
            title = "Welcome back, Jordan";
            fragment = new HomeFragment();
            mToolbarText.setText(title);
        } else if (id == R.id.navContact) {
            title = "Contact";
            mToolbarText.setText(title);
            fragment = new ContactCCFragment();

        } else if (id == R.id.navBlock) {

            setmToolbarBlockFragment();
            fragment = new BlockCCFragment();

        } else if (id == R.id.navPromo) {
            Intent intent = new Intent(this, PromoActivity.class);
            startActivity(intent);
        } else if (id == R.id.navAboutUs) {

        } else if (id == R.id.navSettings) {

        } else if (id == R.id.navLogout) {

        }

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main, fragment)
                    .commit();
        }

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
