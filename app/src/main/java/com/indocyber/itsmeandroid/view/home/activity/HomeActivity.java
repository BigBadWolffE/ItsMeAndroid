package com.indocyber.itsmeandroid.view.home.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;


import android.app.AlertDialog;
import android.content.Intent;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.utilities.Preference;
import com.indocyber.itsmeandroid.view.aboutus.activity.AboutUsActivity;
import com.indocyber.itsmeandroid.view.blockcc.activity.BlockCCActivity;

import com.indocyber.itsmeandroid.view.contactcc.activity.ContactCCActivity;
import com.indocyber.itsmeandroid.view.home.fragment.HomeFragment;
import com.indocyber.itsmeandroid.view.promo.activity.PromoActivity;
import com.indocyber.itsmeandroid.view.register.RegistrationActivity;
import com.indocyber.itsmeandroid.view.splashscreen.SplashScreenActivity;

import dmax.dialog.SpotsDialog;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mToggle;
    private TextView mToolbarText;

    private Preference preference;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mToolbar = findViewById(R.id.toolbar);

        preference = new Preference(this);
        setSupportActionBar(mToolbar);
        mToolbarText = (TextView) findViewById(R.id.txtToolbar);

        alertDialog = new SpotsDialog.Builder()
                .setCancelable(false)
                .setContext(HomeActivity.this)
                .build();
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


    public void setmToolbarHomeFragment() {
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
            Intent intent = new Intent(this, ContactCCActivity.class);
            startActivity(intent);

        } else if (id == R.id.navBlock) {
            Intent intent = new Intent(this, BlockCCActivity.class);
            startActivity(intent);

        } else if (id == R.id.navPromo) {
            Intent intent = new Intent(this, PromoActivity.class);
            startActivity(intent);
        } else if (id == R.id.navAboutUs) {
            Intent intent = new Intent(this, AboutUsActivity.class);
            startActivity(intent);
        } else if (id == R.id.navSettings) {

        } else if (id == R.id.navLogout) {
            alertDialog.show();
            preference.setLoginFirstTime(false);
            Intent intent = new Intent(this, SplashScreenActivity.class);
            startActivity(intent);
            finish();
            alertDialog.dismiss();
            Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show();
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
