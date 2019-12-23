package com.indocyber.itsmeandroid.view.addmembership;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.indocyber.itsmeandroid.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class AddMembershipActivity extends AppCompatActivity {
    private Spinner mSpnrMerchant;
    private Spinner mSpnrMember;
    private EditText mEdtxFullname;
    private EditText mEdtxDateTime;
    private TextView txtNameCard;
    private TextView txtExpireCard;
    private Button mBtnSave;
    private AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_membership);

        mSpnrMerchant = findViewById(R.id.spnrMerchant);
        mSpnrMember = findViewById(R.id.spnrMember);
        mEdtxFullname = findViewById(R.id.edtxFullname);
        mEdtxDateTime = findViewById(R.id.edtxDateTime);
        txtNameCard = findViewById(R.id.txtNameCard);
        txtExpireCard = findViewById(R.id.txtExpireCard);
        mBtnSave = findViewById(R.id.btnSave);

        alertDialog = new SpotsDialog.Builder().setCancelable(false).setContext(AddMembershipActivity.this).build();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Add Membership");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setElevation(0f);
        }
        mBtnSave.setOnClickListener(v -> {
            alertDialog.show();
            new Handler().postDelayed(() -> {
                alertDialog.dismiss();
                showCustomDialog();
            }, 2000);
        });

        mEdtxDateTime.setOnClickListener(v -> {
            showDialogCalendar();
        });
        mEdtxFullname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                new Handler().postDelayed(() -> {
                    if (txtNameCard.getText().length() >= 25){
                        txtNameCard.setTextSize(10);
                    }else {
                        txtNameCard.setTextSize(16);
                    }
                    txtNameCard.setText(s);
                },300);
                //Toast.makeText(AddMembershipActivity.this, txtNameCard.getLineCount()+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        setSpinnerMember();
        setSpinnerMerchant();
    }

    private void showDialogCalendar() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {

                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    c.set(mYear, mMonth, mDay);
                    String update = sdf.format(c.getTime());

                    mEdtxDateTime.setText(update);
                    txtExpireCard.setText(update);
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setSpinnerMember() {
        try {
            List<HashMap<String, String>> listSpinner = new ArrayList<HashMap<String, String>>();

            String[] idSpinner = {"1", "2"};
            String[] nameSpinner = {"Select Member", "Platinum"};


            for (int i = 0; i < idSpinner.length; i++) {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("id", idSpinner[i]);
                hm.put("level_name", nameSpinner[i]);
                listSpinner.add(hm);
            }
            String[] from = {"id", "level_name"};
            int[] to = {R.id.id_spinner, R.id.nama_spinner};
            SimpleAdapter adapter = new SimpleAdapter(this, listSpinner, R.layout.layout_spinner, from, to);
            mSpnrMember.setAdapter(adapter);
            mSpnrMember.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long idLong) {
                    HashMap<String, String> hm = (HashMap<String, String>) parent.getAdapter().getItem(position);
                    String id = hm.get("id");
                    String level_name = hm.get("level_name");
                   /* typeId = id;
                    typeName = level_name;*/
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSpinnerMerchant() {
        try {
            List<HashMap<String, String>> listSpinner = new ArrayList<HashMap<String, String>>();

            String[] idSpinner = {"1", "2"};
            String[] nameSpinner = {"Select Merchant", "Alacarte"};


            for (int i = 0; i < idSpinner.length; i++) {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("id", idSpinner[i]);
                hm.put("level_name", nameSpinner[i]);
                listSpinner.add(hm);
            }
            String[] from = {"id", "level_name"};
            int[] to = {R.id.id_spinner, R.id.nama_spinner};
            SimpleAdapter adapter = new SimpleAdapter(this, listSpinner, R.layout.layout_spinner, from, to);
            mSpnrMerchant.setAdapter(adapter);
            mSpnrMerchant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long idLong) {
                    HashMap<String, String> hm = (HashMap<String, String>) parent.getAdapter().getItem(position);
                    String id = hm.get("id");
                    String level_name = hm.get("level_name");
                   /* typeId = id;
                    typeName = level_name;*/
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showCustomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_succes_add_membership);
        dialog.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        final Button btnClose = (Button) dialog.findViewById(R.id.btnClose);


        /*final TextView txtNumberCard = (TextView) dialog.findViewById(R.id.txtNumberCard);
        txtNumberCard.setText(model.getNumberCard());*/


        btnClose.setOnClickListener(v -> {
            dialog.dismiss();
            finish();
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}
