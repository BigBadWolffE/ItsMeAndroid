package com.indocyber.itsmeandroid.viewremastered.loginandregister;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.utilities.Preference;
import com.indocyber.itsmeandroid.viewremastered.BaseActivity;
import com.indocyber.itsmeandroid.viewmodel.LoginViewModel;
import com.indocyber.itsmeandroid.viewmodel.ViewModelFactory;
import com.indocyber.itsmeandroid.viewremastered.home.activity.HomeRemastered;

import java.util.regex.Pattern;

import javax.inject.Inject;

import dmax.dialog.SpotsDialog;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.INTENT_ID;

public class LoginAuthActivityRemastered extends BaseActivity {

    public static EditText etusernameauth, etpasswordauth;
    private ImageView backButton;
    private ImageView imgWarning;
    private TextView btnLoginAuth, btnLupaPass, txtWarning;
    private CardView cdCaution, cdlblAuthlogin;
    private Pattern emailCustom;
    private Pattern phoneCustom;
    private Preference preference;
    private String base64key;
    private AlertDialog loader;
    private ProgressBar progressBar;
    private String username = "";
    private LoginViewModel viewModel;
    private Pattern passwordCustom;
    private int hide_password;
    private ImageButton txtPass;

    @Inject
    ViewModelFactory factory;

    @Override
    protected int layoutRes() {
        return R.layout.activity_login_auth_remastered;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutRes());

        viewModel = ViewModelProviders.of(this, factory).get(LoginViewModel.class);
        preference = new Preference(this);
        loader = new SpotsDialog.Builder()
                .setCancelable(false)
                .setContext(LoginAuthActivityRemastered.this)
                .build();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            username = bundle.getString(INTENT_ID);

        etusernameauth = findViewById(R.id.ipt_username);
        etpasswordauth = findViewById(R.id.ipt_password);
        btnLoginAuth = findViewById(R.id.btn_login);
        btnLupaPass = findViewById(R.id.passwordLupa);
        backButton = findViewById(R.id.imageView5);
        cdCaution = findViewById(R.id.caution);
        cdlblAuthlogin = findViewById(R.id.layout_btn_next);
        txtWarning = findViewById(R.id.txtWarning);
        imgWarning = findViewById(R.id.imgWarning);
        progressBar = findViewById(R.id.progressBar);
        txtPass = findViewById(R.id.txtPass);

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
                = Pattern.compile("08" + "[0-9]{9,13}");

        btnLoginAuth.setEnabled(false);
        etusernameauth.addTextChangedListener(usernameauthWatcher);
        etusernameauth.requestFocus();
        etusernameauth.setError(null);
        etpasswordauth.addTextChangedListener(passauthWatcher);
        progressBar.setVisibility(View.GONE);

        onClick();
        observeViewModel();

