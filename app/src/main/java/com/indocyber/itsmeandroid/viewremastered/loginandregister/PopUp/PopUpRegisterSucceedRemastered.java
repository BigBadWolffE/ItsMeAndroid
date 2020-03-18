package com.indocyber.itsmeandroid.viewremastered.loginandregister.PopUp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;

import com.indocyber.itsmeandroid.R;

public class PopUpRegisterSucceedRemastered {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static TextView throwLogin;
    public static ImageView closeDialog;
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
        throwLogin = dialogView.findViewById(R.id.btn_next_login);
        closeDialog = dialogView.findViewById(R.id.ivClose);
    }

}
