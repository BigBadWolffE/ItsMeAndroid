package com.indocyber.itsmeandroid.view.profile.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ProfileNPWPModel;
import com.indocyber.itsmeandroid.services.network.Api;
import com.indocyber.itsmeandroid.utilities.Preference;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;
import com.indocyber.itsmeandroid.view.BaseFragment;
import com.indocyber.itsmeandroid.viewmodel.ProfileDetailViewModel;
import com.indocyber.itsmeandroid.viewmodel.ViewModelFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import javax.inject.Inject;


public class ProfileNPWPFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    protected static final int CAMERA_REQUEST = 0;
    protected static final int GALLERY_PICTURE = 1;

    private String mParam1;
    private String mParam2;

    private TextView errorText;
    private LinearLayout agreeLayout;
    private CheckBox agreeCheck;
    private Button saveBtn;
    private ImageView mFotoNPWP, mTakeFotoNPWP, mShareNPWP;
    private EditText mNamaNPWP, mAlamatKPP, mNoNPWP, mNIK, mAlamatNPWP;
    private ProfileNPWPModel mNPWPModel;
    private View mViewOnCreate;
    private String fotoBase64;
    private ProfileDetailViewModel viewModel;
    private Preference preference;
    @Inject
    ViewModelFactory factory;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public ProfileNPWPFragment() {
        // Required empty public constructor
    }

    public static ProfileNPWPFragment newInstance(String param1, String param2) {
        ProfileNPWPFragment fragment = new ProfileNPWPFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_profile_npwp;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(layoutRes(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mViewOnCreate = view;
        preference = new Preference(getContext());
        viewModel = ViewModelProviders.of(getActivity(), factory).get(ProfileDetailViewModel.class);
//        pref = getContext().getSharedPreferences("MyPref", 0);
//        editor = pref.edit();
//        editor.remove("ProfileNPWP");
//        editor.apply();
//        Gson gson = new Gson();
//        String paramUserData = pref.getString("ProfileNPWP", null);
//        mNPWPModel = gson.fromJson(paramUserData, ProfileNPWPModel.class);
        Log.d("Cek", "Profile NPWP "+mNPWPModel);
        initializeView();
        observeViewModel();

//        if (mNPWPModel != null) {
//            setModelNotNull();
//            agreeLayout.setVisibility(View.GONE);
//            saveBtn.setBackground(getContext().getDrawable(R.drawable.button_primary));
//            saveBtn.setEnabled(true);
//        }

        agreeCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAgreement();
            }
        });

        mTakeFotoNPWP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTakePhotoDialog();
            }
        });

        mShareNPWP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareFoto();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (formValidation()) {
                    ProfileNPWPModel mNPWPModelInside = new ProfileNPWPModel();
                    mNPWPModelInside = setToModel();
                    saveToSharedPreferences(mNPWPModelInside);
                    Toast.makeText(getContext(), "Saved Sukses", Toast.LENGTH_SHORT).show();
                    errorText.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
//        viewModel.getProfileNpwp(preference.getUserAuth());
    }

    private void setModelNotNull(ProfileNPWPModel model) {
//        Bitmap bitmap = UtilitiesCore.decodeImage(model.getFotoNPWP());
//        mFotoNPWP.setImageBitmap(bitmap);
        UtilitiesCore.loadImageFromUri(mFotoNPWP, getContext(), Api.NPWP_IMAGE, preference.getUserAuth());
        mNamaNPWP.setText(model.getNamaLengkap());
        mNoNPWP.setText(model.getNoNpwp());
        mNIK.setText(model.getNik());
        mAlamatNPWP.setText(model.getAlamat());
        mAlamatKPP.setText(model.getAlamatKPP());
    }

    private void saveToSharedPreferences(ProfileNPWPModel model) {
        Gson gson = new Gson();
        String json = gson.toJson(model);
        editor.putString("ProfileNPWP", json);
        editor.commit();
    }

    private ProfileNPWPModel setToModel() {
        ProfileNPWPModel mNPWPModelInside = new ProfileNPWPModel();
        mNPWPModelInside.setNamaLengkap(mNamaNPWP.getText().toString());
        mNPWPModelInside.setNoNpwp(mNoNPWP.getText().toString());
        mNPWPModelInside.setAlamat(mAlamatNPWP.getText().toString());
        mNPWPModelInside.setNik(mNIK.getText().toString());
        mNPWPModelInside.setAlamatKPP(mAlamatKPP.getText().toString());
        mNPWPModelInside.setFotoNPWP(fotoBase64);
        return mNPWPModelInside;
    }

    private boolean formValidation() {
        if (mNamaNPWP.getText().length() == 0) {
            setErrorText("Silahkan isi nama terlebih dahulu.");
            return false;
        } else if (mNoNPWP.getText().length() == 0) {
            setErrorText("Silahkan isi nomor npwp terlebih dahulu.");
            return false;
        } else if (mNIK.getText().length() == 0) {
            setErrorText("Silahkan isi NIK terlebih dahulu.");
            return false;
        } else if (mAlamatNPWP.getText().length() == 0) {
            setErrorText("Silahkan isi alamat terlebih dahulu.");
            return false;
        } else if (mAlamatKPP.getText().length() == 0) {
            setErrorText("Silahkan isi alamat kantor pelayanan pajak terlebih dahulu.");
            return false;
        } else if (mFotoNPWP.getDrawable().getConstantState()
                .equals(getContext().getResources().getDrawable(R.drawable.ic_profile_default_img)
                        .getConstantState())) {
            setErrorText("Silahkan ambil foto terlebih dahulu.");
            return false;
        } else {
            return true;
        }
    }

    private void setErrorText(String text) {
        errorText.setVisibility(View.VISIBLE);
        errorText.setText(text);
    }

    private void shareFoto() {
        if (mFotoNPWP.getDrawable().getConstantState()
                .equals(getContext().getResources().getDrawable(R.drawable.ic_profile_default_img)
                        .getConstantState())) {
            Toast.makeText(getContext(), "Tidak ada Foto yang dapat di bagikan", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Bitmap bitmap = ((BitmapDrawable)mFotoNPWP.getDrawable()).getBitmap();
            Uri uri = getImageUri(getContext(), bitmap);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                    shareIntent.setType("text/plain");
//                    shareIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setType("image/jpeg");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            try {
                startActivity(shareIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getContext(), "Gagal membagikan foto", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void initializeView() {
        agreeCheck = mViewOnCreate.findViewById(R.id.cbAgreeTerms);
        saveBtn = mViewOnCreate.findViewById(R.id.btnSaveProfileNPWP);
        mFotoNPWP = mViewOnCreate.findViewById(R.id.imgFotoNPWP);
        mTakeFotoNPWP = mViewOnCreate.findViewById(R.id.imgTakeFotoNPWP);
        mShareNPWP = mViewOnCreate.findViewById(R.id.imgShareFotoNPWP);
        mNamaNPWP = mViewOnCreate.findViewById(R.id.txtProfileNamaNPWP);
        mNoNPWP = mViewOnCreate.findViewById(R.id.txtProfileNoNPWP);
        mNIK = mViewOnCreate.findViewById(R.id.txtProfileNIKNPWP);
        mAlamatNPWP = mViewOnCreate.findViewById(R.id.txtProfileAlamatNPWP);
        mAlamatKPP = mViewOnCreate.findViewById(R.id.txtProfileAlamatKPP);
        agreeLayout = mViewOnCreate.findViewById(R.id.layoutAgreeTerms);
        errorText = mViewOnCreate.findViewById(R.id.errorText);
    }

    private void checkAgreement() {
        if (!agreeCheck.isChecked()) {
            saveBtn.setBackground(getContext().getDrawable(R.drawable.button_grey));
            saveBtn.setEnabled(false);
        } else {
            saveBtn.setBackground(getContext().getDrawable(R.drawable.button_primary));
            saveBtn.setEnabled(true);
        }
    }

    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(),
                bitmap, UUID.randomUUID().toString() + ".png", "drawing");
        return Uri.parse(path);
    }

    private void startTakePhotoDialog() {
        final AlertDialog.Builder myAlertDialog =
                new AlertDialog.Builder(getContext(), R.style.Theme_MaterialComponents_Light_Dialog_Alert);
        myAlertDialog.setTitle("Select Option");
        myAlertDialog.setMessage("How do you want to set your picture?");

        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        pickFromGallery();
                    }
                });

        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        takeFromCamera();
                    }
                });

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
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA_REQUEST);
        }
    }

    private void observeViewModel() {
        viewModel.getUserNpwp().observe(getActivity(), profileNPWPModel -> {
            setModelNotNull(profileNPWPModel);
            agreeLayout.setVisibility(View.GONE);
            saveBtn.setBackground(getContext().getDrawable(R.drawable.button_primary));
            saveBtn.setEnabled(true);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {

            switch (requestCode) {
                case CAMERA_REQUEST:
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    mFotoNPWP.setImageBitmap(imageBitmap);
                    break;
                case GALLERY_PICTURE:
                    Uri uri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                        bitmap = UtilitiesCore.getResizedBitmap(bitmap, 800);
                        mFotoNPWP.setImageBitmap(bitmap);
                        fotoBase64 = UtilitiesCore.encodeImage(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

}
