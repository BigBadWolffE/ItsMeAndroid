package com.indocyber.itsmeandroid.viewremastered.morecard.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import com.indocyber.itsmeandroid.viewremastered.tagkartu.TagKartu;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
    private Bitmap imgKartu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_card_remastered);
        initView();
        data = Objects.requireNonNull(getIntent().getExtras()).getParcelable(INTENT_ID);
        Bundle extras = getIntent().getExtras();
        imgKartu = extras.getParcelable("BITMAP_KARTU");
        System.out.println("image Kartu ni : " + imgKartu);
//        SaveImage(imgKartu);
        int ALL_PERMISSIONS = 101;
        final String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS);


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
//        mImageCard.setImageResource(R.drawable.img_blank_kartukredit_bca);
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

    private void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File myDir = new File(root + "/shareKartu");
        myDir.mkdirs();

        String fname = "kartu-"+ data.getId() +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
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
        ImageView ic_waShare = view.findViewById(R.id.whatsappShareIcon);
        ImageView ic_twitterShare = view.findViewById(R.id.twitterShareIcon);
        ImageView ic_fbShare = view.findViewById(R.id.FacebookShareIcon);
        ImageView ic_lineShare = view.findViewById(R.id.lineShareIcon);
        ImageView ic_gmailShare = view.findViewById(R.id.gmailShareIcon);
        ImageView ic_emailShare = view.findViewById(R.id.emailShareIcon);
        ImageView ic_telegramShare = view.findViewById(R.id.telegramShareIcon);
        ImageView ic_hangoutShare = view.findViewById(R.id.hangoutShareIcon);
        closeBtn.setOnClickListener(view1 -> dialog.dismiss());
        dialog.show();
        window.setAttributes(wlp);

//        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
////        File myDir = new File(root + "/shareKartu");
////        String FILENAME = "image.png";
//        String PATH = root + "/shareKartu";
//        File f = new File(PATH);
//        Uri yourUri = Uri.fromFile(f);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        imgKartu.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), imgKartu, String.valueOf(data.getId()), null);
        Uri imageUri = Uri.parse(path);
//        Uri uri = Uri.parse(yourUri);

        ic_waShare.setOnClickListener(view1 -> {

//        Uri uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.drawable.img_blank_kartukredit_bca);
//            System.out.println("data : " + data.getImage());
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("image/*");
//            whatsappIntent.setType("te/*");
            whatsappIntent.setPackage("com.whatsapp");
            whatsappIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
//            whatsappIntent.putExtra(Intent.EXTRA_TEXT, data.getNumberCard());
//            whatsappIntent.putExtra(Intent.EXTRA_TEXT, data.getExpireCard());
            try {
                this.startActivity(whatsappIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "Whatsapp has not been installed.", Toast.LENGTH_SHORT).show();
            }
        });

        ic_twitterShare.setOnClickListener(view1 -> {
            Intent twitterIntent = new Intent(Intent.ACTION_SEND);
            twitterIntent.setType("text/plain*");
            twitterIntent.setPackage("com.twitter.android");
            twitterIntent.putExtra(Intent.EXTRA_TEXT, "Share Kartu");
            try {
                this.startActivity(twitterIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "Twitter has not been installed.", Toast.LENGTH_SHORT).show();
            }
        });

        ic_fbShare.setOnClickListener(view1 -> {
            Intent fbIntent = new Intent(Intent.ACTION_SEND);
//            fbIntent.setType("text/plain");
            fbIntent.setPackage("com.facebook.katana");
            fbIntent.setType("image/*");
            fbIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
//            fbIntent.putExtra(Intent.EXTRA_TEXT, "Share Kartu");
            try {
                this.startActivity(fbIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "Facebook has not been installed.", Toast.LENGTH_SHORT).show();
            }
        });

        ic_lineShare.setOnClickListener(view1 -> {
//            System.out.println("masuk line sini");
            Intent lineIntent = new Intent(Intent.ACTION_SEND);
            lineIntent.setType("text/plain");
            lineIntent.setPackage("jp.naver.line.android");
            lineIntent.putExtra(Intent.EXTRA_TEXT, "Share Kartu");
            try {
                this.startActivity(lineIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "Line has not been installed.", Toast.LENGTH_SHORT).show();
            }
        });

        ic_gmailShare.setOnClickListener(view1 -> {
            Intent gmailIntent = new Intent(Intent.ACTION_SEND);
            gmailIntent.setType("text/plain");
            gmailIntent.setPackage("com.google.android.gm");
            gmailIntent.putExtra(Intent.EXTRA_TEXT, "Share Kartu");
            try {
                this.startActivity(gmailIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "Gmail has not been installed.", Toast.LENGTH_SHORT).show();
            }
        });

        ic_emailShare.setOnClickListener(view1 -> {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("text/plain");
            emailIntent.setPackage("com.android.email");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Share Kartu");
            try {
                this.startActivity(emailIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "Email has not been installed.", Toast.LENGTH_SHORT).show();
            }
        });

        ic_telegramShare.setOnClickListener(view1 -> {
            Intent telegramIntent = new Intent(Intent.ACTION_SEND);
            telegramIntent.setType("text/plain");
            telegramIntent.setPackage("org.telegram.messenger");
            telegramIntent.putExtra(Intent.EXTRA_TEXT, "Share Kartu");
            try {
                this.startActivity(telegramIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "Telegram has not been installed.", Toast.LENGTH_SHORT).show();
            }
        });

        ic_hangoutShare.setOnClickListener(view1 -> {
            Intent hangoutIntent = new Intent(Intent.ACTION_SEND);
            hangoutIntent.setType("text/plain");
            hangoutIntent.setPackage("com.google.android.talk");
            hangoutIntent.putExtra(Intent.EXTRA_TEXT, "Share Kartu");
            try {
                this.startActivity(hangoutIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "Hangout has not been installed.", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
