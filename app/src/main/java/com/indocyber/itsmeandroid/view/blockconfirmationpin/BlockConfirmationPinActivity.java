package com.indocyber.itsmeandroid.view.blockconfirmationpin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
import com.indocyber.itsmeandroid.view.blockcc.activity.BlockCCActivity;
import com.indocyber.itsmeandroid.viewmodel.BlockConfirmationPinViewModel;

import dmax.dialog.SpotsDialog;

public class BlockConfirmationPinActivity extends AppCompatActivity {

    private TextView mTxtNumberCard;
    private TextView mTxtNameCard;
    private TextView mTxtExpireCard;
    private Button mBtnConfirmation;
    private ImageView mImageBlock;
    private ImageCardModel model;

    private AlertDialog alertDialog;
    private PinView firstPinView;
    private BlockConfirmationPinViewModel viewModel;

    public static String INTENT_BLOCK_CONFIRMATION = "INTENT_BLOCK_CONFIRMATION";
    public static String INTENT_BLOCK_POSITION = "INTENT_BLOCK_POSITION";
    public static int REQUEST_BLOCK_DELETE = 101;
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
        mBtnConfirmation = findViewById(R.id.btnConfirmation);
        mBtnConfirmation.setOnClickListener(v -> {
            viewModel.blockCard(model.getId());
        });

        mImageBlock = findViewById(R.id.imageBlock);

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
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        final Button btnClose = (Button) dialog.findViewById(R.id.btnClose);
        final TextView txtNumberCard = (TextView) dialog.findViewById(R.id.txtNumberCard);

        txtNumberCard.setText(model.getNumberCard());


        btnClose.setOnClickListener(v -> {
            finish();
        });


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
