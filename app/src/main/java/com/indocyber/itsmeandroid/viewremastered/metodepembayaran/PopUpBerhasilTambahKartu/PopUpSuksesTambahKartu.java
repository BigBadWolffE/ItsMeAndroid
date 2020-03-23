package com.indocyber.itsmeandroid.viewremastered.metodepembayaran.PopUpBerhasilTambahKartu;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.viewremastered.metodepembayaran.MetodePembayaranActivityRemastered;
import com.indocyber.itsmeandroid.viewremastered.resetpinfromaccount.LupaPinActivityRemastered;

public class PopUpSuksesTambahKartu {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static TextView resetText;
    public static ImageView closeDialog;
    public static AlertDialog dialog;

    public static void showDialog(final Activity activity) {

        ViewGroup viewGroup = activity.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(activity).
                inflate(R.layout.popup_kirim_email_berhasil, viewGroup,
                        false);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        dialog = builder.create();
        resetText = dialogView.findViewById(R.id.textView6);
        resetText.setText("Kartu Berhasil Ditambahkan");
        closeDialog = dialogView.findViewById(R.id.ivClose);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(activity, MetodePembayaranActivityRemastered.class);
                activity.startActivityForResult(intent,92);

            }
        });
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
}
