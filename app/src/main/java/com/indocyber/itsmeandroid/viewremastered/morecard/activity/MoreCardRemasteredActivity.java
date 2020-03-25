package com.indocyber.itsmeandroid.viewremastered.morecard.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.utilities.GlobalVariabel;
import com.indocyber.itsmeandroid.view.chat.ChatActivity;
import com.indocyber.itsmeandroid.view.requestincreaselimit.RequestIncreaseLimitActivity;
import com.indocyber.itsmeandroid.viewremastered.billinginfo.BillingInfo;
import com.indocyber.itsmeandroid.viewremastered.blockkartu.BlockKartu;
import com.indocyber.itsmeandroid.viewremastered.editcard.activity.editkartu;
import com.indocyber.itsmeandroid.viewremastered.home.activity.HomeRemastered;
import com.indocyber.itsmeandroid.viewremastered.tagkartu.TagKartu;

import java.io.Serializable;
import java.util.Objects;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.CARD_TYPE;
import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.CREDIT_CARD;
import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.FRAGMENT_TYPE;
import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.INTENT_ID;
import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.MEMBER_CARD;
import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.PERSONAL_CARD;

public class MoreCardRemasteredActivity extends AppCompatActivity {

    private LinearLayout mLinearBilling;
    private LinearLayout mLinearEdit;
    private LinearLayout mLinearBlokir;
    private LinearLayout mLinearTambahLimit;
    private LinearLayout mLinearHastag;
    private LinearLayout mLinearShare;
    private LinearLayout mLinearPromo;
    private LinearLayout mLinearChat;
    private LinearLayout mLinearCall;
    private LinearLayout linearEditMember;
    private LinearLayout linearShareMember;
    private LinearLayout linearBlockirMember;
    private LinearLayout linearMemberCard;
    private LinearLayout linearCreditCard;
    private TextView mTtxtNumberCard;
    private TextView txtTitle;
    private ImageButton mImageBtnBack;
    private ImageCardModel data;
    private String customerServicePhone = "021595929";
    private int cardType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_card_remastered);

        data = Objects.requireNonNull(getIntent().getExtras()).getParcelable(INTENT_ID);
        cardType = Objects.requireNonNull(getIntent().getExtras()).getInt(CARD_TYPE,1);

        initView();
        initCheckCardType();

        onClick();

    }

    private void initCheckCardType(){
        if (cardType == CREDIT_CARD){
            linearMemberCard.setVisibility(View.GONE);
            linearCreditCard.setVisibility(View.VISIBLE);
            setDataCreditCard();
        }else if (cardType == MEMBER_CARD){
            linearMemberCard.setVisibility(View.VISIBLE);
            linearCreditCard.setVisibility(View.GONE);
            setDataMemberCard();
        }else if (cardType == PERSONAL_CARD){
            linearMemberCard.setVisibility(View.VISIBLE);
            linearCreditCard.setVisibility(View.GONE);
            setDataPersonalCard();
        }
    }


    private void initView() {
        mLinearBilling = findViewById(R.id.linearBilling);
        mLinearEdit = findViewById(R.id.linearEdit);
        mLinearBlokir = findViewById(R.id.linearBlokir);
        mLinearTambahLimit = findViewById(R.id.linearTambahLimit);
        mLinearHastag = findViewById(R.id.linearHastag);
        mLinearShare = findViewById(R.id.linearShare);
        mLinearPromo = findViewById(R.id.linearPromo);
        mLinearChat = findViewById(R.id.linearChat);
        mLinearCall = findViewById(R.id.linearCall);
        mTtxtNumberCard = findViewById(R.id.txtNumberCard);
        mImageBtnBack = findViewById(R.id.imageBtnBack);
        linearEditMember = findViewById(R.id.linearEditMember);
        linearShareMember = findViewById(R.id.linearShareMember);
        linearBlockirMember = findViewById(R.id.linearBlockirMember);
        linearMemberCard = findViewById(R.id.linearMemberCard);
        linearCreditCard = findViewById(R.id.linearCreditCard);
        txtTitle = findViewById(R.id.txtTitle);
    }



    @SuppressLint("SetTextI18n")
    private void setDataMemberCard(){
        txtTitle.setText("Kartu Member");
        if (data != null) {
            mTtxtNumberCard.setText("Member Card");
        }
    }
    @SuppressLint("SetTextI18n")
    private void setDataPersonalCard(){
        txtTitle.setText("Kartu Member");
        if (data != null) {
            mTtxtNumberCard.setText("Personal Card");
        }
    }

    @SuppressLint("SetTextI18n")
    private void setDataCreditCard(){
        txtTitle.setText("Kartu Kredit");

        if (data != null) {
            if (data.getNumberCard() != null) {
                onCardNumberChange(data.getNumberCard());
            }

        }
    }
    private void onClick() {
        mLinearBilling.setOnClickListener(v -> {
            Intent intent = new Intent(this, BillingInfo.class);
            intent.putExtra(INTENT_ID, data);
            startActivity(intent);
        });

        mLinearEdit.setOnClickListener(v -> {
            Intent intent = new Intent(this, editkartu.class);
            intent.putExtra(INTENT_ID, data);
            startActivity(intent);
        });

        mLinearBlokir.setOnClickListener(v -> {
            Intent intent = new Intent(this, BlockKartu.class);
            intent.putExtra(INTENT_ID, data);
            startActivity(intent);
        });

        mLinearTambahLimit.setOnClickListener(v -> {
            Intent intent = new Intent(this, RequestIncreaseLimitActivity.class);
            intent.putExtra("parentCode", GlobalVariabel.CARD_MENU);
            intent.putExtra(INTENT_ID, data);
            startActivity(intent);
        });

        mLinearHastag.setOnClickListener(v -> {
            Intent intent = new Intent(this, TagKartu.class);
            intent.putExtra("cardId", data.getId());
            startActivity(intent);
        });

        mLinearShare.setOnClickListener(v -> {
            showShareOption();
        });

        mLinearPromo.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeRemastered.class);
            intent.putExtra(FRAGMENT_TYPE, 1);
            startActivity(intent);
        });

        mLinearChat.setOnClickListener(v -> {
            Intent i = new Intent(this, ChatActivity.class);
            startActivity(i);
        });

        mLinearCall.setOnClickListener(v -> dialPhoneNumber(customerServicePhone));

        mImageBtnBack.setOnClickListener(v -> {
            finish();
        });


        //Button personal dan member
        linearEditMember.setOnClickListener(v -> {

        });

        linearShareMember.setOnClickListener(v -> {

        });

        linearBlockirMember.setOnClickListener(v -> {

        });

    }
    private void onCardNumberChange(final String text){
        StringBuilder paddedText = new StringBuilder(text + "");
        for(int i = 4; i <= 11; i++){
            paddedText.replace(i,i,"X");
        }

        String updatedText = paddedText.substring(0, 4) + "   " + paddedText.substring(4, 8) + "   "
                + paddedText.substring(8, 12) + "   " + paddedText.substring(12, 16);


        mTtxtNumberCard.setText(updatedText);
    }

    private void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void showShareOption() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.ShareDialogTheme);
        View view = LayoutInflater.from(this).inflate(R.layout.share_option, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        ImageView closeBtn = view.findViewById(R.id.closeOption);
        closeBtn.setOnClickListener(view1 -> dialog.dismiss());
        dialog.show();
        window.setAttributes(wlp);
    }
}
