package com.indocyber.itsmeandroid.view.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.User;
import com.indocyber.itsmeandroid.utilities.Preference;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;
import com.indocyber.itsmeandroid.view.addmembership.AddMembershipActivity;
import com.indocyber.itsmeandroid.view.blockconfirmationpin.BlockConfirmationPinActivity;
import com.indocyber.itsmeandroid.view.home.activity.HomeActivity;
import com.indocyber.itsmeandroid.view.membershipsecuritycode.MembershipSecurityCodeActivity;
import com.indocyber.itsmeandroid.view.otp.OtpActivity;
import com.indocyber.itsmeandroid.viewmodel.RegisterViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;

import static com.indocyber.itsmeandroid.utilities.UtilitiesCore.snackBarIconError;

public class RegistrationActivity extends AppCompatActivity {
    private String idQuestion;
    private String idAnswer;
    private String nameQuestion;
    private String nameAnswer;
    private Spinner mSpnrQuestion;
    //private Spinner mSpnrAnswer;
    private Button buttonRegister;

    private EditText txtFullname;
    private EditText txtEmail;
    private EditText txtPhonenumber;
    private EditText txtPassword;
    private EditText txtRetypePassword;
    private EditText txtAnswer;
    private PinView pinView;
    private CheckBox checkboxRegister;
    private AlertDialog loader;
    private Preference preference;
    private RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("New Registration");
            getSupportActionBar().setElevation(0f);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        preference = new Preference(this);
        mSpnrQuestion = findViewById(R.id.spnrQuestion);
        txtFullname = findViewById(R.id.txtFullname);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhonenumber = findViewById(R.id.txtPhonenumber);
        txtPassword = findViewById(R.id.txtPassword);
        txtRetypePassword = findViewById(R.id.txtRetypePassword);
        txtAnswer = findViewById(R.id.txtAnswer);
        checkboxRegister = findViewById(R.id.checkboxRegister);
        viewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        pinView = findViewById(R.id.firstPinView);
        buttonRegister = findViewById(R.id.buttonRegister);
        loader = new SpotsDialog.Builder()
                .setCancelable(false)
                .setContext(RegistrationActivity.this)
                .build();
        setSpinnerQuestion();
        setPinView();
        checkRegister();
        observeViewModel();
    }

    private void checkRegister() {
        buttonRegister.setOnClickListener(v -> {
            if (!validateForm()) {
                return;
            }
            User newUser = new User();
            newUser.setNamaLengkap(txtFullname.getText().toString());
            newUser.setEmail(txtEmail.getText().toString());
            newUser.setNoTelp(txtPhonenumber.getText().toString());
            newUser.setPassword(txtPassword.getText().toString());
            newUser.setPin(pinView.getText().toString());
            newUser.setHobby(txtAnswer.getText().toString());
            viewModel.register(newUser);
        });
    }

    private boolean validateForm() {
        if (txtFullname.getText().toString().isEmpty()) {
            txtFullname.setError("Field cannot be empty");
            txtFullname.requestFocus();
            return false;
        }

        if (txtEmail.getText().toString().isEmpty()) {
            txtEmail.setError("Field cannot be empty");
            txtEmail.requestFocus();
            return false;
        }

        if (!UtilitiesCore.checkEmailFormat(txtEmail.getText().toString())) {
            txtEmail.setError("Invalid email format");
            txtEmail.requestFocus();
            return false;
        }

        if (txtPhonenumber.getText().toString().isEmpty()) {
            txtPhonenumber.setError("Field cannot be empty");
            txtPhonenumber.requestFocus();
            return false;
        }

        if (txtAnswer.getText().toString().isEmpty()){
            txtAnswer.setError("Field cannot be empty");
            txtAnswer.requestFocus();
            return false;
        }

        if(txtPassword.getText().toString().isEmpty()) {
            txtPassword.setError("Field cannot be empty");
            txtPassword.requestFocus();
            return false;
        }

        if(!txtRetypePassword.getText().toString().equals(txtPassword.getText().toString())) {
            txtRetypePassword.setError("Password does not match!");
            txtRetypePassword.requestFocus();
            return false;
        }

        if (pinView.getText().toString().length() < 6) {
            snackBarIconError(RegistrationActivity.this, "Pin must contain 6 digits.");
            return false;
        }

        if (!checkboxRegister.isChecked()) {
            UtilitiesCore.buildAlertDialog(
                    RegistrationActivity.this,
                    "Please read and indicate your acceptance of the site's Terms of Service",
                    R.drawable.ic_invalid,
                    null
            );
            return false;
        }

        return true;
    }

    private void observeViewModel() {
        viewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                loader.show();
            } else {
                loader.dismiss();
            }
        });

        viewModel.getIsSaved().observe(this, saveSuccess -> {
            if (saveSuccess) UtilitiesCore.buildAlertDialog(
                    RegistrationActivity.this,
                    "Registration Success.",
                    R.drawable.ic_approved,
                    dialogInterface -> onLoginSuccess(dialogInterface)
            );
        });

        viewModel.getError().observe(this,
                error -> snackBarIconError(RegistrationActivity.this, error));
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

    private void setPinView() {
        pinView.setTextColor(
                ResourcesCompat.getColor(getResources(), R.color.black, getTheme()));

        pinView.setItemCount(6);
        pinView.setItemHeight(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_size));
        pinView.setItemWidth(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_size));
        pinView.setItemRadius(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_radius));
        pinView.setItemSpacing(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_spacing));
        pinView.setLineWidth(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_line_width));
        pinView.setAnimationEnable(true);// start animation when adding text
        pinView.setCursorVisible(false);

        pinView.setCursorWidth(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_cursor_width));

        pinView.setItemBackgroundColor(Color.WHITE);

        pinView.setHideLineWhenFilled(false);
    }

    private void setSpinnerQuestion() {
        try {
            List<HashMap<String, String>> listSpinner = new ArrayList<HashMap<String, String>>();

            /*String[] idSpinner = {"1", "2", "3"};
            String[] nameSpinner = {"Donasi Kemanusiaan", "Donasi Pendidikan", "Donasi Kesehatan"};*/

            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("id", "1");
            hm.put("level_name", "What are your hobby?");
            listSpinner.add(hm);
            /*for (int i = 0; i < 3; i++) {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("id", idSpinner[i]);
                hm.put("level_name", nameSpinner[i]);
                listSpinner.add(hm);
            }*/
            String[] from = {"id", "level_name"};
            int[] to = {R.id.id_spinner, R.id.nama_spinner};
            SimpleAdapter adapter = new SimpleAdapter(this, listSpinner, R.layout.layout_spinner, from, to);
            mSpnrQuestion.setAdapter(adapter);
            mSpnrQuestion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long idLong) {
                    HashMap<String, String> hm = (HashMap<String, String>) parent.getAdapter().getItem(position);
                    String id = hm.get("id");
                    String level_name = hm.get("level_name");
                   /* typeId = id;
                    typeName = level_name;*/
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void showCustomDialog() {
//        final Dialog dialog = new Dialog(RegistrationActivity.this);
//        dialog.setContentView(R.layout.dialog_succes_registration);
//        dialog.setCancelable(false);
//
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(dialog.getWindow().getAttributes());
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//
//        final Button btnClose = dialog.findViewById(R.id.btnClose);
//
//        btnClose.setOnClickListener(v -> {
//            loader.dismiss();
//            finish();
//            preference.setLoginFirstTime(true);
//            preference.setLoggedUser(txtFullname.getText().toString(),
//                    txtEmail.getText().toString());
//            Intent i = new Intent(RegistrationActivity.this, HomeActivity.class);
//            startActivity(i);
//        });
//
//        dialog.show();
//        dialog.getWindow().setAttributes(lp);
//    }

    private void onLoginSuccess(DialogInterface dialog) {
        dialog.dismiss();
        finish();
        preference.setLoginFirstTime(true);
        preference.setLoggedUser(txtFullname.getText().toString(),
                txtEmail.getText().toString());
        Intent i = new Intent(RegistrationActivity.this, HomeActivity.class);
        startActivity(i);
    }
}
