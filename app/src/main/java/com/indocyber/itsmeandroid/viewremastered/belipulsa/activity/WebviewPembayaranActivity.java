package com.indocyber.itsmeandroid.viewremastered.belipulsa.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

import com.indocyber.itsmeandroid.R;

public class WebviewPembayaranActivity extends AppCompatActivity {

    private ImageButton imageBtnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_pembayaran);

        initView();
        onClick();
    }

    private void initView(){
        imageBtnBack = findViewById(R.id.imageBtnBack);
    }

    private void onClick(){
        imageBtnBack.setOnClickListener(v ->{
            finish();
        });
    }
}
