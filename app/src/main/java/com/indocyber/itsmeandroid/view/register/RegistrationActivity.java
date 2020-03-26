package com.indocyber.itsmeandroid.view.register;

import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.SecretQuestion;
import com.indocyber.itsmeandroid.model.User;
import com.indocyber.itsmeandroid.utilities.Preference;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;
import com.indocyber.itsmeandroid.utilities.commonclass.CustomSpinnerAdapter;
import com.indocyber.itsmeandroid.viewremastered.BaseActivity;
import com.indocyber.itsmeandroid.view.home.activity.HomeActivity;
import com.indocyber.itsmeandroid.view.login.LoginWithEmailActivity;
import com.indocyber.itsmeandroid.viewmodel.RegisterViewModel;
import com.indocyber.itsmeandroid.viewmodel.ViewModelFactory;

import java.util.List;

import javax.inject.Inject;

import dmax.dialog.SpotsDialog;

import static com.indocyber.itsmeandroid.utilities.UtilitiesCore.snackBarIconError;

public class RegistrationActivity extends BaseActivity {
    private Spinner mSpnrQuestion;
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
    @Inject
    ViewModelFactory factory;
    private RegisterViewModel viewModel;
    String base64Auth = "";

    @Override
    protected int layoutRes() {
        return R.layout.activity_registration;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutRes());
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
        viewModel = ViewModelProviders.of(this, factory).get(RegisterViewModel.class);
        viewModel.fetchQuestionList();
        pinView = findViewById(R.id.firstPinView);
        buttonRegister = findViewById(R.id.buttonRegister);
        loader = new SpotsDialog.Builder()
                .setCancelable(false)
                .setContext(RegistrationActivity.this)
                .build();
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
            SecretQuestion selectedQuestion = (SecretQuestion) mSpnrQuestion.getSelectedItem();
            newUser.setSecretQuestionId(selectedQuestion.getSecretQuestionId());
            newUser.setSecretAnswer(txtAnswer.getText().toString());
            String key = newUser.getEmail() + ":" + newUser.getPassword();
            base64Auth = Base64.encodeToString(key.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP);
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

        String passwordRegex = "((?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*?])(?=.{8,}))";
        if(!UtilitiesCore.stringRegexMatcher(txtPassword.getText().toString(), passwordRegex)) {
            txtPassword.setError("Field must contain at least 8 characters, 1 uppercase, 1 lowercase, 1 digit, and 1 special char.");
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

//        viewModel.getIsSaved().observe(this, saveSuccess -> {
//            if (saveSuccess)
//        });

        viewModel.getError().observe(this,
                error -> snackBarIconError(RegistrationActivity.this, error));

        viewModel.getQuestionList().observe(this, secretQuestions -> setSpinnerQuestion(secretQuestions));

        viewModel.getDataError().observe(this,
                message -> UtilitiesCore.buildAlertDialog(
                        this,
                        message,
                        R.drawable.ic_invalid,
                        dialogInterface -> {
                            dialogInterface.dismiss();
                            finish();
                        }));

        viewModel.getLoginError().observe(this,
                message -> UtilitiesCore.buildAlertDialog(
                        this,
                        message,
                        R.drawable.ic_invalid,
                        dialogInterface -> {
                            dialogInterface.dismiss();
                            Intent intent = new Intent(this, LoginWithEmailActivity.class);
                            finish();
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                ));

        viewModel.getUserData().observe(this, user -> {
            if (user != null) {
                UtilitiesCore.buildAlertDialog(
                        RegistrationActivity.this,
                        "Registration Success.",
                        R.drawable.ic_approved,
                        dialogInterface -> onRegistrationSuccess(dialogInterface, user)
                );
            }
        });
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

    private void setSpinnerQuestion(List<SecretQuestion> questionList) {
        CustomSpinnerAdapter<SecretQuestion> spinnerAdapter =
                new CustomSpinnerAdapter<>(this, R.layout.spinner_item_text, questionList);
        mSpnrQuestion.setAdapter(spinnerAdapter);
        mSpnrQuestion.setSelection(0);
    }

    private void onRegistrationSuccess(DialogInterface dialog, User user) {
        dialog.dismiss();
        preference.saveUserAuth(base64Auth);
        preference.setLoggedUser(user.getNamaLengkap(), user.getEmail());
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
