package com.indocyber.itsmeandroid.viewremastered.blockallcard.activity;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.davidmiguel.numberkeyboard.NumberKeyboard;
import com.davidmiguel.numberkeyboard.NumberKeyboardListener;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ListBlockAllCard;
import com.indocyber.itsmeandroid.viewremastered.BaseActivity;
import com.indocyber.itsmeandroid.viewmodel.PinActivityViewModel;
import com.indocyber.itsmeandroid.viewmodel.ViewModelFactory;
import com.indocyber.itsmeandroid.viewremastered.home.activity.HomeRemastered;

import java.util.Objects;

import javax.inject.Inject;

import dmax.dialog.SpotsDialog;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.INTENT_PARCELABLE;

public class SetPinBlockCardActivity extends BaseActivity implements NumberKeyboardListener {

    private PinView firstPinView;
    private AlertDialog loader;
    private ImageView backButton;
    private TextView submitPin;
    private CardView lblSubmit;
    private ListBlockAllCard listBlockAllCard;
    private PinActivityViewModel viewModel;


    @Inject
    ViewModelFactory factory;

    @Override
    protected int layoutRes() {
        return R.layout.activity_set_pin_block_card;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_set_pin_block_card);
        viewModel = ViewModelProviders.of(this, factory).get(PinActivityViewModel.class);

        loader = new SpotsDialog.Builder()
                .setCancelable(false)
                .setContext(SetPinBlockCardActivity.this)
                .build();
        NumberKeyboard numberKeyboard = findViewById(R.id.numberKeyboardOtp);
        numberKeyboard.setListener(this);

        listBlockAllCard = getIntent().getExtras().getParcelable(INTENT_PARCELABLE);

        Toast.makeText(this, listBlockAllCard.getList().get(0).getId()+"", Toast.LENGTH_SHORT).show();
        initView();
        setPinView();
        onClick();
        hideKeyboard();
        initViewModel();
    }

    private void initView() {
        firstPinView = findViewById(R.id.firstPinView);
        backButton = findViewById(R.id.imageView5);
        submitPin = findViewById(R.id.btn_pin_register);
        lblSubmit = findViewById(R.id.lbl_btn_validation);

    }

    private void onClick() {
        backButton.setOnClickListener(v -> {
            finish();
        });
        submitPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewModel.blockAllCard(listBlockAllCard);
            }
        });
    }

    private void initViewModel() {
        viewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                loader.show();
            } else {
                loader.dismiss();
            }
        });

        viewModel.getError().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error + "", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getIsDone().observe(this, isDone -> {
            if (isDone) {
                showSuccessDialog();

            }
        });
    }


    private void showSuccessDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_success_block_all_card);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        ((ImageButton) dialog.findViewById(R.id.imgBtnClose)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(SetPinBlockCardActivity.this, HomeRemastered.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.show();
        //dialog.getWindow().setAttributes(lp);
    }

    private void setPinView() {
        firstPinView.setTextColor(ResourcesCompat.getColor(getResources(), R.color.black, getTheme()));
        firstPinView.setItemCount(6);
        firstPinView.setItemHeight(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_size));
        firstPinView.setItemWidth(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_size));
        firstPinView.setItemRadius(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_radius));
        firstPinView.setItemSpacing(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_spacing));
        firstPinView.setLineWidth(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_line_width));
        firstPinView.setAnimationEnable(true);// start animation when adding text
        firstPinView.setCursorVisible(false);
        firstPinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (before == 6) {
                    submitPin.setEnabled(true);
                    lblSubmit.setCardBackgroundColor(getResources().getColor(R.color.blue));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        firstPinView.setCursorWidth(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_cursor_width));

        firstPinView.setItemBackgroundColor(Color.WHITE);

        firstPinView.setHideLineWhenFilled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }


    @Override
    public void onLeftAuxButtonClicked() {

    }

    @Override
    public void onNumberClicked(int number) {
        firstPinView.setText(Objects.requireNonNull(firstPinView.getText()).append(String.valueOf(number)));

    }

    @Override
    public void onRightAuxButtonClicked() {
        if (!Objects.requireNonNull(firstPinView.getText()).toString().equals("")) {
            StringBuilder build = new StringBuilder(firstPinView.getText().toString());
            build.deleteCharAt(firstPinView.getText().toString().length() - 1);
            firstPinView.setText(build.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
