package com.indocyber.itsmeandroid.viewremastered.belipulsa.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.Pulsa;

import java.util.Objects;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.INTENT_PARCELABLE;

public class PembayaranPulsaActivity extends AppCompatActivity {

    private LinearLayout linearKartuKredit;
    private LinearLayout linearTransferVirtualAccount;
    private LinearLayout linearPembayaranInstant;
    private TextView txtHargaBayar;
    private Pulsa data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran_pulsa);

        data = Objects.requireNonNull(getIntent().getExtras()).getParcelable(INTENT_PARCELABLE);

        initView();
        onClick();
        initData();
    }

    private void initData() {
        txtHargaBayar.setText(data.getHargaBayar());
    }

    private void initView(){
        linearKartuKredit = findViewById(R.id.linearKartuKredit);
        linearTransferVirtualAccount = findViewById(R.id.linearTransferVirtualAccount);
        linearPembayaranInstant = findViewById(R.id.linearPembayaranInstant);
        txtHargaBayar = findViewById(R.id.txtHargaBayar);
    }

    private void onClick(){
        linearKartuKredit.setOnClickListener( v->{
            Intent intent = new Intent(PembayaranPulsaActivity.this,PembayaranKreditActivity.class);
            intent.putExtra(INTENT_PARCELABLE,data);
            startActivity(intent);
        });

        linearTransferVirtualAccount.setOnClickListener( v->{

        });

        linearPembayaranInstant.setOnClickListener( v->{

        });
    }
}
