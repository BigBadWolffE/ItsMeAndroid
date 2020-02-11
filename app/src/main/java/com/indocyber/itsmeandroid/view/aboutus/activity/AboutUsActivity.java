package com.indocyber.itsmeandroid.view.aboutus.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.indocyber.itsmeandroid.R;

public class AboutUsActivity extends AppCompatActivity {

    private ArrayAdapter<String> subjectAdapter;
    private String[] mSubjectValue = {"Pilih Subject", "Customer Service", "Back Office", "Developer"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("About Us");
            getSupportActionBar().setElevation(0f);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        EditText mYourName = findViewById(R.id.txtAboutUsName);
        EditText mEmail = findViewById(R.id.txtAboutUsEmail);
        Spinner mSubjectSpinner;
        mSubjectSpinner = findViewById(R.id.spinnerAboutUsSubject);
        EditText mMessage = findViewById(R.id.txtAboutUsMessage);

        subjectAdapter
                = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mSubjectValue);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSubjectSpinner.setAdapter(subjectAdapter);

    }
}
