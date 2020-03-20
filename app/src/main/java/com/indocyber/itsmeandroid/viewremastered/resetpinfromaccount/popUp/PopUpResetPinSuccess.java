package com.indocyber.itsmeandroid.viewremastered.resetpinfromaccount.popUp;

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
import com.indocyber.itsmeandroid.viewremastered.akun.AkunRemasteredFragment;
import com.indocyber.itsmeandroid.viewremastered.home.fragment.HomeRemasteredFragment;
import com.indocyber.itsmeandroid.viewremastered.loginandregister.LoginActivityRemastered;

public class PopUpResetPinSuccess {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static TextView resetText;
    public static ImageView closeDialog;
    public static AlertDialog dialog;


    public static void showDialog(final Activity activity) {

        ViewGroup viewGroup = activity.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(activity).
                inflate(R.layout.layout_success_reset, viewGroup,
                        false);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        dialog = builder.create();
        resetText = dialogView.findViewById(R.id.textView6);
        resetText.setText("Reset Pin Anda");
        closeDialog = dialogView.findViewById(R.id.ivClose);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent register = new Intent(activity, AkunRemasteredFragment.class);
                activity.startActivityForResult(register,1);
            }
        });
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
}
