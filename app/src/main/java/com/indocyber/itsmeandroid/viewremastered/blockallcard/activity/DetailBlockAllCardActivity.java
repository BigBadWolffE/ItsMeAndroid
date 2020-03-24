package com.indocyber.itsmeandroid.viewremastered.blockallcard.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.BlockAllCardModel;
import com.indocyber.itsmeandroid.model.ListBlockAllCard;
import com.indocyber.itsmeandroid.viewremastered.blockallcard.adapter.BlockAllCardListAdapter;
import com.indocyber.itsmeandroid.viewremastered.blockallcard.adapter.DetailCardListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.INTENT_PARCELABLE;

public class DetailBlockAllCardActivity extends AppCompatActivity {

    private ImageButton imageBtnBack;
    private RecyclerView recycBlockAllCard;
    private EditText edtxInfoKartu;
    private CheckBox checkAgree;
    private Button btnBlokirKartu;
    private DetailCardListAdapter adapter;
    private ListBlockAllCard listBlockAllCard;
    private boolean isTrue;
    private TextView txtAgree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_block_all_card);

        listBlockAllCard = getIntent().getExtras().getParcelable(INTENT_PARCELABLE);

        initView();
        onClick();
        initData();
        edtxInfoKartu.requestFocus();
    }

    private void initData() {
        if (listBlockAllCard != null) {
            adapter = new DetailCardListAdapter(listBlockAllCard.getList(), this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

            recycBlockAllCard.setLayoutManager(linearLayoutManager);
            recycBlockAllCard.setItemAnimator(new DefaultItemAnimator());
            recycBlockAllCard.setNestedScrollingEnabled(false);
            recycBlockAllCard.setAdapter(adapter);
        }
    }

    public void initView() {
        imageBtnBack = findViewById(R.id.imageBtnBack);
        recycBlockAllCard = findViewById(R.id.recycBlockAllCard);
        edtxInfoKartu = findViewById(R.id.edtxInfoKartu);
        checkAgree = findViewById(R.id.checkAgree);
        btnBlokirKartu = findViewById(R.id.btnBlokirKartu);
        txtAgree = findViewById(R.id.txtAgree);

        txtAgree.setText(Html.fromHtml("<p align=right> <b> "
                + "<font color= \"#a2a2a2\" size=6>"
                + "I Agree " + "</font>"
                + "<font color= \"#474747\" size=6>"
                + " Terms " + "</font>" +
                "<font color= \"#a2a2a2\" size=6>"
                + " and " + "</font>" +
                "<font color= \"#474747\" size=6>"
                + " Condition " + "</font>"
                ));
    }

    private void onClick() {
        imageBtnBack.setOnClickListener(v -> {
            finish();
        });

        checkAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                isTrue = isChecked;

            }
        });
        btnBlokirKartu.setOnClickListener(v -> {
            if (edtxInfoKartu.getText().toString().isEmpty()) {
                edtxInfoKartu.requestFocus();
                edtxInfoKartu.setError("field empty");
            } else if (!isTrue) {
                Toast.makeText(DetailBlockAllCardActivity.this, "Please Check Agreement", Toast.LENGTH_SHORT).show();

            } else {
                Intent intent = new Intent(DetailBlockAllCardActivity.this, SetPinBlockCardActivity.class);
                startActivity(intent);
            }

        });
    }
}
