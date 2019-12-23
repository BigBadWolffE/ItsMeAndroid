package com.indocyber.itsmeandroid.view.requestincreaselimit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;

import org.apache.commons.text.WordUtils;

/**
 *
 * @author Muhammad Faisal
 * @version 1.0
 */
public class RequestIncreaseLimitActivity extends AppCompatActivity {

    private TextView mCardNumber;
    private TextView mCardHolder;
    private TextView mCardExpiry;
    private EditText mCurrentLimit;
    private EditText mNominalRequestInput;
    private Button mSubmitButton;
    private String mNumber;
    private String mHolderName;
    private String mExpiryDate;
    private Spinner mRequestLimitSpinner;
    private Spinner mRequestForSpinner;

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

        createToolbar();

        mCurrentLimit = findViewById(R.id.txtCurrentLimit);
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

        mSubmitButton = findViewById(R.id.btnSubmit);
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
        formatCreditCard(mNumber, mHolderName, mExpiryDate);
    }

    private  void createToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Request Increase Limit");
            getSupportActionBar().setElevation(0f);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void formatCreditCard(String cardNumber, String holderName, String expiryDate){
        ImageView creditCard = findViewById(R.id.imgCreditCard);
        int[] position = {0, 0};
        creditCard.getLocationOnScreen(position);

        int paddingLeft = (creditCard.getWidth() * 8 / 100);
        int startYAxis = (creditCard.getHeight() / 2);

        mCardNumber = findViewById(R.id.lblCcNumber);
        mCardNumber.setText(padCardNumber(cardNumber, 3));
        mCardNumber.setX(position[0] + paddingLeft);
        mCardNumber.setY(startYAxis + getResources().getDimension(R.dimen.spacing_medium));
        mCardNumber.bringToFront();

        TextView holderLabel = findViewById(R.id.lblHolderLabel);
        holderLabel.setX(position[0] + paddingLeft);
        holderLabel.setY(mCardNumber.getY() + mCardNumber.getHeight()
                + getResources().getDimension(R.dimen.spacing_large));

        TextView expiryLabel = findViewById(R.id.lblExpiryLabel);
        expiryLabel.setX(position[0] + paddingLeft
                + mCardNumber.getWidth() - expiryLabel.getWidth());
        expiryLabel.setY(mCardNumber.getY() + mCardNumber.getHeight()
                + getResources().getDimension(R.dimen.spacing_large));

        mCardHolder = findViewById(R.id.lblCardHolder);
        mCardHolder.setText(WordUtils.capitalizeFully(holderName));
        mCardHolder.setX(position[0] + paddingLeft);
        mCardHolder.setY(holderLabel.getY() + holderLabel.getHeight()
                + getResources().getDimension(R.dimen.spacing_xsmall));

        mCardExpiry = findViewById(R.id.lblExpiry);
        mCardExpiry.setText(expiryDate);
        mCardExpiry.setX(expiryLabel.getX());
        mCardExpiry.setY(expiryLabel.getY() + expiryLabel.getHeight() +
                getResources().getDimension(R.dimen.spacing_xsmall));
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
                dialogInterface -> finish(),
                310,
                320
        );
    }

    private String padCardNumber(String number, int pad) {
        String padding = "";
        for(int i = 0; i < pad; i++){
            padding += " ";
        }

        String paddedText = number + "";
        return paddedText.substring(0, 4) + padding + paddedText.substring(4, 8) + padding
                + paddedText.substring(8, 12) + padding + paddedText.substring(12, 16);
    }
}
