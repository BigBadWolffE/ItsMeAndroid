package com.indocyber.itsmeandroid.view.promo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.indocyber.itsmeandroid.R;

public class PromoDetailActivity extends AppCompatActivity {
    private String title, desc, periode;
    private int imgPromo;
    private ImageView mPromoImage;
    private TextView mTitle, mDesc, mPeriode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_detail);

        title = getIntent().getExtras().getString("titlePromo");
        imgPromo = getIntent().getExtras().getInt("imgPromo");
        desc = getIntent().getExtras().getString("descPromo");
        periode = getIntent().getExtras().getString("periodePromo");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Promo "+title);
            getSupportActionBar().setElevation(0f);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mPromoImage = findViewById(R.id.imgPromoDetailItem);
        mTitle = findViewById(R.id.lblPromoDetailTitle);
        mPeriode = findViewById(R.id.lblPromoDetailTglPeriode);
        mDesc = findViewById(R.id.lblPromoDetailDesc);
        mPromoImage.setImageResource(imgPromo);
        mTitle.setText(title);
        mPeriode.setText(periode);
        mDesc.setText(desc);
    }
}
