package com.indocyber.itsmeandroid.view.requestincreaselimit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;

import org.apache.commons.text.WordUtils;

/**
 *
 * @author Muhammad Faisal
 * @version 1.0
 */
public class RequestIncreaseLimitActivity extends AppCompatActivity {

    private EditText mNominalRequestInput;
    private String mNumber;
    private String mHolderName;
    private String mExpiryDate;
    private Spinner mRequestLimitSpinner;
    private Spinner mRequestForSpinner;
    private ImageView cardImage;

    private static final String[] REQUEST_LIMIT_OPTIONS = { "Select Limit", "Tetap", "Sementara" };
    private static final String[] REQUEST_FOR_OPTIONS = { "Select For", "Berobat", "Nikah",
            "Sekolah", "Liburan" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_increase_limit);
        Bundle extras = getIntent().getExtras();
        mNumber = extras.getString("cardNumber");
        mHolderName = extras.getString("holderName");
        mExpiryDate = extras.getString("expiryDate");

        cardImage = findViewById(R.id.imgCreditCard);
        cardImage.setImageResource(extras.getInt("cardImageResource"));

        createToolbar();

        EditText mCurrentLimit = findViewById(R.id.txtCurrentLimit);
        mCurrentLimit.setEnabled(false);
        mNominalRequestInput = findViewById(R.id.txtNominalRequest);
        mNominalRequestInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        mNominalRequestInput.addTextChangedListener(new TextWatcher() {
            private String current = "";
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if(!charSequence.toString().equals(current)){
                    String cleanString = charSequence.toString().replaceAll("\\D", "");
                    String formattedValue;
                    if(cleanString.length() > 9){
                        formattedValue = UtilitiesCore.formatCurrency(999999999);
                    } else if(cleanString.length() < 1){
                        formattedValue = UtilitiesCore.formatCurrency(0);
                    }
                    else {
                        formattedValue = UtilitiesCore.formatCurrency(Integer.valueOf(cleanString));
                    }

                    current = formattedValue;

                    mNominalRequestInput.setText(formattedValue.replace(".",","));
                    mNominalRequestInput.setSelection(mNominalRequestInput.getText().length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Button mSubmitButton = findViewById(R.id.btnSubmit);
        mSubmitButton.setOnClickListener(view -> submit());

        mRequestLimitSpinner = findViewById(R.id.spnRequestLimit);
        mRequestLimitSpinner.setAdapter(new ArrayAdapter<>(this,
                R.layout.spinner_item_text, REQUEST_LIMIT_OPTIONS));
        mRequestForSpinner = findViewById(R.id.spnRequestFor);
        mRequestForSpinner.setAdapter(new ArrayAdapter<>(this,
                R.layout.spinner_item_text, REQUEST_FOR_OPTIONS));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        formatCreditCard();
    }

    private  void createToolbar() {
//        if (getSupportActionBar() != null) {
//            View view = LayoutInflater.from(this).inflate(R.layout.action_bar, null);
//            TextView title = view.findViewById(R.id.titleText);
//            ImageView actionBack = view.findViewById(R.id.imgback);
//            actionBack.setOnClickListener(view1 -> finish());
//            title.setText("Tambah Kartu Kredit");
//            getSupportActionBar().setCustomView(view);
//            getSupportActionBar().setElevation(0f);
//        }
        AppBarLayout appbar = findViewById(R.id.actionBar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView textView = findViewById(R.id.toolbar_text);
        VectorDrawable backButton = (VectorDrawable) getDrawable(R.drawable.ic_ico_arrow_back);
        int iconDimension = (int) getResources().getDimension(R.dimen._20sdp);
        Drawable resizedBackButton =
                UtilitiesCore.resizeDrawable(backButton, this, iconDimension , iconDimension);
        textView.setText("Tambah Limit");
        textView.setAllCaps(false);
        toolbar.setNavigationIcon(resizedBackButton);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0f);
    }

    private void formatCreditCard(){
//        ImageView creditCard = findViewById(R.id.imgCreditCard);
        int[] position = {0, 0};
        cardImage.getLocationOnScreen(position);

        int paddingLeft = (cardImage.getWidth() * 8 / 100);
        int startYAxis = (cardImage.getHeight() / 2);

        TextView mCardNumber = findViewById(R.id.lblCcNumber);
        mCardNumber.setText(padCardNumber(mNumber, 3));
        mCardNumber.setX(paddingLeft);
        mCardNumber.setY(startYAxis + getResources().getDimension(R.dimen.spacing_medium));
        mCardNumber.bringToFront();
        TextView mCardHolder = findViewById(R.id.lblCardHolder);
        mCardHolder.setText(mHolderName);
        mCardHolder.setX(paddingLeft);
        mCardHolder.setY(cardImage.getHeight() * 80 / 100);

        TextView mCardExpiry = findViewById(R.id.lblExpiry);
        mCardExpiry.setX(cardImage.getWidth() / 2);
        mCardExpiry.setY(cardImage.getHeight() * 70 / 100);
    }

    private void submit() {
        CheckBox termsAgreement = findViewById(R.id.chkTermsAgreement);
        if(mRequestForSpinner.getSelectedItemPosition() == 0 ||
                mRequestLimitSpinner.getSelectedItemPosition() == 0
                || mNominalRequestInput.getText().length() < 1){
            UtilitiesCore.buildAlertDialog(
                    this,
                    getString(R.string.form_incomplete_warning),
                    R.drawable.ic_invalid,
                    null
            );
            return;
        }

        if (!termsAgreement.isChecked()){
            UtilitiesCore.buildAlertDialog(
                    this,
                    getString(R.string.agreement_warning),
                    R.drawable.ic_invalid,
                    null
            );
            return;
        }

        String styledText = "Request Menaikan Limit Credit Card Anda<br>"
                        + "<big><b>" + padCardNumber(mNumber, 1) + "</b></big><br>"
                        + " Berhasil";
        UtilitiesCore.buildAlertDialog(
                this,
                Html.fromHtml(styledText),
                R.drawable.ic_approved,
                dialogInterface -> finish()
        );
    }

    private String padCardNumber(String number, int pad) {
        StringBuilder padding = new StringBuilder();
        for(int i = 0; i < pad; i++){
            padding.append(" ");
        }

        String paddedText = number + "";
        return paddedText.substring(0, 4) + padding + paddedText.substring(4, 8) + padding
                + paddedText.substring(8, 12) + padding + paddedText.substring(12, 16);
    }
}
