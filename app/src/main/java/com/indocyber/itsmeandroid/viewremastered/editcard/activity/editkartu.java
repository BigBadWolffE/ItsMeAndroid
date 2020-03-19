package com.indocyber.itsmeandroid.viewremastered.editcard.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
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
import com.indocyber.itsmeandroid.view.otp.OtpActivity;
import com.indocyber.itsmeandroid.view.requestincreaselimit.RequestIncreaseLimitActivity;

import org.apache.commons.text.WordUtils;

public class editkartu extends AppCompatActivity {

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
    private ImageView mCardImage;
    private String cardNumber;
    private int cardImage;
    private String cardHolder;
    private String cardBillingAddress;
    private String expiryDate;
    private int mCardImageResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editkartu);
        Bundle extras = getIntent().getExtras();
        cardNumber = extras.getString("cardNumber");
        cardHolder = extras.getString("cardHolder");
        cardImage = extras.getInt("cardImage");
        expiryDate = extras.getString("expiryDate");
        cardBillingAddress = extras.getString("cardBillingAddress");
        formatCreditCard();
        initializeCardHolder();
        initializeButton();
        initializeCardDetailInput();
        initializeCardExpiry();
        initializeCardNumber();
        createToolbar();
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
        ScrollView scrollView = findViewById(R.id.scrollContainer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView textView = findViewById(R.id.toolbar_text);
        VectorDrawable backButton = (VectorDrawable) getDrawable(R.drawable.ic_ico_arrow_back);
        int iconDimension = (int) getResources().getDimension(R.dimen._20sdp);
        Drawable resizedBackButton =
                UtilitiesCore.resizeDrawable(backButton, this, iconDimension , iconDimension);
        textView.setText("Edit Kartu Kredit");
        textView.setAllCaps(false);
        toolbar.setNavigationIcon(resizedBackButton);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0f);
        scrollView.setPadding(0, appbar.getHeight(), 0, 0);
    }

    private void initializeCardHolder() {
        mCardHolderInput = findViewById(R.id.txtCardHolder);
        mCardHolderInput.setText(cardHolder);
        mCardHolder.setText(cardHolder);
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
    }

    private void initializeCardNumber() {
        mCardImage = findViewById(R.id.imgCreditCard);
        mCardNumberInput = findViewById(R.id.txtCardNumber);
        mCardNumberInput.setText(cardNumber);
        mCardNumber.setText(cardNumber);
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

        mCardNumberInput.setOnFocusChangeListener((view, onFocus) -> {
            if (!onFocus && mCardNumberInput.getText().length() == 16) {
                if (!mCardNumberInput.getText().toString().substring(0, 1).equals("5")
                        && !mCardNumberInput.getText().toString().substring(0, 1).equals("4")) {
                    UtilitiesCore.buildAlertDialog(
                            this,
                            "Kartu tidak dikenal!",
                            R.drawable.ic_invalid,
                            null
                    );
                    return;
                }
                mCardImageResource = cardImage;
                mCardImage.setImageResource(mCardImageResource);
            }
        });
    }

    private void initializeCardExpiry() {
        mCardExpiryInput = findViewById(R.id.txtExpiryDateMonth);
        mCardExpiryInput.setText(expiryDate);
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
                    try {
                        if(Integer.valueOf(expiryDate.substring(0, 2)) > 12) {
                            expiryDate = "12/" + expiryDate.substring(2,3);
                        } else {
                            expiryDate = expiryDate.substring(0, 2) + "/" + expiryDate.substring(2,3);
                        }
                    } catch (Exception e){
                        Log.e("Error converting value", "Value is not a number");
                    }
                    mCardExpiryInput.setText(expiryDate);
                    mCardExpiryInput.setSelection(mCardExpiryInput.getText().length());
                }
//                String maxedYear = setMaxYear(expiryDate);
//                if (!maxedYear.equals(charSequence.toString())) {
//                    mCardExpiryInput.setText(maxedYear);
//                    mCardExpiryInput.setSelection(mCardExpiryInput.getText().length());
//                }
                mCardExpiry.setText(mCardExpiryInput.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void initializeCardDetailInput() {
        mBillingAddressInput = findViewById(R.id.txtBillingAddress);
        mBillingAddressInput.setText(cardBillingAddress);
        mBillingAddressInput.setMaxLines(5);
        mBillingAddressInput.setSingleLine(false);
        mBillingAddressInput.setVerticalScrollBarEnabled(true);
        mBillingAddressInput.setMovementMethod(ScrollingMovementMethod.getInstance());
        mBillingAddressInput.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
    }

    private void initializeButton() {
        Button mIncreaseCreditLimitButton;
        mIncreaseCreditLimitButton = findViewById(R.id.btnCreditLimit);
        mIncreaseCreditLimitButton.setOnClickListener(view -> requestIncreaseCreditLimit());

        Button mAddCreditCardButton;
        mAddCreditCardButton = findViewById(R.id.btnAddCc);
        mAddCreditCardButton.setOnClickListener(view -> submit());
    }

    private boolean formIsValid(){
        if (mCardNumberInput.getText().length() < 16) return false;

        if (mCardExpiryInput.getText().length() < 5) return false;

        if (mCardHolderInput.getText().length() < 1) return false;

        if (mBillingAddressInput.getText().toString().trim().length() < 1) return false;

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
        intent.putExtra("cardImageResource", mCardImageResource);
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
        intent.putExtra("cardNumber", mCardNumberInput.getText().toString());
        intent.putExtra("cardHolder", mCardHolderInput.getText().toString());
        intent.putExtra("expiryDate", mCardExpiryInput.getText().toString());
        intent.putExtra("billingAddress", mBillingAddressInput.getText().toString());
        intent.putExtra("country", mCountryInput.getSelectedItemPosition());
        intent.putExtra("city", mCityInput.getSelectedItemPosition());
        intent.putExtra("postalCode", mPostalCodeInput.getText().toString());
        intent.putExtra("cardImageResource", mCardImageResource);
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        formatCreditCard();
    }

    private void onCardNumberChange(final CharSequence text){
        StringBuilder paddedText = new StringBuilder(text + "");
        for(int i = paddedText.length(); i < 16; i++){
            paddedText.append("X");
        }

        String updatedText = paddedText.substring(0, 4) + "   " + paddedText.substring(4, 8) + "   "
                + paddedText.substring(8, 12) + "   " + paddedText.substring(12, 16);

        mCardNumber.setText(updatedText);
    }

}
