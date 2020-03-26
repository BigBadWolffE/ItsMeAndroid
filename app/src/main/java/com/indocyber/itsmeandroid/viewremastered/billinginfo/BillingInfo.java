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
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;

import java.util.Objects;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.INTENT_ID;

public class BillingInfo extends AppCompatActivity {

    private String mNumber;
    private String mHolderName;
    private String mExpiryDate;
    private ImageView cardImage;
    private ImageCardModel data;
    private final static String[] MONTH_LIST = {"Januari", "Februari", "Maret", "April",
            "Mei", "Juni", "Juli", "Agustus", "September",
            "Oktober", "November", "Desember"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_info);
        Bundle extras = getIntent().getExtras();
        data = Objects.requireNonNull(getIntent().getExtras()).getParcelable(INTENT_ID);
        mNumber = data.getNumberCard();
        mHolderName = data.getNameCard();
        mExpiryDate = data.getExpireCard();
        bindBillingInfo();
        cardImage = findViewById(R.id.imgCreditCard);
        cardImage.setImageResource(data.getImage());

        createToolbar();
    }

    private void bindBillingInfo() {
        String monthDue = data.getExpireCard().substring(0, 2);
        String yearDue = data.getExpireCard().substring(3, 5);
        String expiry = "";
        try {
            int month = Integer.valueOf(monthDue);
            expiry = "1 " + MONTH_LIST[month - 1] + " 20" + yearDue;
        } catch (Exception e) {
            expiry = "1 Januari 2025";
        }

        TextView status = findViewById(R.id.cardStatusInfo);
        status.setText(data.isBlockedCard() ? "Diblokir" : "Aktif");
        TextView currentBilling = findViewById(R.id.cardBillingInfo);
        currentBilling.setText("15.000.000");
        TextView minimumPayment = findViewById(R.id.cardMinPayment);
        minimumPayment.setText("1.500.000");
        TextView printDate = findViewById(R.id.cardIssueDate);
        printDate.setText("1 Januari 2020");
        TextView dueDate = findViewById(R.id.cardDueDate);
        dueDate.setText(expiry);
        TextView creditLimit = findViewById(R.id.cardLimit);
        creditLimit.setText("20.000.000");
        TextView credit = findViewById(R.id.cardBalance);
        credit.setText("5.000.000");
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
        int iconDimension = (int) getResources().getDimension(R.dimen._15sdp);
        Drawable resizedBackButton =
                UtilitiesCore.resizeDrawable(backButton, this, iconDimension , iconDimension);
        textView.setText("Billing Info");
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
        mCardExpiry.setText(mExpiryDate);
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
