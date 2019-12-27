package com.indocyber.itsmeandroid.view.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.view.register.RegistrationActivity;

public class LoginOptionActivity extends AppCompatActivity {
    private Button mbtnLogin;
    private LinearLayout mlinearRegister;
    public static String INTENT_NAME = "INTENT_NAME";
    private EditText edtxUsername;

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
        edtxUsername = findViewById(R.id.edtxUsername);

        mbtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtxUsername.getText().length()> 0 ) {
                    Intent intent = new Intent(LoginOptionActivity.this, LoginWithEmailActivity.class);
                    intent.putExtra(INTENT_NAME, edtxUsername.getText().toString());
                    startActivity(intent);
                }else {
                    edtxUsername.setError("Field Empty");
                    edtxUsername.requestFocus();
                }

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
