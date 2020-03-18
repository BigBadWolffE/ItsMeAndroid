package com.indocyber.itsmeandroid.viewremastered.promo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.indocyber.itsmeandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailPromoActivity extends AppCompatActivity {
    @BindView(R.id.btnShare)
    LinearLayout mBtnShaere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_promo);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btnShare)
    void sharePromo() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,"share promo");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}
