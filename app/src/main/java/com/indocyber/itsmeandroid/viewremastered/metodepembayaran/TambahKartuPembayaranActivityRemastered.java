package com.indocyber.itsmeandroid.viewremastered.metodepembayaran;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.viewremastered.metodepembayaran.PopUpBerhasilTambahKartu.PopUpSuksesTambahKartu;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class TambahKartuPembayaranActivityRemastered extends AppCompatActivity implements TambahPembayaranAdapter.Listener{
    private RecyclerView rcCardSelect;
    public static List<TambahPembayaranModel> tambahPembayaranModels =  new ArrayList<>();
    public static AlertDialog alertDialog;
    public static TambahPembayaranAdapter tambahPembayaranAdapter;
    public static TextView save;
    public static CheckBox checkAll;
    public static ImageView backButton;

    private int[] banner = {
            R.drawable.img_kartukredit_bca,
            R.drawable.img_kartukredit_citi,
            R.drawable.img_kartukredit_anz
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kartu_pembayaran_remastered);
        alertDialog = new SpotsDialog.Builder().setCancelable(false).setContext(TambahKartuPembayaranActivityRemastered.this).build();


        tambahPembayaranModels = initialize();
        rcCardSelect = findViewById(R.id.rc_kartu);
        tambahPembayaranAdapter = new TambahPembayaranAdapter(initialize(),TambahKartuPembayaranActivityRemastered.this, this);
        LinearLayoutManager horizontalLayoutManager =
                new LinearLayoutManager(TambahKartuPembayaranActivityRemastered.this);
        rcCardSelect.setLayoutManager(horizontalLayoutManager);
        rcCardSelect.setAdapter(tambahPembayaranAdapter);
        save = findViewById(R.id.btnTambah);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(() -> {
                    alertDialog.show();
                    new Handler().postDelayed(() -> {
                        alertDialog.dismiss();
//                        Intent intent = new Intent(SetPinActivityRemastered.this, PopUpRegisterSucceedRemastered.class);
//                        startActivity(intent);
                        PopUpSuksesTambahKartu.showDialog(TambahKartuPembayaranActivityRemastered.this);
                    }, 800);
                }, 200);

            }
        });
        backButton = findViewById(R.id.imageView5);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        checkAll = findViewById(R.id.allChecked);
        checkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkAll.isChecked()){
                    tambahPembayaranAdapter.selectAll();
                }
                else {
                    tambahPembayaranAdapter.unselectall();
                }
            }
        });
    }
    private List<TambahPembayaranModel> initialize() {
        List<TambahPembayaranModel> allList = new ArrayList<>();
        for (int i=0 ; i<banner.length ; i++) {
            allList.add(new TambahPembayaranModel(banner[i]));
        }
        return allList;
    }
    @Override
    public void onClick(int position) {

    }
}
