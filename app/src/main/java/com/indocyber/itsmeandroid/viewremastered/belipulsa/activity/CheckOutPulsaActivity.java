package com.indocyber.itsmeandroid.viewremastered.belipulsa.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.Pulsa;

import java.util.Objects;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.INTENT_PARCELABLE;

public class CheckOutPulsaActivity extends AppCompatActivity {

    private TextView mTxtHargaPulsa;
    private TextView mTxtOperator;
    private TextView mTxtNomor;
    private TextView mTxtHargaBayar;
    private ImageButton mImageBtnBack;
    private Button mBtnBayar;
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

    private void initView() {
        mTxtHargaPulsa = findViewById(R.id.txtHargaPulsa);
        mTxtOperator = findViewById(R.id.txtOperator);
        mTxtNomor = findViewById(R.id.txtNomor);
        mTxtHargaBayar = findViewById(R.id.txtHargaBayar);
        mBtnBayar = findViewById(R.id.btnBayar);
        mImageBtnBack = findViewById(R.id.imageBtnBack);
    }

    private void initData() {
        mTxtHargaPulsa.setText("Rp " + data.getHargaPulsa());
        mTxtOperator.setText(data.getOperator());
        mTxtNomor.setText(data.getHargaPulsa());
        mTxtHargaBayar.setText("Rp " + data.getHargaBayar());
    }

    private void onClick() {

        mImageBtnBack.setOnClickListener(v -> {
            finish();
        });

        mBtnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckOutPulsaActivity.this, PembayaranPulsaActivity.class);
                intent.putExtra(INTENT_PARCELABLE, data);
                startActivity(intent);
            }
        });
    }
}
