package com.indocyber.itsmeandroid.view.editcreditcard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;
import com.indocyber.itsmeandroid.viewmodel.EditCardViewModel;

import org.apache.commons.text.WordUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import dmax.dialog.SpotsDialog;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.INTENT_ID;

public class EditCreditCardActivity extends AppCompatActivity {

    private static final String[] idCountries = {"0", "1"};
    private static final String[] idCities = {"0", "1", "2",
            "3", "4", "5", "6"};
    private static final String[] countries = {"Select Country", "Indonesia"};
    private static final String[] cities = {"Select City", "Jakarta Barat", "Jakarta Selatan",
            "Jakarta Timur", "Jakarta Utara", "Tanggerang Kota", "Tanggerang Selatan"};
    private TextView txtNumberCard;
    private TextView txtNameCard;
    private TextView txtExpireCard;

    private EditText edtxCardName;
    private EditText edtxValidCard;
    private EditText edtxDate;
    private EditText edtxBillingAddress;
    private EditText edtxPostalCode;
    private Spinner mSpnrCountry;
    private Spinner mSpnrCity;
    private AlertDialog alertDialog;
    private EditCardViewModel viewModel;
    private ImageCardModel modelEdit;
    private CheckBox checkboxRegister;


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
        edtxBillingAddress = findViewById(R.id.edtxBillingAddress);
        edtxDate = findViewById(R.id.edtxDate);
        edtxPostalCode = findViewById(R.id.edtxPostalCode);

        checkboxRegister = findViewById(R.id.checkboxRegister);

        txtNumberCard = findViewById(R.id.txtNumberCard);
        txtNameCard = findViewById(R.id.txtNameCard);
        txtExpireCard = findViewById(R.id.txtExpireCard);

        mSpnrCountry = findViewById(R.id.spnrCountry);
        mSpnrCity = findViewById(R.id.spnrCity);
        Button mBtnSave = findViewById(R.id.btnSave);

        Bundle extras = getIntent().getExtras();
        int intent_id = 0;
        if (extras != null)  intent_id = extras.getInt(INTENT_ID);

        viewModel = ViewModelProviders.of(this).get(EditCardViewModel.class);

        mBtnSave.setOnClickListener(v -> {

          /*  new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    alertDialog.dismiss();
                    finish();
                }
            }, 2000);*/
            saveData();
        });

        edtxDate.setFocusable(false);
        edtxDate.setOnClickListener(v -> showDialogCalendar());

