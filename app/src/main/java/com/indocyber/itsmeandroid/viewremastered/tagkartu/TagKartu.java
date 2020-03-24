package com.indocyber.itsmeandroid.viewremastered.tagkartu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;
import com.indocyber.itsmeandroid.view.BaseActivity;
import com.indocyber.itsmeandroid.viewmodel.TagKartuViewModel;
import com.indocyber.itsmeandroid.viewmodel.ViewModelFactory;
import com.indocyber.itsmeandroid.viewremastered.loginandregister.SetPinActivityRemastered;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dmax.dialog.SpotsDialog;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.INTENT_ID;

public class TagKartu extends BaseActivity {

    private TextView mCardNumber;
    private EditText txtEditTag;
    private TextView savedTag;
    private ImageView mCardImage;
    private String cardNumber;
    private int cardImage;
    private String cardHolder;
    private String cardBillingAddress;
    private String expiryDate;
    private TagKartuViewModel viewModel;
    private List<String> tags = new ArrayList<>();
    private AlertDialog loader;
    private int cardId;
    @Inject
    ViewModelFactory factory;
//    ImageCardModel data;

    @Override
    protected int layoutRes() {
        return R.layout.activity_tag_kartu;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutRes());
        Bundle extras = getIntent().getExtras();
        cardId = extras.getInt("cardId");
//        data = Objects.requireNonNull(getIntent().getExtras()).getParcelable(INTENT_ID);
        viewModel = ViewModelProviders.of(this, factory).get(TagKartuViewModel.class);
        viewModel.getCardById(cardId);
        loader = new SpotsDialog.Builder().setCancelable(false).setContext(TagKartu.this).build();
        formatCreditCard();
        initializeButton();
        initializeCardNumber();
        createToolbar();
        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.getCardData().observe(this, data -> {
            cardNumber = data.getNumberCard();
            cardHolder = data.getNameCard();
            cardImage = data.getImage();
            expiryDate = data.getExpireCard();
            cardBillingAddress = data.getBillingAddress();
            mCardImage.setImageResource(cardImage);
            txtEditTag.setText(data.getNewTagList());
            savedTag.setText(data.getNewTagList());
        });

        viewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading) loader.show();
            else loader.dismiss();
        });

        viewModel.getIsDone().observe(this, isDone -> {
            savedTag.setText(txtEditTag.getText().toString());
        });
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
        ScrollView scrollView = findViewById(R.id.scrollContainer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView textView = findViewById(R.id.toolbar_text);
        VectorDrawable backButton = (VectorDrawable) getDrawable(R.drawable.ic_ico_arrow_back);
        int iconDimension = (int) getResources().getDimension(R.dimen._20sdp);
        Drawable resizedBackButton =
                UtilitiesCore.resizeDrawable(backButton, this, iconDimension , iconDimension);
        textView.setText("Tag Kartu Kredit");
        textView.setAllCaps(false);
        toolbar.setNavigationIcon(resizedBackButton);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0f);
        scrollView.setPadding(0, appbar.getHeight(), 0, 0);
    }


    private void initializeCardNumber() {
        mCardImage = findViewById(R.id.imgCreditCard);
        mCardNumber = findViewById(R.id.txtCardNumber);
        txtEditTag = findViewById(R.id.txtEditTag);
        savedTag = findViewById(R.id.cardTags);
    }

    private void initializeButton() {
        Button saveTag;
        saveTag = findViewById(R.id.btnSubmitTag);
        saveTag.setOnClickListener(view -> submit());
    }

    private boolean formIsValid(){
        if (txtEditTag.getText().toString().trim().length() < 1) return false;

        return true;
    }

    private void submit() {
        if (formIsValid()) viewModel.saveTag(cardId, txtEditTag.getText().toString());
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        formatCreditCard();
    }

    private void onCardNumberChange(final CharSequence text){
        StringBuilder paddedText = new StringBuilder(text + "");
        for(int i = paddedText.length(); i < 16; i++){
            paddedText.append("X");
        }

        String updatedText = paddedText.substring(0, 4) + "   " + paddedText.substring(4, 8) + "   "
                + paddedText.substring(8, 12) + "   " + paddedText.substring(12, 16);

        mCardNumber.setText(updatedText);
    }

    private void formatCreditCard(){
        ImageView cardImage = findViewById(R.id.imgCreditCard);
        int[] position = {0, 0};
        cardImage.getLocationOnScreen(position);

        int paddingLeft = (cardImage.getWidth() * 8 / 100);
        int startYAxis = (cardImage.getHeight() / 2);

        TextView mCardNumber = findViewById(R.id.lblCcNumber);
        mCardNumber.setText(padCardNumber(cardNumber, 3));
        mCardNumber.setX(paddingLeft);
        mCardNumber.setY(startYAxis + getResources().getDimension(R.dimen.spacing_medium));
        mCardNumber.bringToFront();
        TextView mCardHolder = findViewById(R.id.lblCardHolder);
        mCardHolder.setText(cardHolder);
        mCardHolder.setX(paddingLeft);
        mCardHolder.setY(cardImage.getHeight() * 80 / 100);

        TextView mCardExpiry = findViewById(R.id.lblExpiry);
        mCardExpiry.setText(expiryDate);
        mCardExpiry.setX(cardImage.getWidth() / 2);
        mCardExpiry.setY(cardImage.getHeight() * 70 / 100);
    }

    private String padCardNumber(String number, int pad) {
        if (number == null) return "";
        if (number.length() < 16) return number;
        StringBuilder padding = new StringBuilder();
        for(int i = 0; i < pad; i++){
            padding.append(" ");
        }

        String paddedText = number + "";
        return paddedText.substring(0, 4) + padding + paddedText.substring(4, 8) + padding
                + paddedText.substring(8, 12) + padding + paddedText.substring(12, 16);
    }
}
