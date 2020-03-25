package com.indocyber.itsmeandroid.viewremastered.metodepembayaran;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.indocyber.itsmeandroid.R;

public class TambahMetodePembayaranActivityRemastered extends AppCompatActivity {
    public static TextView tambahKartu;
    public static ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_metode_pembayaran_remastered);
        tambahKartu = findViewById(R.id.textView11);
        backButton =findViewById(R.id.imageView5);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tambahKartu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent tambah = new Intent(TambahMetodePembayaranActivityRemastered.this,TambahKartuPembayaranActivityRemastered.class);
                startActivityForResult(tambah,75);
            }
        });

    }
}
