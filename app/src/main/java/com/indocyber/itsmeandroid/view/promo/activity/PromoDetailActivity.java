package com.indocyber.itsmeandroid.view.promo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.indocyber.itsmeandroid.R;

public class PromoDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Promo Detail");
            getSupportActionBar().setElevation(0f);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
