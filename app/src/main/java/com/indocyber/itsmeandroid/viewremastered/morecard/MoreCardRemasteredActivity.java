package com.indocyber.itsmeandroid.viewremastered.morecard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.indocyber.itsmeandroid.view.chat.ChatActivity;
import com.indocyber.itsmeandroid.view.requestincreaselimit.RequestIncreaseLimitActivity;
import com.indocyber.itsmeandroid.viewremastered.billinginfo.BillingInfo;
import com.indocyber.itsmeandroid.viewremastered.blockkartu.BlockKartu;
import com.indocyber.itsmeandroid.viewremastered.editcard.activity.editkartu;

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
    private String customerServicePhone = "021595929";

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
            intent.putExtra(INTENT_ID, data);
            startActivity(intent);
        });

        mLinearHastag.setOnClickListener(v -> {

        });

        mLinearShare.setOnClickListener(v -> {
            showShareOption();
        });

        mLinearPromo.setOnClickListener(v -> {

        });

        mLinearChat.setOnClickListener(v -> {
            Intent i = new Intent(this, ChatActivity.class);
            startActivity(i);
        });

        mLinearCall.setOnClickListener(v -> dialPhoneNumber(customerServicePhone));

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
