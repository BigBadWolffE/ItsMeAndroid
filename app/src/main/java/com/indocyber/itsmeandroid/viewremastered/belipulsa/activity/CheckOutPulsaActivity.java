package com.indocyber.itsmeandroid.viewremastered.belipulsa.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.Pulsa;

import java.util.Objects;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.INTENT_PARCELABLE;

public class CheckOutPulsaActivity extends AppCompatActivity {

    private TextView txtHargaPulsa;
    private TextView txtOperator;
    private TextView txtNomor;
    private TextView txtHargaBayar;
    private Button btnBayar;
    private Pulsa data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_pulsa);

        data = Objects.requireNonNull(getIntent().getExtras()).getParcelable(INTENT_PARCELABLE);

        initView();
        initData();
        onClick();


    }

    private void initView(){
        txtHargaPulsa = findViewById(R.id.txtHargaPulsa);
        txtOperator = findViewById(R.id.txtOperator);
        txtNomor = findViewById(R.id.txtNomor);
        txtHargaBayar = findViewById(R.id.txtHargaBayar);
        btnBayar = findViewById(R.id.btnBayar);
    }

    private void initData(){
        txtHargaPulsa.setText("Rp "+data.getHargaPulsa());
        txtOperator.setText(data.getOperator());
        txtNomor.setText(data.getHargaPulsa());
        txtHargaBayar.setText("Rp "+data.getHargaBayar());
    }

    private void onClick(){
    btnBayar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(CheckOutPulsaActivity.this,PembayaranPulsaActivity.class);
            intent.putExtra(INTENT_PARCELABLE,data);
            startActivity(intent);
        }
    });
    }
}
