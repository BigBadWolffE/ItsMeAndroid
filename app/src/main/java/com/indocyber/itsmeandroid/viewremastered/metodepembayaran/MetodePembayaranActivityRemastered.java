package com.indocyber.itsmeandroid.viewremastered.metodepembayaran;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.indocyber.itsmeandroid.R;

import java.util.ArrayList;
import java.util.List;

public class MetodePembayaranActivityRemastered extends AppCompatActivity implements MetodePembayaranAdapter.Listener{
    private RecyclerView rcMetode;
    public static List<MetodePembayaranModel> mMetodePembayaran = new ArrayList<>();
    public static MetodePembayaranAdapter mMetodePembayaranAdapter;
    public static TextView tambahBtn;
    public static ImageView backButton;

    private String[] bankNames = {

            "Kartu Kredit BCA",
            "Kartu Kredit Citi Bank",
            "Kartu Kredit ANZ"
    };
    private String[] cardId = {

            "3454 XXXX XXXX 3455",
            "8576 XXXX XXXX 4563",
            "7672 XXXX XXXX 8495"

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metode_pembayaran_remastered);


        mMetodePembayaran = initialize();
        rcMetode = findViewById(R.id.rc_metode_pembayaran);
        mMetodePembayaranAdapter = new MetodePembayaranAdapter(initialize(),MetodePembayaranActivityRemastered.this, this);
        LinearLayoutManager horizontalLayoutManager =
                new LinearLayoutManager(MetodePembayaranActivityRemastered.this);
        rcMetode.setLayoutManager(horizontalLayoutManager);
        rcMetode.setAdapter(mMetodePembayaranAdapter);
        backButton = findViewById(R.id.imageView5);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tambahBtn = findViewById(R.id.btnTambah);
        tambahBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tambah = new Intent(MetodePembayaranActivityRemastered.this,TambahMetodePembayaranActivityRemastered.class);
                startActivityForResult(tambah, 71);
                finish();
            }
        });

    }

    private List<MetodePembayaranModel> initialize() {
        List<MetodePembayaranModel> allList = new ArrayList<>();
        for (int i=0 ; i<bankNames.length ; i++) {
            allList.add(new MetodePembayaranModel(bankNames[i], cardId[i]));
        }
        return allList;
    }
    @Override
    public void onClick(int position) {

    }
}
