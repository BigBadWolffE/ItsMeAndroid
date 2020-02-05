package com.indocyber.itsmeandroid.view.blockconfirmationpin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.chaos.view.PinView;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.viewmodel.BlockConfirmationPinViewModel;

import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class BlockConfirmationPinActivity extends AppCompatActivity {

    private ImageCardModel model;

    private PinView firstPinView;
    private BlockConfirmationPinViewModel viewModel;

    public static String INTENT_BLOCK_CONFIRMATION = "INTENT_BLOCK_CONFIRMATION";
    private AlertDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_confirmation_pin);
        model = getIntent().getParcelableExtra(INTENT_BLOCK_CONFIRMATION);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0f);
            getSupportActionBar().setTitle("Confirmation Pin");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        firstPinView = findViewById(R.id.firstPinView);
        TextView mTxtNameCard = findViewById(R.id.txtNameCard);
        TextView mTxtNumberCard = findViewById(R.id.txtNumberCard);
        TextView mTxtExpireCard = findViewById(R.id.txtExpireCard);
        viewModel = ViewModelProviders.of(this).get(BlockConfirmationPinViewModel.class);
        Button mBtnConfirmation = findViewById(R.id.btnConfirmation);
        mBtnConfirmation.setOnClickListener(v -> viewModel.blockCard(model.getId()));
        ImageView mImageBlock = findViewById(R.id.imageBlock);

        if (model != null) {
            mTxtNameCard.setText(model.getNameCard());
            mTxtNumberCard.setText(model.getNumberCard());
            mTxtExpireCard.setText(model.getExpireCard());
            imageGlide(this, mImageBlock, model.getImage());
        }
        setPinView();
        observeViewModel();
    }

    private void setPinView() {
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

    private void imageGlide(Context context, ImageView imageView, int data) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.bg_gradient_soft)
                .error(R.drawable.bg_gradient_soft)
                .priority(Priority.HIGH);

        Glide.with(context)
                .load(data)
                .apply(options)
                .into(imageView);
    }

    private void showCustomDialog() {
        final Dialog dialog = new Dialog(BlockConfirmationPinActivity.this);
        dialog.setContentView(R.layout.dialog_succes_block_card);
        dialog.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        final Button btnClose = dialog.findViewById(R.id.btnClose);
        final TextView txtNumberCard = dialog.findViewById(R.id.txtNumberCard);

        txtNumberCard.setText(model.getNumberCard());
        btnClose.setOnClickListener(v -> finish());
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void observeViewModel() {
        viewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                loader = new SpotsDialog.Builder()
                        .setCancelable(false)
                        .setContext(BlockConfirmationPinActivity.this)
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
