package com.indocyber.itsmeandroid.view.billingdetail;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;

import java.io.File;
import java.io.IOException;

public class BillingDetailActivity extends AppCompatActivity {

    private EditText mAttachmentPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_detail);
        Bundle extras = getIntent().getExtras();

        String title = extras.getString("billingTitle");
        createToolbar(title);
        TextView billingTitle = findViewById(R.id.txtBillingTitle);
        billingTitle.setText(title);

        String body = extras.getString("billingBody");
        TextView billingBody = findViewById(R.id.txtBillingMessage);
        billingBody.setText(body);

        String date = extras.getString("billingDate");
        TextView billingDate = findViewById(R.id.txtBillingDate);
        billingDate.setText(date);

        String attachmentName = extras.getString("billingAttachmentName");
        TextView billingAttachmentName = findViewById(R.id.txtBillingAttachment);
        billingAttachmentName.setText(attachmentName);

        String attachmentPassword = extras.getString("billingAttachmentPassword");
        if(!attachmentPassword.equals("") && !attachmentPassword.isEmpty()){
            passwordPrompt(attachmentPassword);
        }

        renderPdf(attachmentName);
    }

    private  void createToolbar(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setElevation(0f);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void renderPdf(String fileName) {
        PDFView pdfView = findViewById(R.id.pdfView);
        try {
            File pdfFile = new File(getApplicationContext().getCacheDir(), fileName);
            UtilitiesCore.copyFileToCache(getApplicationContext(), pdfFile, fileName);
            pdfView.fromFile(pdfFile)
                    .pages(0)
                    .onTap(e -> openPdfIntent(pdfFile))
                    .load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean openPdfIntent(File file) {
        Intent intent = null;
        if (Build.VERSION.SDK_INT < 24) {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        } else {
            Intent target = new Intent(Intent.ACTION_VIEW);
            Uri pdfURI = FileProvider.getUriForFile(BillingDetailActivity.this,
                    getApplicationContext().getPackageName() + ".provider", file);
            target.setDataAndType(pdfURI, "application/pdf");
            target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent = Intent.createChooser(target, "Open File");
        }
        try {
            if (intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
                return true;
            }
            else {
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
                .inflate(R.layout.attachment_password_dialog, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(view);
        AlertDialog dialog = dialogBuilder.create();
        mAttachmentPassword = view.findViewById(R.id.txtAttachmentPassword);
        Button passwordSubmitButton = view.findViewById(R.id.btnPasswordSubmit);
        passwordSubmitButton
                .setOnClickListener(view1 -> passwordSubmit(attachmentPassword, dialog));
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
        return;
    }
}
