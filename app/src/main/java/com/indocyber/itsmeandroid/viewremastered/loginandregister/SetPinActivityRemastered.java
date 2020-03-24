package com.indocyber.itsmeandroid.viewremastered.loginandregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaos.view.PinView;
import com.davidmiguel.numberkeyboard.NumberKeyboard;
import com.davidmiguel.numberkeyboard.NumberKeyboardListener;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.utilities.GlobalVariabel;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;
import com.indocyber.itsmeandroid.view.addmembership.AddMembershipActivity;
import com.indocyber.itsmeandroid.viewremastered.home.activity.HomeRemastered;
import com.indocyber.itsmeandroid.viewremastered.loginandregister.PopUp.PopUpRegisterSucceedRemastered;
import com.indocyber.itsmeandroid.viewremastered.loginandregister.helper.RegistrationModel;
import com.indocyber.itsmeandroid.viewremastered.loginandregister.helper.RegistrationRequest;

import java.util.Objects;
import java.util.Set;

import dmax.dialog.SpotsDialog;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.INTENT_ID;


public class SetPinActivityRemastered extends AppCompatActivity implements NumberKeyboardListener {
    public static PinView firstPinView;
    public static AlertDialog alertDialog;
    private androidx.appcompat.app.AlertDialog customAlert;
    public static ImageView backButton;
    public static TextView submitPin;
    public static CardView lblSubmit;
    int parentCode = -1;
    String cardNumber = "";
    ImageCardModel data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_remastered);
        parentCode = getIntent().getIntExtra("parentCode", -1);
        if (parentCode >= 0) {
            data = Objects.requireNonNull(getIntent().getExtras()).getParcelable(INTENT_ID);
        }
        if (data != null) {
            cardNumber = data.getNumberCard();
        }
        firstPinView = findViewById(R.id.firstPinView);
        hideKeyboard();
        setPinView();
        alertDialog = new SpotsDialog.Builder().setCancelable(false).setContext(SetPinActivityRemastered.this).build();

        NumberKeyboard numberKeyboard = findViewById(R.id.numberKeyboardOtp);
        numberKeyboard.setListener(this);

        backButton = findViewById(R.id.imageView5);
        backButton = findViewById(R.id.imageView5);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        submitPin = findViewById(R.id.btn_pin_register);
        lblSubmit = findViewById(R.id.lbl_btn_validation);
        submitPin.setEnabled(false);
        alertDialog = new SpotsDialog.Builder().setCancelable(false).setContext(SetPinActivityRemastered.this).build();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        submitPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (parentCode == GlobalVariabel.TAMBAH_LIMIT_ACTIVITY) {
//                    showSuccessDialog(
//                            R.drawable.ic_img_emotion_smile,
//                            "Pengajuan Limit\nKartu Kredit Anda",
//                            padCardNumber(cardNumber, 3) + "\nBerhasil",
//                            dialogInterface -> {
//                                Intent intent = new Intent(SetPinActivityRemastered.this, HomeRemastered.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(intent);
//                            });
//                } else if (parentCode == GlobalVariabel.BLOCK_KARTU) {
//                    showSuccessDialog(
//                            R.drawable.ic_img_emotion_smile,
//                            "Kartu Kredit Anda",
//                            padCardNumber(cardNumber, 3) + "\nBerhasil diblokir",
//                            dialogInterface -> {
//                                Intent intent = new Intent(SetPinActivityRemastered.this, HomeRemastered.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(intent);
//                            });
//                } else if (parentCode == GlobalVariabel.EDIT_KARTU) {
//                    showSuccessDialog(
//                            R.drawable.ic_img_emotion_smile,
//                            "Perubahan Kartu Kredit Anda",
//                            padCardNumber(cardNumber, 3) + "\nBerhasil",
//                            dialogInterface -> {
//                                finish();
//                            });
//                } else if (parentCode == GlobalVariabel.TAMBAH_PERSONAL) {
//                    showSuccessDialog(
//                            R.drawable.ic_img_emotion_smile,
//                            "Penambahan Kartu Personal Anda",
//                            "Berhasil",
//                            dialogInterface -> {
//                                Intent intent = new Intent(SetPinActivityRemastered.this, HomeRemastered.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(intent);
//                            });
//                } else if (parentCode == GlobalVariabel.TAMBAH_MEMBER) {
//                    showSuccessDialog(
//                            R.drawable.ic_img_emotion_smile,
//                            "Penambahan Kartu Member Anda",
//                            "Berhasil",
//                            dialogInterface -> {
//                                Intent intent = new Intent(SetPinActivityRemastered.this, HomeRemastered.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(intent);
//                            });
//                } else {
                    new Handler().postDelayed(() -> {
                        alertDialog.show();
                        new Handler().postDelayed(() -> {
                            alertDialog.dismiss();
//                        Intent intent = new Intent(SetPinActivityRemastered.this, PopUpRegisterSucceedRemastered.class);
//                        startActivity(intent);
//                            PopUpRegisterSucceedRemastered.showDialog(SetPinActivityRemastered.this);
                            RegistrationModel registrationModel = new RegistrationModel();
                            RegistrationRequest.postRegistrationData(SetPinActivityRemastered.this,registrationModel);

                        }, 800);
                    }, 200);
                }
        });
    }

    private void setPinView() {
        firstPinView.setTextColor(ResourcesCompat.getColor(getResources(), R.color.black, getTheme()));
        firstPinView.setItemCount(6);
        firstPinView.setItemHeight(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_size));
        firstPinView.setItemWidth(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_size));
        firstPinView.setItemRadius(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_radius));
        firstPinView.setItemSpacing(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_spacing));
        firstPinView.setLineWidth(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_line_width));
        firstPinView.setAnimationEnable(true);// start animation when adding text
        firstPinView.setCursorVisible(false);
        firstPinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (before == 6) {
                    submitPin.setEnabled(true);
                    lblSubmit.setCardBackgroundColor(getResources().getColor(R.color.blue));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        firstPinView.setCursorWidth(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_cursor_width));

        firstPinView.setItemBackgroundColor(Color.WHITE);

        firstPinView.setHideLineWhenFilled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }


    @Override
    public void onLeftAuxButtonClicked() {

    }

    @Override
    public void onNumberClicked(int number) {
        firstPinView.setText(Objects.requireNonNull(firstPinView.getText()).append(String.valueOf(number)));
        /*pinview.setValue(pinview.getValue() + number);
        edit_query.setText(edit_query.getText().append(String.valueOf(number)));
        Toast.makeText(this, pinview.getValue()+"", Toast.LENGTH_SHORT).show();*/


    }

    @Override
    public void onRightAuxButtonClicked() {
        if (!Objects.requireNonNull(firstPinView.getText()).toString().equals("")) {
            StringBuilder build = new StringBuilder(firstPinView.getText().toString());
            build.deleteCharAt(firstPinView.getText().toString().length() - 1);
            firstPinView.setText(build.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

//    private void showSuccessDialog(int icon, String smallText, String bigText, DialogInterface.OnDismissListener onDismiss) {
//        androidx.appcompat.app.AlertDialog.Builder builder =
//                new androidx.appcompat.app.AlertDialog.Builder(SetPinActivityRemastered.this);
//        View view = LayoutInflater.from(this).inflate(R.layout.alert_dialog_success, null);
//        ImageView alertIcon = view.findViewById(R.id.imgAlertIcon);
//        alertIcon.setImageResource(icon);
//        TextView txtSmallText = view.findViewById(R.id.txtSmallText);
//        txtSmallText.setText(smallText);
//        TextView txtBigText = view.findViewById(R.id.txtBigText);
//        txtBigText.setText(bigText);
//        ImageView close = view.findViewById(R.id.closeAlert);
//        builder.setView(view);
//        builder.setOnDismissListener(onDismiss);
//        customAlert = builder.create();
//        close.setOnClickListener(view1 -> customAlert.dismiss());
//        customAlert.show();
//    }

//    private void showErrorDialog(int icon, String smallText, String bigText, String errorText, DialogInterface.OnDismissListener onDismiss) {
//        androidx.appcompat.app.AlertDialog.Builder builder =
//                new androidx.appcompat.app.AlertDialog.Builder(SetPinActivityRemastered.this);
//        View view = LayoutInflater.from(this).inflate(R.layout.alert_dialog_invalid, null);
//        ImageView alertIcon = view.findViewById(R.id.imgAlertIcon);
//        alertIcon.setImageResource(icon);
//        TextView txtSmallText = view.findViewById(R.id.txtSmallText);
//        txtSmallText.setText(smallText);
//        TextView txtBigText = view.findViewById(R.id.txtBigText);
//        txtBigText.setText(bigText);
//        TextView txtErrorText = view.findViewById(R.id.txtErrorText);
//        txtBigText.setText(errorText);
//        ImageView close = view.findViewById(R.id.closeAlert);
//        builder.setView(view);
//        builder.setOnDismissListener(onDismiss);
//        customAlert = builder.create();
//        close.setOnClickListener(view1 -> customAlert.dismiss());
//        customAlert.show();
//    }
//
//    private String padCardNumber(String number, int pad) {
//        StringBuilder padding = new StringBuilder();
//        for(int i = 0; i < pad; i++){
//            padding.append(" ");
//        }
//
//        String paddedText = number + "";
//        return paddedText.substring(0, 4) + padding + paddedText.substring(4, 8) + padding
//                + paddedText.substring(8, 12) + padding + paddedText.substring(12, 16);
//    }

    public static void alertWrong (final Context activity ){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setMessage("Data Anda Telah Terdaftar!");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Dismiss",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
