package com.indocyber.itsmeandroid.viewremastered.morecard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ImageCardModel;

import java.util.Objects;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.INTENT_ID;

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
    private TextView mTtxtNumberCard;
    private ImageButton mImageBtnBack;
    private ImageCardModel data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_card_remastered);
        initView();

        data = Objects.requireNonNull(getIntent().getExtras()).getParcelable(INTENT_ID);

        onClick();
        setData();
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
    }

    private void setData(){
        if (data != null) {
            onCardNumberChange(data.getNumberCard());

        }
    }
    private void onClick() {
        mLinearBilling.setOnClickListener(v -> {

        });

        mLinearEdit.setOnClickListener(v -> {

        });

        mLinearBlokir.setOnClickListener(v -> {

        });

        mLinearTambahLimit.setOnClickListener(v -> {

        });

        mLinearHastag.setOnClickListener(v -> {

        });

        mLinearShare.setOnClickListener(v -> {

        });

        mLinearPromo.setOnClickListener(v -> {

        });

        mLinearChat.setOnClickListener(v -> {

        });

        mLinearCall.setOnClickListener(v ->{

        });

        mImageBtnBack.setOnClickListener(v -> {
            finish();
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
}
