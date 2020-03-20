package com.indocyber.itsmeandroid.viewremastered.notification.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;
import com.indocyber.itsmeandroid.view.billingdetail.BillingDetailActivity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailNotificationBillingActivity extends AppCompatActivity {
    @BindView(R.id.lblDate)
    TextView mLblDate;
    @BindView(R.id.lblBody)
    TextView mLblBody;
    @BindView(R.id.lblTitleBilling)
    TextView mlblTitleBilling;
    @BindView(R.id.lblFile)
    TextView mLblFile;
    @BindView(R.id.imageBtnBack)
    ImageView mBtnBack;
    String attachmentName;

    private EditText mAttachmentPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_notification_billing);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String title = extras.getString("billingTitle");

            mlblTitleBilling.setText(title);

            String body = extras.getString("billingBody");

            mLblBody.setText(body);

            String date = extras.getString("billingDate");

            mLblDate.setText(date);

            attachmentName = extras.getString("billingAttachmentName");

            mLblFile.setText(attachmentName);

            String attachmentPassword = extras.getString("billingAttachmentPassword");
            if(attachmentPassword != null) {
                if(!attachmentPassword.equals("") && !attachmentPassword.isEmpty()){
                    passwordPrompt(attachmentPassword);
                }
            }
        }
    }

    @OnClick(R.id.lblFile)
    void openFilePdf() {
        renderPdf(attachmentName);
    }

    @OnClick(R.id.imageBtnBack)
    void back(){
        finish();
    }

    private void renderPdf(String fileName) {
        try {
            File pdfFile = new File(getApplicationContext().getCacheDir(), fileName);
            UtilitiesCore.copyFileToCache(getApplicationContext(), pdfFile, fileName);
            openPdfIntent(pdfFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean openPdfIntent(File file) {
        Intent intent;
        if (Build.VERSION.SDK_INT < 24) {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        } else {
            Intent target = new Intent(Intent.ACTION_VIEW);
            Uri pdfURI = FileProvider.getUriForFile(DetailNotificationBillingActivity.this,
                    getApplicationContext().getPackageName() + ".provider", file);
            target.setDataAndType(pdfURI, "application/pdf");
            target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent = Intent.createChooser(target, "Open File");
        }
        try {
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
                return true;
            } else {
                Toast.makeText(getApplicationContext(), "No Application found to open the pdf",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void passwordPrompt(String attachmentPassword) {
        View view = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.dialog_open_notif_billing, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(view);
        AlertDialog dialog = dialogBuilder.create();
        mAttachmentPassword = view.findViewById(R.id.txtPasswordBilling);
        TextView passwordSubmitButton = view.findViewById(R.id.lblBtnOpen);
        TextView cancelButton = view.findViewById(R.id.lblBtnCancel);

        passwordSubmitButton.setOnClickListener(view1 -> passwordSubmit(attachmentPassword, dialog));
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }

    private void passwordSubmit(String attachmentPassword, AlertDialog dialog) {
        if (!mAttachmentPassword.getText().toString().equals(attachmentPassword)) {
            dialog.dismiss();
            UtilitiesCore.buildAlertDialog(
                    this,
                    "Failed to open document, invalid password.",
                    R.drawable.ic_invalid,
                    dialogInterface -> {
                        dialogInterface.dismiss();
                        finish();
                    }
            );
        }
        dialog.dismiss();
    }

}
