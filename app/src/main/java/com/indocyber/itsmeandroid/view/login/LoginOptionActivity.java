package com.indocyber.itsmeandroid.view.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.view.register.RegistrationActivity;

public class LoginOptionActivity extends AppCompatActivity {
    private Button mbtnLogin;
    private LinearLayout mlinearRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_option);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().hide();
        }

        mbtnLogin = findViewById(R.id.btnLogin);
        mlinearRegister = findViewById(R.id.linearRegister);

        mbtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginOptionActivity.this, LoginWithEmailActivity.class);
                startActivity(intent);
            }
        });

        mlinearRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginOptionActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });


    }
}
