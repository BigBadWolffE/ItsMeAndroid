package com.indocyber.itsmeandroid.viewremastered.akun;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.services.network.Api;
import com.indocyber.itsmeandroid.utilities.EmailValidator;
import com.indocyber.itsmeandroid.utilities.Preference;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;
import com.indocyber.itsmeandroid.viewremastered.BaseActivity;
import com.indocyber.itsmeandroid.viewmodel.EditProfileViewModel;
import com.indocyber.itsmeandroid.viewmodel.ViewModelFactory;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class EditProfileActivity extends BaseActivity {
    @BindView(R.id.txtEmail)
    EditText mTxtEmail;
    @BindView(R.id.layout_input_email)
    TextInputLayout mLayoutEmail;
    @BindView(R.id.btnKonfirmasi)
    Button mBtnSubmit;
    @BindView(R.id.imageTakeFotoProfile)
    ImageView mBtnTakePhoto;
    @BindView(R.id.imageFotoProfile)
    CircleImageView imgPhotoProfile;
    @BindView(R.id.lblProfileName)
    TextView profileName;
    @BindView(R.id.txtPhonenumber)
    EditText phone;
    private EditProfileViewModel viewModel;
    private android.app.AlertDialog dialog;
    @Inject
    ViewModelFactory factory;
    private Preference preference;
//    private Preference preference;

    protected static final int CAMERA_REQUEST = 0;
    protected static final int GALLERY_PICTURE = 1;
    private String extension = "";
    private String profileImage = "";


    @Override
    protected int layoutRes() {
        return R.layout.activity_edit_profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutRes());
        ButterKnife.bind(this);
        preference = new Preference(this);
        viewModel = ViewModelProviders.of(this, factory).get(EditProfileViewModel.class);
        viewModel.fetchProfile(preference.getUserAuth());
        dialog = new SpotsDialog.Builder()
                .setCancelable(false)
                .setContext(EditProfileActivity.this)
                .build();
        profileName.setText(preference.getLoggedUserFullname());
        mTxtEmail.setText(preference.getLoggedUserEmail());
        UtilitiesCore.loadImageFromUri(imgPhotoProfile, this, Api.PROFILE_IMAGE,
                preference.getUserAuth(), preference.getMetaData());
        textWatcher(mLayoutEmail);
        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading) dialog.show();
            else dialog.dismiss();
        });

        viewModel.getUserData().observe(this, user -> {
            phone.setText(user.getNoTelp());
        });

        viewModel.getIsDone().observe(this, isDone -> {
            if (isDone) {
                preference.setLoggedUser(preference.getLoggedUserFullname(),
                        mTxtEmail.getText().toString());
                preference.setMetaData(System.currentTimeMillis() + "");
                UtilitiesCore.buildAlertDialog(
                        this,
                        "Profile berhasil DiUbah.",
                        R.drawable.ic_approved,
                        dialogInterface -> {
                            dialogInterface.dismiss();
                            finish();
                        });
            }
        });
    }

    private boolean validasi() {
        if (mTxtEmail.getText().toString().equals("")) {
            mTxtEmail.setError("Email Harus diisi");
            return false;
        } else if (!EmailValidator.validate(mTxtEmail.getText().toString())) {
            mTxtEmail.setError("Email Yang DiMasukan Tidak Valid");
            return false;
        }

        return true;
    }

    private void textWatcher(final TextInputLayout textInputLayout) {
        textInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textInputLayout.setErrorEnabled(false);
                textInputLayout.setError("");
            }
        });
    }

    private void startTakePhotoDialog() {
        final AlertDialog.Builder myAlertDialog =
                new AlertDialog.Builder(this, R.style.Theme_MaterialComponents_Light_Dialog_Alert);
        myAlertDialog.setTitle("Select Option");
        myAlertDialog.setMessage("How do you want to set your picture?");

        myAlertDialog.setPositiveButton("Gallery",
                (arg0, arg1) -> pickFromGallery());

        myAlertDialog.setNegativeButton("Camera",
                (arg0, arg1) -> takeFromCamera());

        myAlertDialog.show();
    }

    private void pickFromGallery() {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        startActivityForResult(intent,GALLERY_PICTURE);
    }

    private void takeFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
//            String authKey = preference.getUserAuth();
            switch (requestCode) {
                case CAMERA_REQUEST:
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");

                    UtilitiesCore.loadCircleImage(imgPhotoProfile, imageBitmap, this);
                    profileImage = UtilitiesCore.encodeToBase64Only(imageBitmap, extension);
                    extension = ".jpg";
//                    Log.d("Base64", encode);
//                    viewModel.updateProfilePicture(authKey, encode, ".jpg");
//                    saveToSharedPreferences(encode);

                    break;
                case GALLERY_PICTURE:
                    Uri uri = data.getData();
                    String filePath = uri.getPath();
                    try {
                        if (filePath != null) {
                            extension = filePath.substring(filePath.lastIndexOf("."));
                        }
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        UtilitiesCore.loadCircleImage(imgPhotoProfile, bitmap, this);
                        profileImage = UtilitiesCore.encodeToBase64Only(bitmap, extension);
//                        Log.d("Base64", end);
//                        viewModel.updateProfilePicture(authKey, end, extension);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

    }

    @OnClick(R.id.btnKonfirmasi)
    void submit(){
        if(validasi()){
            viewModel.updateProfile(
                    preference.getUserAuth(),
                    mTxtEmail.getText().toString(),
                    phone.getText().toString(),
                    profileImage,
                    extension
            );
        }
    }

    @OnClick(R.id.imageTakeFotoProfile)
    void takePhoto(){
        startTakePhotoDialog();
    }
    @OnClick(R.id.imgback)
    void finis() {
        finish();
    }
}
