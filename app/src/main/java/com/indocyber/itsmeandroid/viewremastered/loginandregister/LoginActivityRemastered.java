package com.indocyber.itsmeandroid.viewremastered.loginandregister;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.indocyber.itsmeandroid.R;

import java.util.regex.Pattern;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.INTENT_ID;

public class LoginActivityRemastered extends AppCompatActivity {

    private TextView btnNext,registerText;
    private EditText inputUserName;
    private Pattern emailCustom;
    private Pattern phoneCustom;
    private CardView lblLgn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_remastered);

        btnNext = findViewById(R.id.btn_login);
        registerText = findViewById(R.id.textView2);
        inputUserName = findViewById(R.id.ipt_username);
        lblLgn =findViewById(R.id.layout_btn_next);
        btnNext.setEnabled(false);

        emailCustom = Pattern.compile(
                "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,500}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,500}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,500}" +
                        ")+"
        );

        phoneCustom = Pattern.compile("08"+"[0-9]{9,13}");

        inputUserName.addTextChangedListener(inputUsernameWatcher);
        inputUserName.requestFocus();
        inputUserName.setError(null);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent formLogin = new Intent(LoginActivityRemastered.this,LoginAuthActivityRemastered.class);
                formLogin.putExtra(INTENT_ID,inputUserName.getText().toString().trim());
                startActivity(formLogin);
            }
        });

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(LoginActivityRemastered.this,RegisterActivityRemastered.class);
                startActivityForResult(register,1);
                finish();
            }
        });


    }
    public TextWatcher inputUsernameWatcher = new TextWatcher() {
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
            if(inputUserName.getText().toString().trim().length() != 0){
                btnNext.setEnabled(true);
                lblLgn.setCardBackgroundColor(getResources().getColor(R.color.blue));
                btnNext.setTextColor(getResources().getColor(R.color.colorwhite));

                if (!emailCustom.matcher(inputUserName.getText().toString()).matches()){
                    inputUserName.setError("Email atau nomor telpon");
                } else {
                    inputUserName.setError(null);
                }

            }


        }
    };

    @Override
    public void onBackPressed() {
        AlertDialog.Builder ab = new AlertDialog.Builder(LoginActivityRemastered.this);
        ab.setTitle("Its Me!");
        ab.setMessage("Apakah anda yakin untuk keluar?");
        ab.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //if you want to kill app . from other then your main avtivity.(Launcher)
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);

                //if you want to finish just current activity

                LoginActivityRemastered.this.finish();
            }
        });
        ab.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        ab.show();
    }


}
