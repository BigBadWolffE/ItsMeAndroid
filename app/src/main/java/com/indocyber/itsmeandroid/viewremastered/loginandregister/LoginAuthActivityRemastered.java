package com.indocyber.itsmeandroid.viewremastered.loginandregister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.view.splashscreen.SplashScreenActivity;
import com.indocyber.itsmeandroid.viewremastered.home.activity.HomeRemastered;
import com.indocyber.itsmeandroid.viewremastered.loginandregister.helper.SavePref;

import java.util.regex.Pattern;

public class LoginAuthActivityRemastered extends AppCompatActivity {

    public static EditText etusernameauth,etpasswordauth;
    public static ImageView backButton;
    public static TextView btnLoginAuth,btnLupaPass;
    public static CardView cdCaution,cdlblAuthlogin;
    public static Pattern emailCustom;
    public static Pattern phoneCustom;
    private Pattern passwordCustom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_auth_remastered);
        etusernameauth = findViewById(R.id.ipt_username);
        etpasswordauth = findViewById(R.id.ipt_password);
        btnLoginAuth = findViewById(R.id.btn_login);
        btnLupaPass = findViewById(R.id.passwordLupa);
        backButton = findViewById(R.id.imageView5);
        cdCaution = findViewById(R.id.caution);
        cdlblAuthlogin = findViewById(R.id.layout_btn_next);

        final String Password_Pattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        passwordCustom = Pattern.compile(Password_Pattern);

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
                = Pattern.compile("08"+"[0-9]{9,13}");

        btnLoginAuth.setEnabled(false);
        etusernameauth.setText(SavePref.readLoginUser(this));
        etusernameauth.addTextChangedListener(usernameauthWatcher);
        etusernameauth.requestFocus();
        etusernameauth.setError(null);
        etpasswordauth.addTextChangedListener(passauthWatcher);
        btnLupaPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(LoginAuthActivityRemastered.this, LupaPasswordActivityRemastered.class);
                startActivityForResult(newIntent, 0);
            }
        });
        btnLoginAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etpasswordauth.getText().toString().length()<=5){
                    etusernameauth.getBackground().setColorFilter(getResources().getColor(R.color.red_main), PorterDuff.Mode.SRC_ATOP);
                    etpasswordauth.getBackground().setColorFilter(getResources().getColor(R.color.red_main), PorterDuff.Mode.SRC_ATOP);
                    cdCaution.setVisibility(View.VISIBLE);
                    btnLoginAuth.setEnabled(false);
                    cdlblAuthlogin.setCardBackgroundColor(getResources().getColor(R.color.grey_main_dark));
                }else if(etpasswordauth.getText().toString().length()>5){
                    etusernameauth.getBackground().setColorFilter(getResources().getColor(R.color.grey_main_medium), PorterDuff.Mode.SRC_ATOP);
                    etpasswordauth.getBackground().setColorFilter(getResources().getColor(R.color.grey_main_medium), PorterDuff.Mode.SRC_ATOP);
                    cdCaution.setVisibility(View.GONE);
                    Intent newIntent = new Intent(LoginAuthActivityRemastered.this, HomeRemastered.class);
                    startActivityForResult(newIntent, 0);
                }
                finish();
            }
        });

    }



    public TextWatcher usernameauthWatcher = new TextWatcher() {
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
            if(etusernameauth.getText().toString().trim().length() != 0)
                if (!emailCustom.matcher(etusernameauth.getText().toString()).matches()){
                    etusernameauth.setError("Email atau nomor telpon");
                } else {
                    etusernameauth.setError(null);
                }

        }
    };
    public TextWatcher passauthWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(etpasswordauth.getText().toString().trim().length() != 0){
                if (!passwordCustom.matcher(etpasswordauth.getText().toString()).matches()){
                    etpasswordauth.setError("Berisi 1 huruf kapital 1 angka 1 symbol");
                    btnLoginAuth.setEnabled(false);
                    cdlblAuthlogin.setCardBackgroundColor(getResources().getColor(R.color.grey_login));
                } else {
                    etpasswordauth.setError(null);
                    cdlblAuthlogin.setCardBackgroundColor(getResources().getColor(R.color.blue));
                    btnLoginAuth.setEnabled(true);
                    btnLoginAuth.setTextColor(getResources().getColor(R.color.colorwhite));
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {


        }
    };

}
