package com.indocyber.itsmeandroid.view.otp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.appbar.AppBarLayout;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;
import com.indocyber.itsmeandroid.view.home.activity.HomeActivity;
import com.indocyber.itsmeandroid.viewmodel.AddCcViewModel;
import com.indocyber.itsmeandroid.viewremastered.home.activity.HomeRemastered;
import com.indocyber.itsmeandroid.viewremastered.loginandregister.SetPinActivityRemastered;

import java.util.Objects;

import dmax.dialog.SpotsDialog;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.INTENT_ID;


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
        cardData = Objects.requireNonNull(getIntent().getExtras()).getParcelable(INTENT_ID);
        mCardNumber = cardData.getNumberCard();
        createToolbar();
        initializeOtpInput();
        timer = findViewById(R.id.lblOtpTimer);
        resendButton = findViewById(R.id.lblResendOtp);
        resendButton.setOnClickListener(view -> resendOtp());
        resendButton.setVisibility(View.GONE);
        confirmationButton = findViewById(R.id.btnConfirmationButton);
        confirmationButton.setOnClickListener(view -> confirm());
        startTimer();

        loader = new SpotsDialog.Builder()
                .setCancelable(false)
                .setContext(OtpActivity.this)
                .build();

        viewModel = ViewModelProviders.of(this).get(AddCcViewModel.class);
        observeViewModel();
    }

    // TODO: 23/12/2019 Delete after demo
    private void createCardFromExtras(Bundle extras) {
        cardData.setLastBill("Rp 15.000.000");
        cardData.setMinPayment("Rp 1.500.000");
        cardData.setAvailableCredit("Rp 5.000.000");
    }

//    // TODO: 23/12/2019 Delete after demo
//    private int randomizeCardImage() {
//        int[] images = {
//                R.drawable.img_blank_cc_anz,
//                R.drawable.img_blank_cc_bca,
//                R.drawable.img_blank_cc_mandiri
//        };
//        int randomValue = (int)(Math.random() * images.length);
//        if(randomValue == images.length) randomValue = images.length - 1;
//        return images[randomValue];
//    }

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
        textView.setText("Tambah Kartu Kredit");
        textView.setAllCaps(false);
        toolbar.setNavigationIcon(resizedBackButton);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0f);
    }

    private boolean otpIsValid() {
        return mOtp1.getText().length() >= 1 && mOtp2.getText().length() >= 1 &&
                mOtp3.getText().length() >= 1 && mOtp4.getText().length() >= 1;
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
        Intent intent = new Intent(this, HomeRemastered.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void observeViewModel() {
        viewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                loader.show();
            } else {
                loader.dismiss();
            }
        });

        viewModel.getIsSaved().observe(this, isSaved -> {
            if (isSaved) {
                showSuccessDialog(
                        R.drawable.ic_img_emotion_smile,
                        "Penambahan Kartu Kredit Anda",
                        padCardNumber(mCardNumber, 3) + "\nBerhasil",
                        dialogInterface -> {
                            Intent intent = new Intent(this, HomeRemastered.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        });
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

    private void showSuccessDialog(int icon, String smallText, String bigText, DialogInterface.OnDismissListener onDismiss) {
        androidx.appcompat.app.AlertDialog.Builder builder =
                new androidx.appcompat.app.AlertDialog.Builder(OtpActivity.this);
        View view = LayoutInflater.from(this).inflate(R.layout.alert_dialog_success, null);
        ImageView alertIcon = view.findViewById(R.id.imgAlertIcon);
        alertIcon.setImageResource(icon);
        TextView txtSmallText = view.findViewById(R.id.txtSmallText);
        txtSmallText.setText(smallText);
        TextView txtBigText = view.findViewById(R.id.txtBigText);
        txtBigText.setText(bigText);
        ImageView close = view.findViewById(R.id.closeAlert);
        builder.setView(view);
        builder.setOnDismissListener(onDismiss);
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        close.setOnClickListener(view1 -> dialog.dismiss());
        dialog.show();
    }

    private void showErrorDialog(int icon, String smallText, String bigText, String errorText, DialogInterface.OnDismissListener onDismiss) {
        androidx.appcompat.app.AlertDialog.Builder builder =
                new androidx.appcompat.app.AlertDialog.Builder(OtpActivity.this);
        View view = LayoutInflater.from(this).inflate(R.layout.alert_dialog_invalid, null);
        ImageView alertIcon = view.findViewById(R.id.imgAlertIcon);
        alertIcon.setImageResource(icon);
        TextView txtSmallText = view.findViewById(R.id.txtSmallText);
        txtSmallText.setText(smallText);
        TextView txtBigText = view.findViewById(R.id.txtBigText);
        txtBigText.setText(bigText);
        TextView txtErrorText = view.findViewById(R.id.txtErrorText);
        txtBigText.setText(errorText);
        ImageView close = view.findViewById(R.id.closeAlert);
        builder.setView(view);
        builder.setOnDismissListener(onDismiss);
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        close.setOnClickListener(view1 -> dialog.dismiss());
        dialog.show();
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
