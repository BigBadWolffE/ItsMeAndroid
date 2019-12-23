package com.indocyber.itsmeandroid.view.editcardsecuritycode;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.chaos.view.PinView;
import com.davidmiguel.numberkeyboard.NumberKeyboard;
import com.davidmiguel.numberkeyboard.NumberKeyboardListener;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.view.addmembership.AddMembershipActivity;
import com.indocyber.itsmeandroid.view.editcreditcard.EditCreditCardActivity;

import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class EditCardSecurityCodeActivity extends AppCompatActivity implements NumberKeyboardListener {

    //private Pinview pinview;
    //private EditText edit_query;
    private AlertDialog alertDialog;
    private PinView firstPinView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_security_code);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Security Code");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setElevation(0f);
        }
        firstPinView = findViewById(R.id.firstPinView);
        hideKeyboard();
        setPinView();
        alertDialog = new SpotsDialog.Builder().setCancelable(false).setContext(EditCardSecurityCodeActivity.this).build();

        NumberKeyboard numberKeyboard = (NumberKeyboard) findViewById(R.id.numberKeyboard);
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
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            alertDialog.show();
                            new Handler().postDelayed(() -> {
                                alertDialog.dismiss();
                                finish();
                                Intent intent = new Intent(EditCardSecurityCodeActivity.this, EditCreditCardActivity.class);
                                startActivity(intent);

                            }, 800);
                        }
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
