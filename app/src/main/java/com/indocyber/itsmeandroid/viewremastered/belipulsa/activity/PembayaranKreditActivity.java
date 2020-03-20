package com.indocyber.itsmeandroid.viewremastered.belipulsa.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.Pulsa;

import java.util.Objects;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.INTENT_PARCELABLE;

public class PembayaranKreditActivity extends AppCompatActivity {
    private Pulsa data;
    private Button btnBayar;
    private ImageButton imageBtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran_kredit);
        data = Objects.requireNonNull(getIntent().getExtras()).getParcelable(INTENT_PARCELABLE);
        initView();
        onClick();
    }

    private void initView(){
        btnBayar = findViewById(R.id.btnBayar);
        imageBtnBack = findViewById(R.id.imageBtnBack);
    }

    private void onClick(){
        imageBtnBack.setOnClickListener(v ->{
            finish();
        });

        btnBayar.setOnClickListener( v -> {
            Intent intent = new Intent(this,WebviewPembayaranActivity.class);
            startActivity(intent);
        });
    }
}
