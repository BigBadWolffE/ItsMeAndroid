package com.indocyber.itsmeandroid.view.blockconfirmationpin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.chaos.view.PinView;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;
import com.indocyber.itsmeandroid.view.blockcc.activity.BlockCCActivity;
import com.indocyber.itsmeandroid.viewmodel.BlockConfirmationPinViewModel;

import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class BlockConfirmationPinActivity extends AppCompatActivity {

    private ImageCardModel model;

    private PinView firstPinView;
    private BlockConfirmationPinViewModel viewModel;
    private TextView mTxtNameCard;
    private TextView mTxtNumberCard;
    private TextView mTxtExpireCard;
    private ImageView mImageBlock;

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
        mTxtNameCard = findViewById(R.id.txtNameCard);
        mTxtNumberCard = findViewById(R.id.txtNumberCard);
        mTxtExpireCard = findViewById(R.id.txtExpireCard);
        viewModel = ViewModelProviders.of(this).get(BlockConfirmationPinViewModel.class);
        Button mBtnConfirmation = findViewById(R.id.btnConfirmation);
        mBtnConfirmation.setOnClickListener(v -> viewModel.blockCard(model.getId()));
        mImageBlock = findViewById(R.id.imageBlock);

        if (model != null) {
            mTxtNameCard.setText(model.getNameCard());
            mTxtNumberCard.setText(UtilitiesCore.cardNumberSpacing(model.getNumberCard(), 3));
            mTxtExpireCard.setText(model.getExpireCard());
            mImageBlock.setImageResource(model.getImage());
        }
        setPinView();
        observeViewModel();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        formatCreditCard();
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

    private void formatCreditCard(){
        int[] position = {0, 0};
        mImageBlock.getLocationOnScreen(position);

        int paddingLeft = (mImageBlock.getWidth() * 8 / 100);
        int startYAxis = (mImageBlock.getHeight() / 2);

        mTxtNumberCard.setX(position[0] + paddingLeft);
        mTxtNumberCard.setY(startYAxis + getResources().getDimension(R.dimen.spacing_medium));
        mTxtNumberCard.bringToFront();

        TextView holderLabel = findViewById(R.id.lblHolderLabel);
        holderLabel.setX(position[0] + paddingLeft);
        holderLabel.setY(mTxtNumberCard.getY() + mTxtNumberCard.getHeight()
                + getResources().getDimension(R.dimen.spacing_large));

        TextView expiryLabel = findViewById(R.id.lblExpiryLabel);
        expiryLabel.setX(position[0] + paddingLeft
                + mTxtNumberCard.getWidth() - expiryLabel.getWidth());
        expiryLabel.setY(mTxtNumberCard.getY() + mTxtNumberCard.getHeight()
                + getResources().getDimension(R.dimen.spacing_large));

        mTxtNameCard.setX(position[0] + paddingLeft);
        mTxtNameCard.setY(holderLabel.getY() + holderLabel.getHeight()
                + getResources().getDimension(R.dimen.spacing_xsmall));

        mTxtExpireCard.setX(expiryLabel.getX());
        mTxtExpireCard.setY(expiryLabel.getY() + expiryLabel.getHeight() +
                getResources().getDimension(R.dimen.spacing_xsmall));
    }

    private void returnToCardList() {
        Intent intent = new Intent(this, BlockCCActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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
                UtilitiesCore.buildAlertDialog(
                        this,
                        "Card block success!",
                        R.drawable.ic_approved,
                        dialogInterface -> {
                            dialogInterface.dismiss();
                            returnToCardList();
                        });
            }
        });
    }
}
