package com.indocyber.itsmeandroid.viewremastered.loginandregister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.indocyber.itsmeandroid.R;

import java.util.regex.Pattern;

public class RegisterActivityRemastered extends AppCompatActivity {
    public static EditText etNama, etEmail,etHandphone,etPassword,etRtPass,etIptSecurity;
    public static TextView register;
    public static CardView registerLayout;
    public static ImageView backButton;
    public static CheckBox cbAgreement;
    public static LinearLayout securityLayout;
    public static Spinner spinnerSecuitySelection;
    public static Pattern emailCustom;
    public static Pattern phoneCustom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_remastered);

        etNama = findViewById(R.id.hdr_ipt_name);
        etEmail = findViewById(R.id.hdr_ipt_email);
        etHandphone = findViewById(R.id.hdr_ipt_handphone);
        etPassword = findViewById(R.id.hdr_ipt_password);
        etRtPass = findViewById(R.id.hdr_ipt_retype_pass);
        etIptSecurity = findViewById(R.id.hdr_ipt_answer_security);
        register = findViewById(R.id.btn_next_register);
        spinnerSecuitySelection = findViewById(R.id.sp_security);
        cbAgreement = findViewById(R.id.cb_agree);
        securityLayout = findViewById(R.id.layout_answer);
        registerLayout = findViewById(R.id.lbl_btn_next);
        backButton = findViewById(R.id.imageView5);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String[] arraySpinner = new String[]{
                "Select Question","What is your hobby?", "When is your mom birthday?", "First Pet Name?"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arraySpinner);
        spinnerSecuitySelection.setAdapter(adapter);

        spinnerSecuitySelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spinnerSecuitySelection.getSelectedItem().toString()!= "Select Question"){
                    securityLayout.setVisibility(View.VISIBLE);
                }else{
                    securityLayout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        register.setEnabled(false);
        cbAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(cbAgreement.isChecked()){
                    register.setEnabled(true);
                    registerLayout.setCardBackgroundColor(getResources().getColor(R.color.blue));
                }else{
                    registerLayout.setEnabled(false);
                    registerLayout.setCardBackgroundColor(getResources().getColor(R.color.grey_login));
                }

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registertoSubmit = new Intent(RegisterActivityRemastered.this,SetPinActivityRemastered.class);
                startActivityForResult(registertoSubmit,1);
            }
        });


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
                = Pattern.compile("08"+"[0-9]{9,13}");

        etEmail.addTextChangedListener(emailWatcher);
        etEmail.requestFocus();
        etEmail.setError(null);
        etHandphone.addTextChangedListener(phoneWatcher);

    }

    public TextWatcher emailWatcher = new TextWatcher() {
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
            if(etEmail.getText().toString().trim().length() != 0)
                if (!emailCustom.matcher(etEmail.getText().toString()).matches()){
                    etEmail.setError("anda@kamu.com");
                }else {
                    etEmail.setError(null);
                }

        }
    };

    public TextWatcher phoneWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (etHandphone.getText().toString()!=null){
                if (!phoneCustom.matcher(etHandphone.getText().toString()).matches()){
                    etHandphone.setError("Isi dengan nomor handphone kamu diawali dengan 08(08123456789)");
                }
            }else {
                etHandphone.setError(null);
            }


        }

        @Override
        public void afterTextChanged(Editable s) {




        }
    };
}
