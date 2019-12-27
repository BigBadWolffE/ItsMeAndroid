package com.indocyber.itsmeandroid.view.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.utilities.Preference;
import com.indocyber.itsmeandroid.view.home.activity.HomeActivity;

import static com.indocyber.itsmeandroid.utilities.UtilitiesCore.snackBarIconError;
import static com.indocyber.itsmeandroid.view.login.LoginOptionActivity.INTENT_NAME;

public class LoginWithEmailActivity extends AppCompatActivity {
    private Button mbuttonLogin;
    private String username;

    private EditText edtxUsername;
    private EditText edtxPassword;

    private Preference preference;

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

        mbuttonLogin = findViewById(R.id.buttonLogin);
        edtxUsername = findViewById(R.id.edtxUsername);
        edtxPassword = findViewById(R.id.edtxPassword);

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
                } else if (edtxUsername.getText().toString().equals("indocyber@indocyber.com") && edtxPassword.getText().toString().equals("indocyber")) {
                    preference.setLoginFirstTime(true);
                    Intent intent = new Intent(LoginWithEmailActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    snackBarIconError(LoginWithEmailActivity.this,"Invalid username or password");
                }

        }
    });
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
}
