package com.indocyber.itsmeandroid.viewremastered.belipulsa.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.Pulsa;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;

import org.apache.commons.text.WordUtils;

import java.util.Objects;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.INTENT_PARCELABLE;

public class PembayaranKreditActivity extends AppCompatActivity {
    private Pulsa data;
    private Button btnBayar;
    private ImageButton imageBtnBack;
    private EditText edtxNamaLengkap;
    private EditText edtxNomorKartu;
    private String cardNumber;
    private String expiryDate;
    private String cardHolder;

    private TextView mCardNumber;
    private TextView mCardHolder;
    private TextView mCardExpiry;
    private EditText mCardNumberInput;
    private EditText mCardHolderInput;
    private EditText mExpiryMonthInput;
    private EditText mExpiryYearInput;
    private EditText edtxCvv;
    private EditText mBillingAddressInput;
    private ImageView mCardImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran_kredit);
        data = Objects.requireNonNull(getIntent().getExtras()).getParcelable(INTENT_PARCELABLE);
        initView();
        onClick();
        initializeCardNumber();
        initializeCardHolder();
        initializeCardExpiry();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        formatCreditCard();
    }


    private void initView() {
        btnBayar = findViewById(R.id.btnBayar);
        edtxNamaLengkap = findViewById(R.id.edtxNamaLengkap);
        edtxNomorKartu = findViewById(R.id.edtxNomorKartu);
        imageBtnBack = findViewById(R.id.imageBtnBack);
        edtxCvv = findViewById(R.id.edtxCvv);
    }

    private void onClick() {

        imageBtnBack.setOnClickListener(v -> {
            finish();
        });

        btnBayar.setOnClickListener(v -> {
            if (formIsValid()){
                Intent intent = new Intent(this, WebviewPembayaranActivity.class);
                intent.putExtra(INTENT_PARCELABLE, data);
                startActivity(intent);
            }else {
                Toast.makeText(this,   getString(R.string.form_incomplete_warning)+"", Toast.LENGTH_SHORT).show();
            }

        });
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
    private void initializeCardNumber() {
        mCardImage = findViewById(R.id.imgCreditCard);
        mCardNumberInput = findViewById(R.id.edtxNomorKartu);
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

            }
        });
    }
    private boolean formIsValid(){
        if (mCardNumberInput.getText().length() < 16) return false;

        if (mExpiryYearInput.getText().length() < 2) return false;

        if (mExpiryMonthInput.getText().length() < 2) return false;

        if (mCardHolderInput.getText().length() < 1) return false;
        if (edtxCvv.getText().length() < 2)return false;

        if (mBillingAddressInput.getText().toString().trim().length() < 1) return false;


        return true;
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
    private void initializeCardHolder() {
        mCardHolderInput = findViewById(R.id.edtxNamaLengkap);
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
        mExpiryMonthInput = findViewById(R.id.edtxMonth);
        mExpiryYearInput = findViewById(R.id.edtxYear);
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


}
