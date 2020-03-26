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
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;
import com.indocyber.itsmeandroid.viewremastered.requestincreaselimit.RequestIncreaseLimitActivity;
import com.indocyber.itsmeandroid.viewremastered.loginandregister.SetPinActivityRemastered;

import org.apache.commons.text.WordUtils;

import java.util.Objects;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.INTENT_ID;

public class editkartu extends AppCompatActivity {

    private TextView mCardNumber;
    private TextView mCardHolder;
    private TextView mCardExpiry;
    private EditText mCardNumberInput;
    private EditText mCardHolderInput;
    private EditText mExpiryMonthInput;
    private EditText mExpiryYearInput;
    private EditText mBillingAddressInput;
    private ImageView mCardImage;
    private String cardNumber;
    private int cardImage;
    private String cardHolder;
    private String cardBillingAddress;
    private String expiryDate;
    private int mCardImageResource;
    ImageCardModel data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editkartu);
        Bundle extras = getIntent().getExtras();
        data = Objects.requireNonNull(getIntent().getExtras()).getParcelable(INTENT_ID);
        cardNumber = data.getNumberCard();
        cardHolder = data.getNameCard();
        cardImage = data.getImage();
        expiryDate = data.getExpireCard();
        cardBillingAddress = data.getBillingAddress();
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
        mCardNumber.setText(padCardNumber(cardNumber, 3));
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
        mExpiryMonthInput = findViewById(R.id.txtExpiryDateMonth);
        mExpiryMonthInput.setText(expiryDate.substring(0, 2));
        mExpiryYearInput = findViewById(R.id.txtExpiryDateYear);
        mExpiryYearInput.setText(expiryDate.substring(3, 5));
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

        if (mExpiryMonthInput.getText().length() < 2) return false;

        if (mExpiryYearInput.getText().length() < 2) return false;

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
        data.setNumberCard(mCardNumberInput.getText().toString());
        data.setNameCard(mCardHolderInput.getText().toString());
        data.setExpireCard(mCardExpiry.getText().toString());
        data.setBillingAddress(mBillingAddressInput.getText().toString());
        intent.putExtra(INTENT_ID, data);
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

        Intent intent = new Intent(this, SetPinActivityRemastered.class);
        intent.putExtra("cardHolder", mCardHolderInput.getText().toString());
        intent.putExtra("cardExpiry", mCardExpiry.getText().toString());
        intent.putExtra("billingAddress", mBillingAddressInput.getText().toString());
        intent.putExtra("cardId", data.getId());
        intent.putExtra("cardNumber", mCardNumberInput.getText().toString());
        intent.putExtra("parentCode", 2);
        startActivity(intent);
    }

    private void formatCreditCard(){
        ImageView creditCard = findViewById(R.id.imgCreditCard);
        creditCard.setImageResource(data.getImage());
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
        mCardExpiry.setText(expiryDate);
        mCardExpiry.setX(creditCard.getWidth() / 2);
        mCardExpiry.setY(creditCard.getHeight() * 70 / 100);
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
