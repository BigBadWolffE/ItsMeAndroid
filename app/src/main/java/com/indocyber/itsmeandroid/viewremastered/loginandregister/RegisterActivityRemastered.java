package com.indocyber.itsmeandroid.viewremastered.loginandregister;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.SecretQuestion;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;
import com.indocyber.itsmeandroid.utilities.commonclass.CustomSpinnerAdapter;
import com.indocyber.itsmeandroid.viewremastered.BaseActivity;
import com.indocyber.itsmeandroid.viewmodel.RegisterViewModel;
import com.indocyber.itsmeandroid.viewmodel.ViewModelFactory;
import com.indocyber.itsmeandroid.viewremastered.loginandregister.helper.SavePref;

import java.util.List;
import java.util.regex.Pattern;

import javax.inject.Inject;

import dmax.dialog.SpotsDialog;

public class RegisterActivityRemastered extends BaseActivity implements View.OnClickListener {
    public static EditText etNama, etEmail,etHandphone,etPassword,etRtPass,etIptSecurity;
    public static TextView register;
    public static CardView registerLayout;
    public static ImageView backButton;
    public static CheckBox cbAgreement;
    public static LinearLayout securityLayout;
    public static Spinner spinnerSecuitySelection;
    public static Pattern emailCustom,nameCustom,passCustom;
    public static Pattern phoneCustom;
    boolean isEmpty;
    public static AlertDialog alertDialog;
    private RegisterViewModel viewModel;
    private AlertDialog loader;
    @Inject
    ViewModelFactory factory;


