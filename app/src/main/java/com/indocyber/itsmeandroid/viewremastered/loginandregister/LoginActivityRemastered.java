package com.indocyber.itsmeandroid.viewremastered.loginandregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.viewremastered.home.activity.HomeRemastered;

import java.util.regex.Pattern;

public class LoginActivityRemastered extends AppCompatActivity {

    public static TextView btnNext, registerText;
    public static EditText inputUserName;
    public static Pattern emailCustom;
    public static Pattern phoneCustom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_remastered);

        btnNext = findViewById(R.id.btn_login);
        registerText = findViewById(R.id.textView2);
        inputUserName = findViewById(R.id.ipt_username);

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

        phoneCustom
                = Pattern.compile("08" + "[0-9]{9,13}");

        inputUserName.addTextChangedListener(inputUsernameWatcher);
        inputUserName.requestFocus();
        inputUserName.setError(null);

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(LoginActivityRemastered.this, RegisterActivityRemastered.class);
                startActivityForResult(register, 1);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivityRemastered.this, HomeRemastered.class);
                startActivity(i);
                finish();
            }
        });
    }



    public TextWatcher inputUsernameWatcher = new TextWatcher() {
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
            if (inputUserName.getText().toString().trim().length() != 0)
                if (!emailCustom.matcher(inputUserName.getText().toString()).matches()) {
                    inputUserName.setError("Email atau nomor telpon");
                } else {
                    inputUserName.setError(null);
                }
            else if (!phoneCustom.matcher(inputUserName.getText().toString()).matches()) {
                inputUserName.setError("Email atau nomor telpon");
            } else {
                inputUserName.setError(null);
            }

        }
    };
}
