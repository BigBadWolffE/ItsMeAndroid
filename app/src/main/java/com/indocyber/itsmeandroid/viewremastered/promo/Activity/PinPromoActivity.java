package com.indocyber.itsmeandroid.viewremastered.promo.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PinPromoActivity extends AppCompatActivity {

    @BindView(R.id.pinView)
    PinView mPinView;
    @BindView(R.id.txt_pin_input)
    TextView mBtnInputPin;
    @BindView(R.id.btn_submit)
    CardView mBtnSubmit;

//    @BindView(R.id.txtPin1)
//    EditText mTxtPin1;
//    @BindView(R.id.txtPin2)
//    EditText mTxtPin2;
//    @BindView(R.id.txtPin3)
//    EditText mTxtPin3;
//    @BindView(R.id.txtPin4)
//    EditText mTxtPin4;
//    @BindView(R.id.txtPin5)
//    EditText mTxtPin5;
//    @BindView(R.id.txtPin6)
//    EditText mTxtPin6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_promo);
        ButterKnife.bind(this);
        hideKeyboard();
        setPinView();

//        mTxtPin1.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (mTxtPin1.getText().toString().length() == 1) {
//                    mTxtPin2.requestFocus();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        mTxtPin2.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (mTxtPin2.getText().toString().length() == 1) {
//                    mTxtPin3.requestFocus();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        mTxtPin3.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (mTxtPin3.getText().toString().length() == 1) {
//                    mTxtPin4.requestFocus();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        mTxtPin4.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (mTxtPin4.getText().toString().length() == 1) {
//                    mTxtPin5.requestFocus();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        mTxtPin5.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (mTxtPin5.getText().toString().length() == 1) {
//                    mTxtPin6.requestFocus();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        mTxtPin6.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
////                if (mTxtPin6.getText().toString().length() == 1) {
////                    mTxtPin6.clearFocus();
////                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

    }

    @OnClick(R.id.btn_submit)
    void konfirmasiPin() {
//        if (mTxtPin1.getText().toString().isEmpty() || mTxtPin2.getText().toString().isEmpty() || mTxtPin3.getText().toString().isEmpty() || mTxtPin4.getText().toString().isEmpty() || mTxtPin5.getText().toString().isEmpty() || mTxtPin6.getText().toString().isEmpty()) {
//            Toast.makeText(this, "PIN Yang Anda masukan belum lengkap.", Toast.LENGTH_LONG).show();
//        } else {
            UtilitiesCore.buildAlertDialog(
                    this,
                    "kode promo telah dikirim ke nomor handhone anda",
                    R.drawable.ic_approved,
                    dialogInterface -> {
                        dialogInterface.dismiss();
                        finish();
                    });
//        }

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

    private void setPinView() {
        mPinView.setTextColor(ResourcesCompat.getColor(getResources(), R.color.black, getTheme()));
        mPinView.setItemCount(6);
        mPinView.setItemHeight(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_size));
        mPinView.setItemWidth(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_size));
        mPinView.setItemRadius(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_radius));
        mPinView.setItemSpacing(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_spacing));
        mPinView.setLineWidth(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_line_width));
        mPinView.setAnimationEnable(true);// start animation when adding text
        mPinView.setCursorVisible(false);
        mPinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (before == 6) {
                    mBtnSubmit.setEnabled(true);
                    mBtnSubmit.setCardBackgroundColor(getResources().getColor(R.color.blue));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mPinView.setCursorWidth(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_cursor_width));

        mPinView.setItemBackgroundColor(Color.WHITE);

        mPinView.setHideLineWhenFilled(false);
    }
}
