package com.indocyber.itsmeandroid.viewremastered.loginandregister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.indocyber.itsmeandroid.R;

public class ResetPasswordFromForgotActivity extends AppCompatActivity {

    public static EditText pwSekarang, pwBaru, retypePwBaru;
    public static CardView lblButton;
    public static TextView btnSubmit;
    public static ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_layout_from_login);

        pwSekarang = findViewById(R.id.ipt_email_reset);
        pwBaru = findViewById(R.id.ipt_password_baru);
        retypePwBaru = findViewById(R.id.ipt__baru_retype);
        lblButton = findViewById(R.id.cardView2);
        btnSubmit = findViewById(R.id.btn_submit_reset);
        backButton = findViewById(R.id.imageView5);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSubmit.setEnabled(false);

        retypePwBaru.addTextChangedListener(textWatcher);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent reset = new Intent(ResetPasswordFromForgotActivity.this,ConfirmPinResetPasswordActivityRemastered.class);
                startActivityForResult(reset,56);

            }
        });
    }

    public TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if(pwBaru.getText().toString()!= null && pwSekarang.getText().toString()!= null && retypePwBaru.getText().toString()!= null){
                btnSubmit.setEnabled(true);
                lblButton.setCardBackgroundColor(getResources().getColor(R.color.blue));
                btnSubmit.setTextColor(getResources().getColor(R.color.colorwhite));
            }
            else{
                btnSubmit.setEnabled(false);
                lblButton.setCardBackgroundColor(getResources().getColor(R.color.grey_40));
                btnSubmit.setTextColor(getResources().getColor(R.color.grey_main_medium));
            }
        }


        @Override
        public void afterTextChanged(Editable editable) {


        }
    };
}
