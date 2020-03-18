package com.indocyber.itsmeandroid.viewremastered.loginandregister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.chaos.view.PinView;
import com.davidmiguel.numberkeyboard.NumberKeyboard;
import com.davidmiguel.numberkeyboard.NumberKeyboardListener;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.view.addmembership.AddMembershipActivity;

import dmax.dialog.SpotsDialog;


public class SetPinActivityRemastered extends AppCompatActivity implements NumberKeyboardListener {
    public static PinView firstPinView;
    public static AlertDialog alertDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_remastered);

        hideKeyboard();
        setPinView();

        alertDialog = new SpotsDialog.Builder().setCancelable(false).setContext(SetPinActivityRemastered.this).build();


        NumberKeyboard numberKeyboard = findViewById(R.id.numberKeyboard);
        numberKeyboard.setListener(this);
    }

    private void setPinView() {
        firstPinView.setTextColor(
                ResourcesCompat.getColor(getResources(), R.color.black, getTheme()));

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
                    new Handler().postDelayed(() -> {
                        alertDialog.show();
                        new Handler().postDelayed(() -> {
                            alertDialog.dismiss();
                            finish();
                            Intent intent = new Intent(SetPinActivityRemastered.this, AddMembershipActivity.class);
                            startActivity(intent);


                        }, 800);
                    }, 200);
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
    public void onNumberClicked(int i) {

    }

    @Override
    public void onRightAuxButtonClicked() {

    }
}
