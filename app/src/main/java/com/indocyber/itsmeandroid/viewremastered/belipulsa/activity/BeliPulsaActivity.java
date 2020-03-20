package com.indocyber.itsmeandroid.viewremastered.belipulsa.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

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
    private int PICK_CONTACT_REQUEST = 1;


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

    private void onClick() {
        imageBtnBack.setOnClickListener(v -> {
            finish();
        });
        imgBtnClose.setOnClickListener(v -> {
            edtxPhoneNumber.setText("");
            imgBtnClose.setVisibility(View.GONE);
            imgOperator.setVisibility(View.GONE);
        });

        imgBtnContact.setOnClickListener(v -> {
            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(contactPickerIntent, PICK_CONTACT_REQUEST);
        });
        edtxPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    imgBtnClose.setVisibility(View.VISIBLE);
                    imgOperator.setVisibility(View.VISIBLE);
                } else {
                    imgBtnClose.setVisibility(View.GONE);
                    imgOperator.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        adapter.SetItemOnclickListener(new BeliPulsaAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position, Pulsa pulsa) {
                if (edtxPhoneNumber.getText().toString().isEmpty()) {
                    Toast.makeText(BeliPulsaActivity.this, "Please input phone number", Toast.LENGTH_SHORT).show();
                } else {
                    pulsa.setNomorHp(edtxPhoneNumber.getText().toString());
                    Intent intent = new Intent(BeliPulsaActivity.this, CheckOutPulsaActivity.class);
                    intent.putExtra(INTENT_PARCELABLE, pulsa);
                    startActivity(intent);
                }
            }
        });
    }

    @SuppressLint("Recycle")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_CONTACT_REQUEST) {
                Cursor cursor = null;
                try {
                    String phoneNo = null;
                    String name = null;

                    Uri uri = data != null ? data.getData() : null;
                    if (uri != null) {
                        cursor = getContentResolver().query(uri, null, null, null, null);
                    }
                    if (cursor != null) {
                        cursor.moveToFirst();
                    }
                    int phoneIndex = 0;
                    if (cursor != null) {
                        phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    }
                    int nameIndex = 0;
                    if (cursor != null) {
                        nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    }
                    if (cursor != null) {
                        phoneNo = cursor.getString(phoneIndex);
                    }
                    if (cursor != null) {
                        name = cursor.getString(nameIndex);
                    }

                    Log.e("Name and Contact ", name + "," + phoneNo);
                    if (phoneNo != null) {
                        edtxPhoneNumber.setText(phoneNo.replace("+62", "0")
                                .replace(" ", "")
                                .replace("-", ""));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            Log.e("Failed", "Not able to pick contact");
        }
    }

    private void setData() {
        adapter.setList(generateCardList());
        recyclePulsa.setLayoutManager(new GridLayoutManager(this, 2));
        recyclePulsa.setAdapter(adapter);
        recyclePulsa.setHasFixedSize(true);

    }

    private List<Pulsa> generateCardList() {
        List<Pulsa> pulsa = new ArrayList<>();
        pulsa.add(new Pulsa("15.000", "30 hari", "15.300", "Telkomsel"));
        pulsa.add(new Pulsa("25.000", "30 hari", "25.300", "Telkomsel"));
        pulsa.add(new Pulsa("30.000", "30 hari", "30.100", "Telkomsel"));
        pulsa.add(new Pulsa("40.000", "30 hari", "40.100", "Telkomsel"));
        pulsa.add(new Pulsa("50.000", "30 hari", "50.000", "Telkomsel"));
        pulsa.add(new Pulsa("75.000", "30 hari", "75.000", "Telkomsel"));
        pulsa.add(new Pulsa("100.000", "60 hari", "100.000", "Telkomsel"));
        pulsa.add(new Pulsa("150.000", "120 hari", "150.000", "Telkomsel"));
        pulsa.add(new Pulsa("200.000", "120 hari", "200.000", "Telkomsel"));
        pulsa.add(new Pulsa("300.000", "120 hari", "300.000", "Telkomsel"));
        return pulsa;
    }
}
