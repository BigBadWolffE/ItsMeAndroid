package com.indocyber.itsmeandroid.view.addcc;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;
import com.indocyber.itsmeandroid.view.otp.OtpActivity;
import com.indocyber.itsmeandroid.view.requestincreaselimit.RequestIncreaseLimitActivity;

import org.apache.commons.text.WordUtils;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.INTENT_ID;


/**
 * @author Muhammad Faisal
 * @version 1.0
 */
public final class AddCcActivity extends AppCompatActivity {

    private TextView mCardNumber;
    private TextView mCardHolder;
    private TextView mCardExpiry;
    private EditText mCardNumberInput;
    private EditText mCardHolderInput;
    private EditText mExpiryMonthInput;
    private EditText mExpiryYearInput;
    private EditText mBillingAddressInput;
    private Spinner mCountryInput;
    private Spinner mCityInput;
    private EditText mPostalCodeInput;
    private ImageView mCardImage;
    private int mCardImageResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cc);
        createToolbar();
        initializeCardNumber();
        initializeCardHolder();
        initializeCardExpiry();
        initializeCardDetailInput();
        initializeButton();
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
        ScrollView scrollView = findViewById(R.id.scrollContainer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView textView = findViewById(R.id.toolbar_text);
        VectorDrawable backButton = (VectorDrawable) getDrawable(R.drawable.ic_ico_arrow_back);
        int iconDimension = (int) getResources().getDimension(R.dimen._20sdp);
        Drawable resizedBackButton =
                UtilitiesCore.resizeDrawable(backButton, this, iconDimension , iconDimension);
        textView.setText("Tambah Kartu Kredit");
        textView.setAllCaps(false);
        toolbar.setNavigationIcon(resizedBackButton);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0f);
        scrollView.setPadding(0, appbar.getHeight(), 0, 0);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeCardNumber() {
        mCardImage = findViewById(R.id.imgCreditCard);
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
                mCardImageResource = randomizeCardImage();
                mCardImage.setImageResource(mCardImageResource);
            }
        });
    }

    private void initializeCardHolder() {
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
    }

    private void initializeCardExpiry() {
        mExpiryMonthInput = findViewById(R.id.txtExpiryDateMonth);
        mExpiryYearInput = findViewById(R.id.txtExpiryDateYear);
        mExpiryMonthInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        mExpiryMonthInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        mExpiryMonthInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int before, int after,
                                          int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int before, int after, int count) {
                String expiryDate = charSequence.toString();
                String newExpiryDate = "";
                if (charSequence.length() == 2 && count > 0 ) {
                    mExpiryYearInput.requestFocus();
                }

                if (mExpiryYearInput.length() < 1) {
                    newExpiryDate = expiryDate + "/20";
                } else if (mExpiryYearInput.length() < 2) {
                    newExpiryDate = expiryDate + "/0" + mExpiryYearInput.getText().toString();
                } else {
                    newExpiryDate = expiryDate + "/" + mExpiryYearInput.getText().toString();
                }
                mCardExpiry.setText(newExpiryDate);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mExpiryYearInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int before, int after,
                                          int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int before, int after, int count) {
                String expiryYear = charSequence.toString();
                String newExpiry = "";
                if (mExpiryMonthInput.length() < 1) {
                    newExpiry = "01/" + expiryYear;
                } else if (mExpiryMonthInput.length() < 2) {
                    newExpiry = "0" + mExpiryMonthInput.getText().toString() + "/" + expiryYear;
                } else {
                    newExpiry = mExpiryMonthInput.getText().toString() + "/" + expiryYear;
                }
                mCardExpiry.setText(newExpiry);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void initializeCardDetailInput() {
        mBillingAddressInput = findViewById(R.id.txtBillingAddress);
        mBillingAddressInput.setMaxLines(5);
        mBillingAddressInput.setSingleLine(false);
        mBillingAddressInput.setVerticalScrollBarEnabled(true);
        mBillingAddressInput.setMovementMethod(ScrollingMovementMethod.getInstance());
        mBillingAddressInput.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
    }

    // TODO: 07/01/2020 uncomment if expiry year need to be limited
//    private String setMaxYear(String expiryDate) {
//        String returnedDate = expiryDate;
//        if (returnedDate.length() == 5) {
//            try {
//                if(Integer.valueOf(returnedDate.substring(3, 5))
//                        > (UtilitiesCore.getCurrentYear() - 2000) + 10) {
//                    returnedDate = returnedDate.substring(0, 2) + "/"
//                            + ((UtilitiesCore.getCurrentYear() - 2000) + 10);
//                }
//            } catch (Exception e){
//                Log.e("Error converting value", "Value is not a number");
//            }
//            return returnedDate;
//        }
//        return expiryDate;
//    }

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

        if (mExpiryYearInput.getText().length() < 2) return false;

        if (mExpiryMonthInput.getText().length() < 2) return false;

        if (mCardHolderInput.getText().length() < 1) return false;

        if (mBillingAddressInput.getText().toString().trim().length() < 1) return false;

        if (mCardImageResource == 0) mCardImageResource = randomizeCardImage();

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
        intent.putExtra("expiryDate", mCardExpiry.getText().toString());
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

        ImageCardModel data = new ImageCardModel(
                mCardImageResource,
                mCardNumberInput.getText().toString(),
                mCardHolderInput.getText().toString(),
                mCardExpiry.getText().toString(),
                "20.000.000",
                "12/19",
                "12/24",
                false);
        Intent intent = new Intent(this, OtpActivity.class);
        intent.putExtra(INTENT_ID, data);
        startActivity(intent);
    }


    private void formatCreditCard(){
        ImageView creditCard = findViewById(R.id.imgCreditCard);
        int[] position = {0, 0};
        creditCard.getLocationOnScreen(position);

        int paddingLeft = (creditCard.getWidth() * 8 / 100);
        int startYAxis = (creditCard.getHeight() / 2);

        mCardNumber = findViewById(R.id.lblCcNumber);
        mCardNumber.setX(paddingLeft);
        mCardNumber.setY(startYAxis + getResources().getDimension(R.dimen.spacing_medium));
        mCardNumber.bringToFront();
        mCardHolder = findViewById(R.id.lblCardHolder);
        mCardHolder.setX(paddingLeft);
        mCardHolder.setY(creditCard.getHeight() * 80 / 100);

        mCardExpiry = findViewById(R.id.lblExpiry);
        mCardExpiry.setX(creditCard.getWidth() / 2);
        mCardExpiry.setY(creditCard.getHeight() * 70 / 100);
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

    private int randomizeCardImage() {
        int[] images = {
                R.drawable.img_bca_card_template,
                R.drawable.img_blank_kartukredit_anz,
                R.drawable.img_blank_kartukredit_citi
        };
        int randomValue = (int)(Math.random() * images.length);
        if(randomValue == images.length) randomValue = images.length - 1;
        return images[randomValue];
    }
}

