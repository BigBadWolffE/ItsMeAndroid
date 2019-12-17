package com.indocyber.itsmeandroid.view.addcc;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;
import com.indocyber.itsmeandroid.view.otp.OtpActivity;
import com.indocyber.itsmeandroid.view.requestincreaselimit.RequestIncreaseLimitActivity;

import org.apache.commons.text.WordUtils;


/**
 * @author Muhammad Faisal
 * @version 1.0
 */
public final class AddCcActivity extends AppCompatActivity {

    private static final String[] countries = { "Select Country", "Indonesia" };
    private static final String[] cities = { "Select City", "Jakarta Barat", "Jakarta Selatan",
            "Jakarta Timur", "Jakarta Utara", "Tanggerang Kota", "Tanggerang Selatan" };
    private TextView mCardNumber;
    private TextView mCardHolder;
    private TextView mCardExpiry;
    private EditText mCardNumberInput;
    private EditText mCardHolderInput;
    private EditText mCardExpiryInput;
    private EditText mBillingAddressInput;
    private Spinner mCountryInput;
    private Spinner mCityInput;
    private EditText mPostalCodeInput;
    private Button mIncreaseCreditLimitButton;
    private Button mAddCreditCardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cc);
        createToolbar();
        initializeCardInput();
        initializeButton();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        formatCreditCard();
    }

    private  void createToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarText = findViewById(R.id.txtToolbar);
        toolbarText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        toolbarText.setText("Add Credit Card");
        toolbar.setElevation(0);
        Drawable backButton = UtilitiesCore.resizeDrawable(R.drawable.icoarrowback, this, 30, 30);
        toolbar.setNavigationIcon(backButton);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initializeCardInput() {
        mCardNumberInput = findViewById(R.id.txtCardNumber);
        mCardNumberInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        mCardNumberInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int before, int after,
                                          int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int before, int after, int count) {
                onCardNumberChange(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mCardNumber.setVisibility(View.VISIBLE);
            }
        });

        mCardHolderInput = findViewById(R.id.txtCardHolder);
        mCardHolderInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int before, int after,
                                          int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int before, int after,
                                      int count) {
                mCardHolder.setText(WordUtils.capitalizeFully(charSequence.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mCardExpiryInput = findViewById(R.id.txtExpiryDate);
        mCardExpiryInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        mCardExpiryInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int before, int after,
                                          int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int before, int after, int count) {
                String expiryDate = charSequence.toString();
                if (charSequence.length() == 3 && count > 0) {
                    expiryDate = expiryDate.substring(0, 2) + "/" + expiryDate.substring(2,3);
                    mCardExpiryInput.setText(expiryDate);
                    mCardExpiryInput.setSelection(mCardExpiryInput.getText().length());
                }
                mCardExpiry.setText(expiryDate);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mBillingAddressInput = findViewById(R.id.txtBillingAddress);
        mBillingAddressInput.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
        mBillingAddressInput.setMaxLines(5);
        mBillingAddressInput.setSingleLine(false);
        mBillingAddressInput.setVerticalScrollBarEnabled(true);
        mBillingAddressInput.setMovementMethod(ScrollingMovementMethod.getInstance());
        mBillingAddressInput.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);

        mCountryInput = findViewById(R.id.spnCountry);
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item_text, countries);
        mCountryInput.setAdapter(countryAdapter);

        mCityInput = findViewById(R.id.spnCity);
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item_text, cities);
        mCityInput.setAdapter(cityAdapter);

        mPostalCodeInput = findViewById(R.id.txtPostalCode);
    }

    private void initializeButton() {
        mIncreaseCreditLimitButton = findViewById(R.id.btnCreditLimit);
        mIncreaseCreditLimitButton.setOnClickListener(view -> requestIncreaseCreditLimit());

        mAddCreditCardButton = findViewById(R.id.btnAddCc);
        mAddCreditCardButton.setOnClickListener(view -> submit());
    }

    private boolean formIsValid(){
        if(mCardNumberInput.getText().length() < 16){
            return false;
        }

        if(mCardExpiryInput.getText().length() < 5){
            return false;
        }

        if(mCardHolderInput.getText().length() < 1){
            return false;
        }

        if(mBillingAddressInput.getText().length() < 0){
            return false;
        }

        if(mCountryInput.getSelectedItemPosition() == 0){
            return false;
        }

        if(mCityInput.getSelectedItemPosition() == 0){
            return false;
        }

        if(mPostalCodeInput.getText().length() < 3){
            return false;
        }

        return true;
    }

    private void requestIncreaseCreditLimit() {
        if(!formIsValid()){
            UtilitiesCore.buildAlertDialog(
                    this,
                    getString(R.string.form_incomplete_warning),
                    R.drawable.ic_invalid,
                    null
            );
            return;
        }

        Intent intent = new Intent(this, RequestIncreaseLimitActivity.class);
        intent.putExtra("cardNumber", mCardNumberInput.getText().toString());
        intent.putExtra("holderName", mCardHolderInput.getText().toString());
        intent.putExtra("expiryDate", mCardExpiryInput.getText().toString());
        startActivity(intent);
    }

    private void submit() {
        if(!formIsValid()){
            UtilitiesCore.buildAlertDialog(
                    this,
                    getString(R.string.form_incomplete_warning),
                    R.drawable.ic_invalid,
                    null
            );
            return;
        }

        CheckBox termsAgreement = findViewById(R.id.chkTermsAgreement);
        if (!termsAgreement.isChecked()) {
            UtilitiesCore.buildAlertDialog(
                    this,
                    "Please read and indicate your acceptance of the site's Terms of Service",
                    R.drawable.ic_invalid,
                    null
            );
            return;
        }

        Intent intent = new Intent(this, OtpActivity.class);
        intent.putExtra("cardNumber", mCardNumber.getText().toString());
        startActivity(intent);
    }


    private void formatCreditCard(){
        ImageView creditCard = findViewById(R.id.imgCreditCard);
        int[] position = {0, 0};
        creditCard.getLocationOnScreen(position);

        int paddingLeft = (creditCard.getWidth() * 8 / 100);
        int startYAxis = (creditCard.getHeight() / 2);

        mCardNumber = findViewById(R.id.lblCcNumber);
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
        mCardHolder.setX(position[0] + paddingLeft);
        mCardHolder.setY(holderLabel.getY() + holderLabel.getHeight()
                + getResources().getDimension(R.dimen.spacing_xsmall));

        mCardExpiry = findViewById(R.id.lblExpiry);
        mCardExpiry.setX(expiryLabel.getX());
        mCardExpiry.setY(expiryLabel.getY() + expiryLabel.getHeight() +
                getResources().getDimension(R.dimen.spacing_xsmall));
    }

    private void onCardNumberChange(final CharSequence text){
        String paddedText = text + "";
        for(int i = paddedText.length(); i < 16; i++){
            paddedText += "X";
        }

        String updatedText = paddedText.substring(0, 4) + "   " + paddedText.substring(4, 8) + "   "
                + paddedText.substring(8, 12) + "   " + paddedText.substring(12, 16);

        mCardNumber.setText(updatedText);
    }

}

