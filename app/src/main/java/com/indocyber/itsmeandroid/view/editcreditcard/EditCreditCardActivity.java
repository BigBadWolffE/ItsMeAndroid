package com.indocyber.itsmeandroid.view.editcreditcard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.indocyber.itsmeandroid.R;

import org.apache.commons.text.WordUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class EditCreditCardActivity extends AppCompatActivity {

    private TextView txtNumberCard;
    private TextView txtNameCard;
    private TextView txtExpireCard;

    private EditText edtxCardName;
    private EditText edtxValidCard;
    private EditText edtxDate;
    private Spinner mSpnrCountry;
    private Spinner mSpnrCity;
    private Button mBtnSave;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_credit_card);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Edit Credit Card");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setElevation(0f);
        }

        alertDialog = new SpotsDialog.Builder()
                .setCancelable(false)
                .setContext(EditCreditCardActivity.this)
                .build();

        edtxValidCard = findViewById(R.id.edtxValidCard);
        edtxDate = findViewById(R.id.edtxDate);
        edtxCardName = findViewById(R.id.edtxCardName);

        txtNumberCard = findViewById(R.id.txtNumberCard);
        txtNameCard = findViewById(R.id.txtNameCard);
        txtExpireCard = findViewById(R.id.txtExpireCard);

        mSpnrCountry = findViewById(R.id.spnrCountry);
        mSpnrCity = findViewById(R.id.spnrCity);
        mBtnSave = findViewById(R.id.btnSave);

        setSpinnerCountry();
        setSpinnerCity();

        mBtnSave.setOnClickListener(v -> {
            alertDialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    alertDialog.dismiss();
                    finish();
                }
            }, 2000);
        });

        edtxDate.setFocusable(false);
        edtxDate.setOnClickListener(v -> {
            showDialogCalendar();
        });

        edtxCardName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtNameCard.setText(WordUtils.capitalizeFully(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtxValidCard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //onCardNumberChange(s);
                txtNumberCard.setText(s+"");
                Toast.makeText(EditCreditCardActivity.this, s+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setSpinnerCountry() {
        try {
            List<HashMap<String, String>> listSpinner = new ArrayList<HashMap<String, String>>();

            String[] idSpinner = {"1", "2"};
            String[] nameSpinner = {"Select country", "Indonesia"};


            for (int i = 0; i < idSpinner.length; i++) {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("id", idSpinner[i]);
                hm.put("level_name", nameSpinner[i]);
                listSpinner.add(hm);
            }
            String[] from = {"id", "level_name"};
            int[] to = {R.id.id_spinner, R.id.nama_spinner};
            SimpleAdapter adapter = new SimpleAdapter(this, listSpinner, R.layout.layout_spinner, from, to);
            mSpnrCountry.setAdapter(adapter);
            mSpnrCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void setSpinnerCity() {
        try {
            List<HashMap<String, String>> listSpinner = new ArrayList<HashMap<String, String>>();

            String[] idSpinner = {"1", "2"};
            String[] nameSpinner = {"Select country", "DKI Jakarta"};


            for (int i = 0; i < idSpinner.length; i++) {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("id", idSpinner[i]);
                hm.put("level_name", nameSpinner[i]);
                listSpinner.add(hm);
            }
            String[] from = {"id", "level_name"};
            int[] to = {R.id.id_spinner, R.id.nama_spinner};
            SimpleAdapter adapter = new SimpleAdapter(this, listSpinner, R.layout.layout_spinner, from, to);
            mSpnrCity.setAdapter(adapter);
            mSpnrCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void showDialogCalendar() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {

                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
                    c.set(mYear, mMonth, mDay);
                    String update = sdf.format(c.getTime());

                    edtxDate.setText(update);
                    txtExpireCard.setText(update);
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void onCardNumberChange(final CharSequence text){
        String paddedText = text + "";
        for(int i = paddedText.length(); i < 20; i++){
            paddedText += "X";
        }

        String updatedText = paddedText.substring(0, 4) + "   " + paddedText.substring(4, 8) + "   "
                + paddedText.substring(8, 12) + "   " + paddedText.substring(12, 16);

        txtNumberCard.setText(updatedText);
    }
}