        etusernameauth.setText(username);
        showAndHidePassword(etpasswordauth, txtPass);
        //etpasswordauth.setText("rahasia");


    }

    private void observeViewModel() {
        viewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                loader.show();
            } else {
                loader.dismiss();
            }
        });

        viewModel.getUser().observe(this, user -> {
            if (user != null) {

                cdlblAuthlogin.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
               /* cdCaution.setVisibility(View.VISIBLE);
                cdCaution.getBackground().setColorFilter(getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);
                txtWarning.setTextColor(Color.parseColor("#1ac08e"));
                txtWarning.setText("Login success");
                imgWarning.setImageResource(R.drawable.ico_check);*/

                preference.setLoginFirstTime(true);
                preference.setLoggedUser(user.getNamaLengkap(), user.getEmail());
                preference.saveUserAuth(base64key);
                preference.setMetaData(user.getPictureMetaData());

                Intent intent = new Intent(LoginAuthActivityRemastered.this, HomeRemastered.class);
                startActivity(intent);
                finish();
            }
        });

        viewModel.getError().observe(this, error -> {
            if (error != null) {
                cdCaution.setVisibility(View.VISIBLE);
            }
        });
    }

    private void onClick() {
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
                loader.show();
                if (etpasswordauth.getText().toString().length() <= 5) {
                    etusernameauth.getBackground().setColorFilter(getResources().getColor(R.color.red_main), PorterDuff.Mode.SRC_ATOP);
                    etpasswordauth.getBackground().setColorFilter(getResources().getColor(R.color.red_main), PorterDuff.Mode.SRC_ATOP);
                    cdCaution.setVisibility(View.VISIBLE);
                    btnLoginAuth.setEnabled(false);
                    cdlblAuthlogin.setCardBackgroundColor(getResources().getColor(R.color.grey_main_dark));

                    loader.dismiss();

                } else if (etpasswordauth.getText().toString().length() > 5) {
                    etusernameauth.getBackground().setColorFilter(getResources().getColor(R.color.grey_main_medium), PorterDuff.Mode.SRC_ATOP);
                    etpasswordauth.getBackground().setColorFilter(getResources().getColor(R.color.grey_main_medium), PorterDuff.Mode.SRC_ATOP);
                    cdCaution.setVisibility(View.GONE);

                    String key = etusernameauth.getText().toString().trim() + ":" + etpasswordauth.getText().toString().trim();
                    base64key = Base64.encodeToString(key.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP);
                    viewModel.login(base64key);

                }
                /*else {
                    String key = etusernameauth.getText().toString() + ":" + etpasswordauth.getText().toString();
                    base64key = Base64.encodeToString(key.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP);
                    viewModel.login(base64key);
                    Toast.makeText(LoginAuthActivityRemastered.this, "DONE", Toast.LENGTH_SHORT).show();
                   *//* Intent newIntent = new Intent(LoginAuthActivityRemastered.this, HomeRemastered.class);
                    startActivityForResult(newIntent, 0);*//*
                }*/

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private TextWatcher usernameauthWatcher = new TextWatcher() {
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
            if (etusernameauth.getText().toString().trim().length() != 0)
                if (!emailCustom.matcher(etusernameauth.getText().toString()).matches()) {
                    etusernameauth.setError("Email atau nomor telpon");
                } else {
                    etusernameauth.setError(null);
                }

        }
    };


    private TextWatcher passauthWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (etpasswordauth.getText().toString().trim().length() < 1) {
//                if(etpasswordauth.getText().toString().trim().length() != 0){
//                if (!passwordCustom.matcher(etpasswordauth.getText().toString()).matches()){
                etpasswordauth.setError("Berisi 1 huruf kapital 1 angka 1 symbol");
                btnLoginAuth.setEnabled(false);
                cdlblAuthlogin.setCardBackgroundColor(getResources().getColor(R.color.grey_login));
            } else {
                etpasswordauth.setError(null);
                cdlblAuthlogin.setCardBackgroundColor(getResources().getColor(R.color.blue));
                btnLoginAuth.setEnabled(true);
                btnLoginAuth.setTextColor(getResources().getColor(R.color.colorwhite));
            }
//            }
        }

        @Override
        public void afterTextChanged(Editable editable) {


        }
    };

    private void showAndHidePassword(final EditText editText, final ImageButton imageButton) {
        hide_password = 1;
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hide_password == 1) {
                    hide_password = 0;
                    editText.setTransformationMethod(null);
                    if (editText.getText().length() > 0)
                        //imageButton.setBackgroundColor(Color.WHITE);
                        editText.setSelection(editText.getText().length());
                    imageButton.setImageResource(R.drawable.ic_remove_red_eye_primary_24dp);
                } else {
                    hide_password = 1;
                    editText.setTransformationMethod(new PasswordTransformationMethod());
                    if (editText.getText().length() > 0)
                        editText.setSelection(editText.getText().length());

                    imageButton.setImageResource(R.drawable.ic_remove_red_eye_grey_24dp);


                }
            }
        });
    }
}
