package com.indocyber.itsmeandroid.viewremastered.loginandregister.PopUp;

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
import com.indocyber.itsmeandroid.viewremastered.loginandregister.LoginActivityRemastered;

public class PopUpRegisterSucceedRemastered {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static TextView throwLogin;
    public static ImageView closeDialog,imageBerhasil;
    public static AlertDialog dialog;


    public static void showDialog(final Activity activity) {

        ViewGroup viewGroup = activity.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(activity).
                inflate(R.layout.popup_succeed_registration, viewGroup,
                        false);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        dialog = builder.create();
        imageBerhasil = dialogView.findViewById(R.id.imageView6);
        throwLogin = dialogView.findViewById(R.id.btn_next_login);
        closeDialog = dialogView.findViewById(R.id.ivClose);

        throwLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent register = new Intent(activity, LoginActivityRemastered.class);
                activity.startActivityForResult(register,1);
            }
        });
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent register = new Intent(activity, LoginActivityRemastered.class);
                register.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(register);
            }
        });
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

}
