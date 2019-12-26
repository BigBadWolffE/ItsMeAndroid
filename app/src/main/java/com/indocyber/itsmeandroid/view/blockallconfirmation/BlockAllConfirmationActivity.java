package com.indocyber.itsmeandroid.view.blockallconfirmation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.chaos.view.PinView;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.view.blockconfirmationpin.BlockConfirmationPinActivity;
import com.indocyber.itsmeandroid.view.home.activity.HomeActivity;
import com.indocyber.itsmeandroid.viewmodel.BlockAllConfirmationViewModel;

import dmax.dialog.SpotsDialog;

public class BlockAllConfirmationActivity extends AppCompatActivity {

    private BlockAllConfirmationViewModel viewModel;
    private AlertDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_all_confirmation);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0f);
            getSupportActionBar().setTitle("Confirmation Pin");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        viewModel = ViewModelProviders.of(this).get(BlockAllConfirmationViewModel.class);
        Button mBtnConfirmation = findViewById(R.id.btnConfirmation);
        mBtnConfirmation.setOnClickListener(v -> {
            viewModel.blockAllCard();
        });
        setPinView();
        observeViewModel();
    }

    private void setPinView() {
        PinView firstPinView = findViewById(R.id.firstPinView);
        firstPinView.setTextColor(
                ResourcesCompat.getColor(getResources(), R.color.black, getTheme()));

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

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        firstPinView.setCursorWidth(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_cursor_width));

        firstPinView.setItemBackgroundColor(Color.WHITE);

        firstPinView.setHideLineWhenFilled(false);
    }

    private void showCustomDialog() {
        final Dialog dialog = new Dialog(BlockAllConfirmationActivity.this);
        dialog.setContentView(R.layout.dialog_succes_block_card);
        dialog.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        final Button btnClose = (Button) dialog.findViewById(R.id.btnClose);

        btnClose.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void observeViewModel() {
        viewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                loader = new SpotsDialog.Builder()
                        .setCancelable(false)
                        .setContext(BlockAllConfirmationActivity.this)
                        .build();
                loader.show();
            } else {
                loader.dismiss();
            }
        });

        viewModel.getIsSuccess().observe(this, isSuccess -> {
            if (isSuccess) {
                showCustomDialog();
            }
        });
    }
}
