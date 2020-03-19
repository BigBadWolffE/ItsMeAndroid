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

public class LupaPasswordActivityRemastered extends AppCompatActivity {
    public static ImageView backButton;
    public static EditText etemailReset;
    public static TextView btnReset;
    public static Pattern emailCustom;
    public static CardView cdBackgroundReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password_remastered);
        backButton= findViewById(R.id.imageView5);
        etemailReset = findViewById(R.id.ipt_email_reset);
        btnReset = findViewById(R.id.btn_submit_reset);
        cdBackgroundReset =findViewById(R.id.cardView2);
        btnReset.setEnabled(false);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        emailCustom
                = Pattern.compile(
                "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,500}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,500}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,500}" +
                        ")+"
        );

        etemailReset.addTextChangedListener(etemailResetWatcher);
        etemailReset.requestFocus();
        etemailReset.setError(null);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(LupaPasswordActivityRemastered.this,ResetPasswordFromForgotActivity.class);
                startActivityForResult(register,1);
            }
        });


    }
    public TextWatcher etemailResetWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {


        }

        @Override
        public void afterTextChanged(Editable s) {


//                if (etEmail.getText().toString().trim().length()>0){
//                    etEmail.setError(null);
//                }else
            if(etemailReset.getText().toString().trim().length() != 0)
                if (!emailCustom.matcher(etemailReset.getText().toString()).matches()){
                    etemailReset.setError("Email atau nomor telpon");
                } else {
                    etemailReset.setError(null);
                    cdBackgroundReset.setCardBackgroundColor(getResources().getColor(R.color.blue));
                    btnReset.setEnabled(true);
                    btnReset.setTextColor(getResources().getColor(R.color.colorwhite));

                }

        }
    };

}
