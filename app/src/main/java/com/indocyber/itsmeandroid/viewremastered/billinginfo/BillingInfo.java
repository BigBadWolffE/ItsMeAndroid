package com.indocyber.itsmeandroid.viewremastered.billinginfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;

public class BillingInfo extends AppCompatActivity {

    private String mNumber;
    private String mHolderName;
    private String mExpiryDate;
    private ImageView cardImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_info);
        Bundle extras = getIntent().getExtras();
        mNumber = extras.getString("cardNumber");
        mHolderName = extras.getString("cardHolder");
        mExpiryDate = extras.getString("expiryDate");

        cardImage = findViewById(R.id.imgCreditCard);
        cardImage.setImageResource(R.drawable.img_blank_kartukredit_bca);

        createToolbar();
    }

    private  void createToolbar() {
//        if (getSupportActionBar() != null) {
//            View view = LayoutInflater.from(this).inflate(R.layout.action_bar, null);
//            TextView title = view.findViewById(R.id.titleText);
//            ImageView actionBack = view.findViewById(R.id.imgback);
//            actionBack.setOnClickListener(view1 -> finish());
//            title.setText("Tambah Kartu Kredit");
//            getSupportActionBar().setCustomView(view);
//            getSupportActionBar().setElevation(0f);
//        }
        AppBarLayout appbar = findViewById(R.id.actionBar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView textView = findViewById(R.id.toolbar_text);
        VectorDrawable backButton = (VectorDrawable) getDrawable(R.drawable.ic_ico_arrow_back);
        int iconDimension = (int) getResources().getDimension(R.dimen._20sdp);
        Drawable resizedBackButton =
                UtilitiesCore.resizeDrawable(backButton, this, iconDimension , iconDimension);
        textView.setText("Tambah Limit");
        textView.setAllCaps(false);
        toolbar.setNavigationIcon(resizedBackButton);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0f);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        formatCreditCard();
    }

    private void formatCreditCard(){
//        ImageView creditCard = findViewById(R.id.imgCreditCard);
        int[] position = {0, 0};
        cardImage.getLocationOnScreen(position);

        int paddingLeft = (cardImage.getWidth() * 8 / 100);
        int startYAxis = (cardImage.getHeight() / 2);

        TextView mCardNumber = findViewById(R.id.lblCcNumber);
        mCardNumber.setText(padCardNumber(mNumber, 3));
        mCardNumber.setX(paddingLeft);
        mCardNumber.setY(startYAxis + getResources().getDimension(R.dimen.spacing_medium));
        mCardNumber.bringToFront();
        TextView mCardHolder = findViewById(R.id.lblCardHolder);
        mCardHolder.setText(mHolderName);
        mCardHolder.setX(paddingLeft);
        mCardHolder.setY(cardImage.getHeight() * 80 / 100);

        TextView mCardExpiry = findViewById(R.id.lblExpiry);
        mCardExpiry.setX(cardImage.getWidth() / 2);
        mCardExpiry.setY(cardImage.getHeight() * 70 / 100);
    }

    private String padCardNumber(String number, int pad) {
        StringBuilder padding = new StringBuilder();
        for(int i = 0; i < pad; i++){
            padding.append(" ");
        }

        String paddedText = number + "";
        return paddedText.substring(0, 4) + padding + paddedText.substring(4, 8) + padding
                + paddedText.substring(8, 12) + padding + paddedText.substring(12, 16);
    }
}
