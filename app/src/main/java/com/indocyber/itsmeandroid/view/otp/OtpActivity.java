package com.indocyber.itsmeandroid.view.otp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;
import com.indocyber.itsmeandroid.view.home.activity.HomeActivity;
import com.indocyber.itsmeandroid.viewmodel.AddCcViewModel;

import dmax.dialog.SpotsDialog;

/**
 *
 * @author Muhammad Faisal
 * @version 1.0
 */
public class OtpActivity extends AppCompatActivity {

    private EditText mOtp1;
    private EditText mOtp2;
    private EditText mOtp3;
    private EditText mOtp4;
    private TextView timer;
    private TextView resendButton;
    private Button confirmationButton;
    private int timerInterval = 30;
    private Handler mHandler = new Handler();
    private String mCardNumber;
    private ImageCardModel cardData;
    private AddCcViewModel viewModel;
    private AlertDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        Bundle extras = getIntent().getExtras();
        mCardNumber = extras.getString("cardNumber");
        createCardFromExtras(extras);
        createToolbar();
        initializeOtpInput();
        timer = findViewById(R.id.lblOtpTimer);
        resendButton = findViewById(R.id.lblResendOtp);
        resendButton.setOnClickListener(view -> resendOtp());
        resendButton.setVisibility(View.GONE);
        confirmationButton = findViewById(R.id.btnConfirmationButton);
        confirmationButton.setOnClickListener(view -> confirm());
        startTimer();

        viewModel = ViewModelProviders.of(this).get(AddCcViewModel.class);
        observeViewModel();
    }

    // TODO: 23/12/2019 Delete after demo
    private void createCardFromExtras(Bundle extras) {
        String cardHolder = extras.getString("cardHolder");
        String expiryDate = extras.getString("expiryDate");
        String billingAddress = extras.getString("billingAddress");
        String country = extras.getString("country");
        String city = extras.getString("city");
        String postalCode = extras.getString("postalCode");

        cardData = new ImageCardModel(
                randomizeCardImage(),
                mCardNumber.replace("   ", " "),
                cardHolder,
                expiryDate,
                "Rp 20.000.000",
                "15 November 2019",
                "1 November 2019",
                false
                );
        cardData.setBillingAddress(billingAddress);
        cardData.setCountry(country);
        cardData.setCity(city);
        cardData.setPostalCode(postalCode);
        cardData.setLastBill("Rp 15.000.000");
        cardData.setMinPayment("Rp 1.500.000");
        cardData.setAvailableCredit("Rp 5.000.000");
    }

    // TODO: 23/12/2019 Delete after demo
    private int randomizeCardImage() {
        int[] images = {
                R.drawable.img_blank_cc_anz,
                R.drawable.img_blank_cc_bca,
                R.drawable.img_blank_cc_mandiri
        };
        int randomValue = (int)(Math.random() * images.length);
        if(randomValue == images.length) randomValue = images.length - 1;
        return images[randomValue];
    }

    private void initializeOtpInput(){
        mOtp1 = findViewById(R.id.txtOtp1);
        mOtp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if(count > 0){
                    mOtp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mOtp2 = findViewById(R.id.txtOtp2);
        mOtp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if(count > 0){
                    mOtp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mOtp2.setOnKeyListener((view, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                mOtp1.setText("");
                mOtp1.requestFocus();
            }
            return false;
        });
        mOtp3 = findViewById(R.id.txtOtp3);
        mOtp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if(count > 0){
                    mOtp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mOtp3.setOnKeyListener((view, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                mOtp2.setText("");
                mOtp2.requestFocus();
            }
            return false;
        });
        mOtp4 = findViewById(R.id.txtOtp4);
        mOtp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if(count > 0){
                    confirmationButton.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mOtp4.setOnKeyListener((view, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                mOtp3.setText("");
                mOtp3.requestFocus();
            }
            return false;
        });

    }

    private void startTimer(){
        timerInterval = 30;
        resendButton.setVisibility(View.GONE);

        Runnable timeUpdater = new Runnable() {
            @Override
            public void run() {
                timerInterval -= 1;
                if (timerInterval == 0) {
                    timerInterval = 0;
                    mHandler.removeCallbacksAndMessages(null);
                    resendButton.setVisibility(View.VISIBLE);
                    timer.setText("00:00");
                } else {
                    String time;
                    if (timerInterval < 10) {
                        time = "00:0" + timerInterval;
                    } else {
                        time = "00:" + timerInterval;
                    }
                    timer.setText(time);
                    mHandler.postDelayed(this, 1000);
                }
            }
        };
        mHandler.post(timeUpdater);
    }

    private void resendOtp(){
        startTimer();
    }

    private  void createToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Confirmation OTP");
            getSupportActionBar().setElevation(0f);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private boolean otpIsValid() {
        if(mOtp1.getText().length() < 1 || mOtp2.getText().length() < 1 ||
                mOtp3.getText().length() < 1 || mOtp4.getText().length() < 1){
            return false;
        }

        return true;
    }

    private void confirm(){
        if (!otpIsValid()) {
            UtilitiesCore.buildAlertDialog(
                    this,
                    getString(R.string.invalid_otp_alert),
                    R.drawable.ic_invalid,
                    null
            );
            return;
        }
        viewModel.addCreditCard(cardData);
    }

    private void returnToHome(){
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void observeViewModel() {
        viewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                loader = new SpotsDialog.Builder()
                        .setCancelable(false)
                        .setContext(OtpActivity.this)
                        .build();
                loader.show();
            } else {
                loader.dismiss();
            }
        });

        viewModel.getIsSaved().observe(this, isSaved -> {
            if (isSaved) {
                String styledText = "Penambahan Credit Card Anda<br>"
                        + "<big><b>" + mCardNumber + "</b></big><br>"
                        + "Berhasil";

                UtilitiesCore.buildAlertDialog(
                        this,
                        Html.fromHtml(styledText),
                        R.drawable.ic_approved,
                        dialogInterface -> returnToHome(),
                        310,
                        320
                );
            }
        });

        viewModel.getError().observe(this, error -> {
            if (error.length() > 0) {
                UtilitiesCore.buildAlertDialog(
                        this,
                        error,
                        R.drawable.ic_invalid,
                        null
                );
            }
        });
    }
}