    @Override
    protected int layoutRes() {
        return R.layout.activity_register_remastered;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutRes());
        loader = new SpotsDialog.Builder()
                .setCancelable(false)
                .setContext(RegisterActivityRemastered.this)
                .build();
        viewModel = ViewModelProviders.of(this, factory).get(RegisterViewModel.class);
        viewModel.fetchQuestionList();
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

//        String[] arraySpinner = new String[]{
//                "Select Question","What is your hobby?", "When is your mom birthday?", "First Pet Name?"
//        };
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arraySpinner);
//        spinnerSecuitySelection.setAdapter(adapter);

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
//        cbAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if(cbAgreement.isChecked()){
//                    register.setEnabled(true);
//                    registerLayout.setCardBackgroundColor(getResources().getColor(R.color.blue));
//                }else{
//                    registerLayout.setEnabled(false);
//                    registerLayout.setCardBackgroundColor(getResources().getColor(R.color.grey_login));
//                }
//
//            }
//        });
        alertDialog = new SpotsDialog.Builder().setCancelable(false).setContext(RegisterActivityRemastered.this).build();
        alertDialog = new SpotsDialog.Builder().setCancelable(false).setContext(RegisterActivityRemastered.this).build();
        register.setOnClickListener(this);

        cbAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cbAgreement.isChecked()){
                    register.setEnabled(true);
                    registerLayout.setCardBackgroundColor(getResources().getColor(R.color.blue));
                }else {
                    register.setEnabled(false);
                    registerLayout.setCardBackgroundColor(getResources().getColor(R.color.grey_main_medium));
                }

            }
        });
        final String Password_Pattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
        final String Password_Pattern1 = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

        passCustom = Pattern.compile(Password_Pattern);
        nameCustom = Pattern.compile("^[a-zA-Z\\s]*$");

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
        etNama.addTextChangedListener(generalWatcher);
        etPassword.addTextChangedListener(passwordWatcher);
        observeViewModel();
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

            if(etEmail.getText().toString().trim().length() != 0)
                if (!emailCustom.matcher(etEmail.getText().toString()).matches()){
                    etEmail.setError("anda@kamu.com");
                }else {
                    etEmail.setError(null);
                }

        }
    };

    public TextWatcher passwordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(etPassword.getText().toString().trim().length() != 0)
                if (!passCustom.matcher(etPassword.getText().toString()).matches()){
                    etPassword.setError("Minimal 1 Huruf Besar 1 Angka dan Spesial Karakter");
                }else {
                    etPassword.setError(null);
                }
        }

        @Override
        public void afterTextChanged(Editable editable) {



        }
    };

    public TextWatcher generalWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(etNama.getText().toString().trim().length() != 0)
                if (!nameCustom.matcher(etNama.getText().toString()).matches()){
                    etNama.setError("Format nama tidak tepat");
                }else {
                    etNama.setError(null);
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

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_next_register:
                regist();
                break;
        }

    }

    public void regist(){
        isEmpty = false;
        if(etNama.getText().toString().trim().length() == 0){
            isEmpty = true;
            Toast.makeText(this,"Nama kosong",Toast.LENGTH_SHORT).show();
        }else if (etEmail.getText().toString().trim().length() == 0){
            isEmpty = true;
            Toast.makeText(this,"Email kosong",Toast.LENGTH_SHORT).show();
        }else if (etHandphone.getText().toString().trim().length() == 0){
            isEmpty = true;
            Toast.makeText(this,"Phone number kosong",Toast.LENGTH_SHORT).show();
        }else if (etPassword.getText().toString().trim().length() == 0){
            isEmpty = true;
            etPassword.setError("Input minimal 8 karakter");
            Toast.makeText(this,"Input minimal 8 karakter",Toast.LENGTH_SHORT).show();
        }else if (!etRtPass.getText().toString().equals(etPassword.getText().toString())){
            isEmpty = true;
            etRtPass.setError("Kata Sandi Tidak Sama");
        } else if (spinnerSecuitySelection.getSelectedItem().equals("Select Question")){
            ((TextView)spinnerSecuitySelection.getSelectedView()).setError("Wajib Pilih");
            isEmpty = true;
            Toast.makeText(this,"Pilih pertanyaan sekuritas anda!",Toast.LENGTH_SHORT).show();
        }else if (etIptSecurity.getText().toString().trim().length() == 0){
            isEmpty = true;
            etIptSecurity.setError("Tidak boleh kosong");
            Toast.makeText(this,"Tidak boleh kosong",Toast.LENGTH_SHORT).show();
        }new Handler().postDelayed(() -> {
            if(!isEmpty){
                alertDialog.show();
                SavePref.saveName(this,etNama.getText().toString());
                SavePref.saveEmail(this,etEmail.getText().toString());
                SavePref.savePhone(this,etHandphone.getText().toString());
                SavePref.savePass(this,etRtPass.getText().toString());
                SecretQuestion selectedQuestion = (SecretQuestion) spinnerSecuitySelection.getSelectedItem();
                SavePref.saveSecretQuestion(this, selectedQuestion.getSecretQuestionId());
                SavePref.saveSecretAnswer(this,etIptSecurity.getText().toString());
                new Handler().postDelayed(() -> {
                    alertDialog.dismiss();
                    Intent intent = new Intent(RegisterActivityRemastered.this, SetPinActivityRemastered.class);
                    startActivity(intent);
                }, 800);
            }
        }, 200);
    }

    private void observeViewModel() {
        viewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                loader.show();
            } else {
                loader.dismiss();
            }
        });

//        viewModel.getIsSaved().observe(this, saveSuccess -> {
//            if (saveSuccess)
//        });

        viewModel.getQuestionList().observe(this,
                secretQuestions -> setSpinnerQuestion(secretQuestions));

        viewModel.getDataError().observe(this,
                message -> UtilitiesCore.buildAlertDialog(
                        this,
                        message,
                        R.drawable.ic_invalid,
                        dialogInterface -> {
                            dialogInterface.dismiss();
                            finish();
                        }));

    }

    private void setSpinnerQuestion(List<SecretQuestion> questionList) {
        CustomSpinnerAdapter<SecretQuestion> spinnerAdapter =
                new CustomSpinnerAdapter<>(this, R.layout.spinner_item_text, questionList);
        spinnerSecuitySelection.setAdapter(spinnerAdapter);
        spinnerSecuitySelection.setSelection(0);
    }
}
