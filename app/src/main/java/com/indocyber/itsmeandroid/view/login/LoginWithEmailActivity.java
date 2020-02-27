package com.indocyber.itsmeandroid.view.login;

import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.utilities.Preference;
import com.indocyber.itsmeandroid.view.BaseActivity;
import com.indocyber.itsmeandroid.view.forgotpassword.activity.ForgotPasswordActivity;
import com.indocyber.itsmeandroid.view.home.activity.HomeActivity;
import com.indocyber.itsmeandroid.viewmodel.LoginViewModel;
import com.indocyber.itsmeandroid.viewmodel.ViewModelFactory;

import javax.inject.Inject;

import dmax.dialog.SpotsDialog;

import static com.indocyber.itsmeandroid.utilities.UtilitiesCore.snackBarIconError;
import static com.indocyber.itsmeandroid.view.login.LoginOptionActivity.INTENT_NAME;

public class LoginWithEmailActivity extends BaseActivity {

    private LoginViewModel viewModel;
    private AlertDialog loader;
    private EditText edtxUsername;
    private EditText edtxPassword;
    private Preference preference;
    private String base64key;
    @Inject
    ViewModelFactory factory;

    @Override
    protected int layoutRes() {
        return R.layout.activity_login_with_email;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutRes());

        Bundle extras = getIntent().getExtras();
        String username = "";
        if (extras != null)  username = extras.getString(INTENT_NAME);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Login");
            getSupportActionBar().setElevation(0f);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        loader = new SpotsDialog.Builder()
                .setCancelable(false)
                .setContext(LoginWithEmailActivity.this)
                .build();

        TextView mForgotPasswordBtn = findViewById(R.id.labelForgotPassword);
        Button mbuttonLogin = findViewById(R.id.buttonLogin);
        edtxUsername = findViewById(R.id.edtxUsername);
        edtxPassword = findViewById(R.id.edtxPassword);
        viewModel = ViewModelProviders.of(this, factory).get(LoginViewModel.class);
        preference = new Preference(this);
        edtxUsername.setText(username);
        mbuttonLogin.setOnClickListener(v -> {
            if (edtxUsername.getText().length() == 0) {
                edtxUsername.setError("field empty");
                edtxUsername.requestFocus();
            } else if (edtxPassword.getText().length() == 0) {
                edtxPassword.setError("field empty");
                edtxPassword.requestFocus();
            } else {
                String key = edtxUsername.getText().toString() + ":" + edtxPassword.getText().toString();
                base64key = Base64.encodeToString(key.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP);
                viewModel.login(base64key);
            }
        });
        mForgotPasswordBtn.setOnClickListener(view -> {
            Intent intent = new Intent(LoginWithEmailActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
        observeViewModel();
}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
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
                preference.setLoginFirstTime(true);
                preference.setLoggedUser(user.getNamaLengkap(), user.getEmail());
                preference.saveUserAuth(base64key);
                Intent intent = new Intent(LoginWithEmailActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        viewModel.getError().observe(this, error -> {
            if (error != null) {
                snackBarIconError(LoginWithEmailActivity.this,"Invalid username or password");
            }
        });
    }
}
