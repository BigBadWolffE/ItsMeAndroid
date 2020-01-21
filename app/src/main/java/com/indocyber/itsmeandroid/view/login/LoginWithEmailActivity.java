package com.indocyber.itsmeandroid.view.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.utilities.Preference;
import com.indocyber.itsmeandroid.view.forgotpassword.activity.ForgotPasswordActivity;
import com.indocyber.itsmeandroid.view.home.activity.HomeActivity;
import com.indocyber.itsmeandroid.view.register.RegistrationActivity;
import com.indocyber.itsmeandroid.viewmodel.LoginViewModel;

import dmax.dialog.SpotsDialog;

import static com.indocyber.itsmeandroid.utilities.UtilitiesCore.snackBarIconError;
import static com.indocyber.itsmeandroid.view.login.LoginOptionActivity.INTENT_NAME;

public class LoginWithEmailActivity extends AppCompatActivity {
    private Button mbuttonLogin;
    private String username;
    private LoginViewModel viewModel;
    private AlertDialog loader;
    private EditText edtxUsername;
    private EditText edtxPassword;
    private Preference preference;
    private TextView mForgotPasswordBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_email);

        username = getIntent().getExtras().getString(INTENT_NAME);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Login");
            getSupportActionBar().setElevation(0f);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        loader = new SpotsDialog.Builder()
                .setCancelable(false)
                .setContext(LoginWithEmailActivity.this)
                .build();

        mForgotPasswordBtn = findViewById(R.id.labelForgotPassword);
        mbuttonLogin = findViewById(R.id.buttonLogin);
        edtxUsername = findViewById(R.id.edtxUsername);
        edtxPassword = findViewById(R.id.edtxPassword);
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        preference = new Preference(this);
        edtxUsername.setText(username);
        mbuttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtxUsername.getText().length() == 0) {
                    edtxUsername.setError("field empty");
                    edtxUsername.requestFocus();
                } else if (edtxPassword.getText().length() == 0) {
                    edtxPassword.setError("field empty");
                    edtxPassword.requestFocus();
                } else {
                    viewModel.login(edtxUsername.getText().toString(), edtxPassword.getText().toString());
                }
            }
        });
        mForgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginWithEmailActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
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
