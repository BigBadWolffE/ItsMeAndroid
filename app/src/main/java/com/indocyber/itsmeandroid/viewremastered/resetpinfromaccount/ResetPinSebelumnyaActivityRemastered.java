package com.indocyber.itsmeandroid.viewremastered.resetpinfromaccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaos.view.PinView;
import com.davidmiguel.numberkeyboard.NumberKeyboard;
import com.davidmiguel.numberkeyboard.NumberKeyboardListener;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.viewremastered.resetpinfromaccount.popUp.PopUpResetPinSuccess;

import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class ResetPinSebelumnyaActivityRemastered extends AppCompatActivity implements NumberKeyboardListener {

    public static PinView firstPinView;
    public static AlertDialog alertDialog;
    public static ImageView backButton;
    public static TextView submitPin;
    public static CardView lblSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pin_sebelumnya_remastered);


        firstPinView = findViewById(R.id.firstPinView);
        hideKeyboard();
        setPinView();
        alertDialog = new SpotsDialog.Builder().setCancelable(false).setContext(ResetPinSebelumnyaActivityRemastered.this).build();

        NumberKeyboard numberKeyboard = findViewById(R.id.numberKeyboardOtp);
        numberKeyboard.setListener(this);

        backButton = findViewById(R.id.imageView5);
        submitPin = findViewById(R.id.btn_pin_register);
        lblSubmit = findViewById(R.id.lbl_btn_validation);
        submitPin.setEnabled(false);
        alertDialog = new SpotsDialog.Builder().setCancelable(false).setContext(ResetPinSebelumnyaActivityRemastered.this).build();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        submitPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(() -> {
                    alertDialog.show();
                    new Handler().postDelayed(() -> {
                        alertDialog.dismiss();
                        Intent intent = new Intent(ResetPinSebelumnyaActivityRemastered.this, ResetPinFromAkunActivityRemastered.class);
                        startActivity(intent);


                    }, 800);
                }, 200);
            }
        });
    }

    private void setPinView() {
        firstPinView.setTextColor(ResourcesCompat.getColor(getResources(), R.color.black, getTheme()));
        firstPinView.setItemCount(6);
        firstPinView.setItemHeight(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_size));
        firstPinView.setItemWidth(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_size));
        firstPinView.setItemRadius(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_radius));
        firstPinView.setItemSpacing(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_spacing));
        firstPinView.setLineWidth(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_line_width));
        firstPinView.setAnimationEnable(true);// start animation when adding text
        firstPinView.setCursorVisible(false);
        firstPinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (before == 6) {
                    submitPin.setEnabled(true);
                    lblSubmit.setCardBackgroundColor(getResources().getColor(R.color.blue));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        firstPinView.setCursorWidth(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_cursor_width));

        firstPinView.setItemBackgroundColor(Color.WHITE);

        firstPinView.setHideLineWhenFilled(false);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }


    @Override
    public void onLeftAuxButtonClicked() {

    }

    @Override
    public void onNumberClicked(int number) {
        firstPinView.setText(Objects.requireNonNull(firstPinView.getText()).append(String.valueOf(number)));
        /*pinview.setValue(pinview.getValue() + number);
        edit_query.setText(edit_query.getText().append(String.valueOf(number)));
        Toast.makeText(this, pinview.getValue()+"", Toast.LENGTH_SHORT).show();*/


    }

    @Override
    public void onRightAuxButtonClicked() {
        if (!Objects.requireNonNull(firstPinView.getText()).toString().equals("")) {
            StringBuilder build = new StringBuilder(firstPinView.getText().toString());
            build.deleteCharAt(firstPinView.getText().toString().length() - 1);
            firstPinView.setText(build.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }



}
