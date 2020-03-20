package com.indocyber.itsmeandroid.viewremastered.belipulsa.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.Pulsa;
import com.indocyber.itsmeandroid.viewremastered.belipulsa.adapter.BeliPulsaAdapter;


import java.util.ArrayList;
import java.util.List;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.INTENT_PARCELABLE;

public class BeliPulsaActivity extends AppCompatActivity {

    private ImageButton imageBtnBack;
    private ImageButton imgBtnClose;
    private ImageView imgOperator;
    private ImageButton imgBtnContact;
    private EditText edtxPhoneNumber;
    private RecyclerView recyclePulsa;
    private BeliPulsaAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beli_pulsa);
        initView();
        setData();
        onClick();
    }

    private void initView() {
        imageBtnBack = findViewById(R.id.imageBtnBack);
        imgBtnClose = findViewById(R.id.imgBtnClose);
        edtxPhoneNumber = findViewById(R.id.edtxPhoneNumber);
        recyclePulsa = findViewById(R.id.recyclePulsa);
        imgOperator = findViewById(R.id.imgOperator);
        imgBtnContact = findViewById(R.id.imgBtnContact);


        adapter = new BeliPulsaAdapter(this);

    }

    private void onClick(){
        imageBtnBack.setOnClickListener( v-> {

        });
        imgBtnClose.setOnClickListener(v -> {
            finish();
        });

        imgBtnContact.setOnClickListener(v ->{

        });
        edtxPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0){
                    imgBtnClose.setVisibility(View.VISIBLE);
                    imgOperator.setVisibility(View.VISIBLE);
                }else {
                    imgBtnClose.setVisibility(View.VISIBLE);
                    imgOperator.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        adapter.SetItemOnclickListener(new BeliPulsaAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position, Pulsa pulsa) {
                Intent intent = new Intent(BeliPulsaActivity.this,CheckOutPulsaActivity.class);
                intent.putExtra(INTENT_PARCELABLE,pulsa);
                startActivity(intent);
            }
        });
    }

    private void setData() {
        adapter.setList(generateCardList());
        recyclePulsa.setLayoutManager(new GridLayoutManager(this, 2));
        recyclePulsa.setAdapter(adapter);
        recyclePulsa.setHasFixedSize(true);

    }

    private List<Pulsa> generateCardList() {
        List<Pulsa> pulsa = new ArrayList<>();
        pulsa.add(new Pulsa("15.000", "30 hari", "15.300","Telkomsel"));
        pulsa.add(new Pulsa("25.000", "30 hari", "25.300","Telkomsel"));
        pulsa.add(new Pulsa("30.000", "30 hari", "30.100","Telkomsel"));
        pulsa.add(new Pulsa("40.000", "30 hari", "40.100","Telkomsel"));
        pulsa.add(new Pulsa("50.000", "30 hari", "50.000","Telkomsel"));
        pulsa.add(new Pulsa("75.000", "30 hari", "75.000","Telkomsel"));
        pulsa.add(new Pulsa("100.000", "60 hari", "100.000","Telkomsel"));
        pulsa.add(new Pulsa("150.000", "120 hari", "150.000","Telkomsel"));
        pulsa.add(new Pulsa("200.000", "120 hari", "200.000","Telkomsel"));
        pulsa.add(new Pulsa("300.000", "120 hari", "300.000","Telkomsel"));
        return pulsa;
    }
}
