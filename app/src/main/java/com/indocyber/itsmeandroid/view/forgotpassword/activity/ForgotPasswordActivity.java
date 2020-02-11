package com.indocyber.itsmeandroid.view.forgotpassword.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.indocyber.itsmeandroid.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Forgot Password");
            getSupportActionBar().setElevation(0f);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Button btnForgotPass = findViewById(R.id.btnForgotPassword);
//        EditText txtEmailForgot = findViewById(R.id.txtEmailForgotPass);

        btnForgotPass.setOnClickListener(view ->
                Toast.makeText(getApplicationContext(), "Forgot Password Clicked", Toast.LENGTH_SHORT)
                        .show());
    }
}