        viewModel.getEditCard(intent_id);
        viewModel.getData().observe(this, data -> {

            if (data != null) {

                edtxValidCard.setText((data.getNumberCard()));
                edtxDate.setText(data.getPrintDate());
                edtxCardName.setText(data.getNameCard());
                edtxBillingAddress.setText(data.getBillingAddress());
                edtxDate.setText(data.getExpireCard());
                edtxPostalCode.setText(data.getPostalCode());

                String number = data.getNumberCard().replace(" ","");
                //txtNumberCard.setText(data.getNumberCard());
                onCardNumberChange(number);
                txtNameCard.setText(data.getNameCard());
                txtExpireCard.setText(data.getExpireCard());

                setSpinnerCountry(data);
                setSpinnerCity(data);

                modelEdit = data;
            }
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
                onCardNumberChange(s);
                //txtNumberCard.setText(s + "");
                //Toast.makeText(EditCreditCardActivity.this, s + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        formatCreditCard();
    }

    private void setSpinnerCountry(ImageCardModel model) {
        try {
            List<HashMap<String, String>> listSpinner = new ArrayList<>();

//            String[] idSpinner = {"1", "2"};
//            String[] nameSpinner = {"Select country", "Indonesia"};


            for (int i = 0; i < idCountries.length; i++) {
                HashMap<String, String> hm = new HashMap<>();
                hm.put("id", idCountries[i]);
                hm.put("level_name", countries[i]);
                listSpinner.add(hm);
            }

            String[] from = {"id", "level_name"};
            int[] to = {R.id.id_spinner, R.id.nama_spinner};
            SimpleAdapter adapter = new SimpleAdapter(this, listSpinner, R.layout.layout_spinner, from, to);
            mSpnrCountry.setAdapter(adapter);

            mSpnrCountry.setSelection(1);

            mSpnrCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long idLong) {
//                    HashMap<String, String> hm = (HashMap<String, String>) parent.getAdapter().getItem(position);
//                    String id = hm.get("id");
//                    String level_name = hm.get("level_name");



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

    private void formatCreditCard(){
        ImageView creditCard = findViewById(R.id.imgCreditCard);
        int[] position = {0, 0};
        creditCard.getLocationOnScreen(position);

        int paddingLeft = (creditCard.getWidth() * 8 / 100);
        int startYAxis = (creditCard.getHeight() / 2);

        txtNumberCard.setX(position[0] + paddingLeft);
        txtNumberCard.setY(startYAxis + getResources().getDimension(R.dimen.spacing_medium));
        txtNumberCard.bringToFront();

        TextView holderLabel = findViewById(R.id.lblHolderLabel);
        holderLabel.setX(position[0] + paddingLeft);
        holderLabel.setY(txtNumberCard.getY() + txtNumberCard.getHeight()
                + getResources().getDimension(R.dimen.spacing_large));

        TextView expiryLabel = findViewById(R.id.lblExpiryLabel);
        expiryLabel.setX(position[0] + paddingLeft
                + txtNumberCard.getWidth() - expiryLabel.getWidth());
        expiryLabel.setY(txtNumberCard.getY() + txtNumberCard.getHeight()
                + getResources().getDimension(R.dimen.spacing_large));

        txtNameCard.setX(position[0] + paddingLeft);
        txtNameCard.setY(holderLabel.getY() + holderLabel.getHeight()
                + getResources().getDimension(R.dimen.spacing_xsmall));

        txtExpireCard.setX(expiryLabel.getX());
        txtExpireCard.setY(expiryLabel.getY() + expiryLabel.getHeight() +
                getResources().getDimension(R.dimen.spacing_xsmall));
    }

    private void setSpinnerCity(ImageCardModel model) {
        try {
            List<HashMap<String, String>> listSpinner = new ArrayList<>();

//            String[] idSpinner = {"1", "2"};
//            String[] nameSpinner = {"Select country", "DKI Jakarta"};


            for (int i = 0; i < idCities.length; i++) {
                HashMap<String, String> hm = new HashMap<>();
                hm.put("id", idCities[i]);
                hm.put("level_name", cities[i]);
                listSpinner.add(hm);
            }
            String[] from = {"id", "level_name"};
            int[] to = {R.id.id_spinner, R.id.nama_spinner};
            SimpleAdapter adapter = new SimpleAdapter(this, listSpinner, R.layout.layout_spinner, from, to);
            mSpnrCity.setAdapter(adapter);

            mSpnrCity.setSelection(1);

            mSpnrCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long idLong) {
//                    HashMap<String, String> hm = (HashMap<String, String>) parent.getAdapter().getItem(position);
//                    String id = hm.get("id");
//                    String level_name = hm.get("level_name");
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

                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");
                    c.set(mYear, mMonth, mDay);
                    String update = sdf.format(c.getTime());

                    edtxDate.setText(update);
                    txtExpireCard.setText(update);
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void saveData() {
        if (!formIsValid()) {
            UtilitiesCore.buildAlertDialog(
                    this,
                    getString(R.string.form_incomplete_warning),
                    R.drawable.ic_invalid,
                    null
            );
        } else if (!checkboxRegister.isChecked()) {
            UtilitiesCore.buildAlertDialog(
                    this,
                    "Please read and indicate your acceptance of the site's Terms of Service",
                    R.drawable.ic_invalid,
                    null
            );
        } else {
            ImageCardModel model = new ImageCardModel(
                    modelEdit.getId(),
                    modelEdit.getImage(),
                    txtNumberCard.getText().toString(),
                    edtxCardName.getText().toString(),
                    edtxDate.getText().toString(),
                    modelEdit.getCost(),
                    modelEdit.getPrintDate(),
                    modelEdit.getPrintDueDate(),
                    modelEdit.isBlockedCard()
            );
            model.setBillingAddress(model.getBillingAddress());
            model.setCountry(String.valueOf(mSpnrCountry.getSelectedItemPosition()));
            model.setCity(String.valueOf(mSpnrCity.getSelectedItemPosition()));
            model.setPostalCode(edtxPostalCode.getText().toString());
            model.setBillingAddress(edtxBillingAddress.getText().toString());
            model.setMinPayment(model.getMinPayment());
            model.setAvailableCredit(modelEdit.getAvailableCredit());

            viewModel.setIsSaved(model);
            viewModel.getError().observe(this, e -> {
                alertDialog.dismiss();
                Toast.makeText(this, e + "", Toast.LENGTH_SHORT).show();
            });
            viewModel.getIsLoading().observe(this, l -> {
                if (l) {
                    alertDialog.show();
                }

            });
            viewModel.getIsSaved().observe(this, save -> {
                if (save) {
                    alertDialog.dismiss();
                    String styledText = "Penambahan Credit Card Anda<br>"
                            + "<big><b>" + edtxValidCard.getText().toString() + "</b></big><br>"
                            + "Berhasil";
                    UtilitiesCore.buildAlertDialog(
                            this,
                            Html.fromHtml(styledText),
                            R.drawable.icocheckapproved,
                            dialog -> returnToHome()
                    );
                }
            });

        }
    }

    private void returnToHome() {
        finish();
    }

    private boolean formIsValid() {
        if (edtxValidCard.getText().length() < 16) {
            return false;
        }

        if (edtxDate.getText().length() < 5) {
            return false;
        }


        if (edtxBillingAddress.getText().length() < 0) {
            return false;
        }

        if (mSpnrCity.getSelectedItemPosition() == 0) {
            return false;
        }

        if (mSpnrCountry.getSelectedItemPosition() == 0) {
            return false;
        }

        if (edtxPostalCode.getText().length() < 3) {
            return false;
        }

        return true;
    }

    private void onCardNumberChange(final CharSequence text) {
        StringBuilder paddedText = new StringBuilder(text + "");
        for (int i = paddedText.length(); i < 20; i++) {
            paddedText.append("X");
        }

        String updatedText = paddedText.substring(0, 4) + "   " + paddedText.substring(4, 8) + "   "
                + paddedText.substring(8, 12) + "   " + paddedText.substring(12, 16);

        txtNumberCard.setText(updatedText);
    }

    /*private void onCardNumberSetText(final CharSequence text) {
        String paddedText = text + "";
        for (int i = paddedText.length(); i < 20; i++) {
            paddedText += "X";
        }

        String updatedText = paddedText.substring(0, 4) + "   " + paddedText.substring(4, 8) + "   "
                + paddedText.substring(8, 12) + "   " + paddedText.substring(12, 16);

        txtNumberCard.setText(updatedText);
    }*/
}
