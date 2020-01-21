package com.indocyber.itsmeandroid.view.forgotpassword.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.indocyber.itsmeandroid.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    private Button btnForgotPass;
    private EditText txtEmailForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Forgot Password");
            getSupportActionBar().setElevation(0f);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnForgotPass = findViewById(R.id.btnForgotPassword);
        txtEmailForgot = findViewById(R.id.txtEmailForgotPass);

        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Forgot Password Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
