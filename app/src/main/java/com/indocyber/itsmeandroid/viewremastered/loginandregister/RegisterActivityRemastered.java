package com.indocyber.itsmeandroid.viewremastered.loginandregister;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.indocyber.itsmeandroid.R;

public class RegisterActivityRemastered extends AppCompatActivity {
    public static EditText nama, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_remastered);
    }
}
