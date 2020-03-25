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

import java.util.regex.Pattern;

public class ResetPasswordFromForgotActivity extends AppCompatActivity {

    public static EditText pwSekarang, pwBaru, retypePwBaru;
    public static CardView lblButton;
    public static TextView btnSubmit;
    public static ImageView backButton;
    private Pattern passCustom;

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
        final String Password_Pattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
        passCustom = Pattern.compile(Password_Pattern);
        pwBaru.addTextChangedListener(passwordWatcher);
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

    public TextWatcher passwordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(pwBaru.getText().toString().trim().length() != 0)
                if (!passCustom.matcher(pwBaru.getText().toString()).matches()){
                    pwBaru.setError("Minimal 1 Huruf Besar 1 Angka dan Spesial Karakter @!^?");
                }else {
                    pwBaru.setError(null);
                }
        }

        @Override
        public void afterTextChanged(Editable editable) {



        }
    };


    public TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if(!retypePwBaru.getText().toString().equals(pwBaru.getText().toString())){
                btnSubmit.setEnabled(false);
                lblButton.setCardBackgroundColor(getResources().getColor(R.color.grey_login));
                btnSubmit.setTextColor(getResources().getColor(R.color.support_darker_grey));
            }
            else{
                btnSubmit.setEnabled(true);
                lblButton.setCardBackgroundColor(getResources().getColor(R.color.blue));
                btnSubmit.setTextColor(getResources().getColor(R.color.colorwhite));
            }
        }


        @Override
        public void afterTextChanged(Editable editable) {


        }
    };
}
