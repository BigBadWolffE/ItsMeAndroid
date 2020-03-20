package com.indocyber.itsmeandroid.viewremastered.belipulsa.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.Pulsa;
import com.indocyber.itsmeandroid.viewremastered.home.activity.HomeRemastered;

import java.util.Objects;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.INTENT_PARCELABLE;

public class WebviewPembayaranActivity extends AppCompatActivity {

    private ImageButton imageBtnBack;
    private ImageView imgBca;
    private Pulsa data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_pembayaran);

        data = Objects.requireNonNull(getIntent().getExtras()).getParcelable(INTENT_PARCELABLE);

        initView();
        onClick();
    }

    private void initView() {
        imageBtnBack = findViewById(R.id.imageBtnBack);
        imgBca = findViewById(R.id.imgBca);
    }

    private void onClick() {
        imageBtnBack.setOnClickListener(v -> {
            finish();
        });
        imgBca.setOnClickListener(v -> {
            showSuccessDialog();
        });
    }

    private void showSuccessDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_success_beli_pulsa);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtNomor = dialog.findViewById(R.id.txtNomor);
        txtNomor.setText(data.getNomorHp());

        ((ImageButton) dialog.findViewById(R.id.imgBtnClose)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(WebviewPembayaranActivity.this, HomeRemastered.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.show();
        //dialog.getWindow().setAttributes(lp);
    }
}
