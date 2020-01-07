package com.indocyber.itsmeandroid.view.profile.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
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

import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ProfilePassportModel;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

public class ProfilePassportFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    protected static final int CAMERA_REQUEST = 0;
    protected static final int GALLERY_PICTURE = 1;

    private String mParam1;
    private String mParam2;

    private CheckBox agreeCheck;
    private Button saveBtn;
    private ImageView mFotoPassport, mTakeFotoPassport, mSharePassport;
    private EditText mNamaPassport, mKWNPassport, mNoPassport, mTglLahirPassport, mTempatLahirPassport, mBerlakuPassport;
    private View mViewOnCreated;
    private TextView errorText;
    private LinearLayout agreeLayout;
    private int mYear, mMonth, mDay;
    private String fotoBase64;
    private String[] mMonthData = {"Januari", "Februari", "Maret", "April",
            "Mei", "Juni", "Juli", "Agustus", "September",
            "Oktober", "November", "Desember"};
    private ProfilePassportModel mPassportModel = new ProfilePassportModel();
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    
    public ProfilePassportFragment() {
        // Required empty public constructor
    }
    public static ProfilePassportFragment newInstance(String param1, String param2) {
        ProfilePassportFragment fragment = new ProfilePassportFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_passport, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewOnCreated = view;
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        pref = getContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
//        editor.remove("ProfilePassport");
//        editor.apply();
        Gson gson = new Gson();
        String paramUserData = pref.getString("ProfilePassport", null);
        mPassportModel = gson.fromJson(paramUserData, ProfilePassportModel.class);
        Log.d("Cek", "Profile Passport "+mPassportModel);

        initializeView();

        if (mPassportModel != null) {
            setModelNotNull();
            agreeLayout.setVisibility(View.GONE);
            saveBtn.setBackground(getContext().getDrawable(R.drawable.button_primary));
            saveBtn.setEnabled(true);
        }

        agreeCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAgreement();
            }
        });

        mTglLahirPassport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDate(mTglLahirPassport);
            }
        });

        mBerlakuPassport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDate(mBerlakuPassport);
            }
        });

        mTakeFotoPassport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTakePhotoDialog();
            }
        });

        mSharePassport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImage();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (formValidation()) {
                    ProfilePassportModel mPassportModelInside = new ProfilePassportModel();
                    mPassportModelInside = setToModel();
                    saveToSharedPreferences(mPassportModelInside);
                    Toast.makeText(getContext(), "Saved Sukses", Toast.LENGTH_SHORT).show();
                    errorText.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setModelNotNull() {
        Bitmap bitmap = UtilitiesCore.decodeImage(mPassportModel.getFotoPassport());
        mFotoPassport.setImageBitmap(bitmap);
        mNamaPassport.setText(mPassportModel.getNamaLengkap());
        mNoPassport.setText(mPassportModel.getNoPassport());
        mKWNPassport.setText(mPassportModel.getKwn());
        mTempatLahirPassport.setText(mPassportModel.getTempatLahir());
        mTglLahirPassport.setText(mPassportModel.getTglLahir());
        mBerlakuPassport.setText(mPassportModel.getBerlaku());
    }

    private void saveToSharedPreferences(ProfilePassportModel mPassportModelInside) {
        Gson gson = new Gson();
        String json = gson.toJson(mPassportModelInside);
        editor.putString("ProfilePassport", json);
        editor.commit();
    }

    private ProfilePassportModel setToModel() {
        ProfilePassportModel mPassportModelInside = new ProfilePassportModel();
        mPassportModelInside.setNamaLengkap(mNamaPassport.getText().toString());
        mPassportModelInside.setNoPassport(mNoPassport.getText().toString());
        mPassportModelInside.setKwn(mKWNPassport.getText().toString());
        mPassportModelInside.setTempatLahir(mTempatLahirPassport.getText().toString());
        mPassportModelInside.setTglLahir(mTglLahirPassport.getText().toString());
        mPassportModelInside.setBerlaku(mBerlakuPassport.getText().toString());
        mPassportModelInside.setFotoPassport(fotoBase64);
        return mPassportModelInside;
    }

    private boolean formValidation() {
        if (mNamaPassport.getText().length() == 0) {
            setErrorText("Silahkan isi nama terlebih dahulu.");
            return false;
        } else if (mNoPassport.getText().length() == 0) {
            setErrorText("Silahkan isi nomor passport terlebih dahulu.");
            return false;
        } else if (mKWNPassport.getText().length() == 0) {
            setErrorText("Silahkan isi kewarganegaraan terlebih dahulu.");
            return false;
        } else if (mTempatLahirPassport.getText().length() == 0) {
            setErrorText("Silahkan isi tempat lahir terlebih dahulu.");
            return false;
        } else if (mTglLahirPassport.getText().length() == 0) {
            setErrorText("Silahkan isi tanggal lahir terlebih dahulu.");
            return false;
        } else if (mBerlakuPassport.getText().length() == 0) {
            setErrorText("Silahkan isi tanggal berlaku passport terlebih dahulu.");
            return false;
        } else {
            return true;
        }
    }

    private void setErrorText(String s) {
        errorText.setVisibility(View.VISIBLE);
        errorText.setText(s);
    }

    private void getDate(EditText editText) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                R.style.Theme_MaterialComponents_Light_Dialog_Alert,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String dateValue = dayOfMonth + " " + mMonthData[monthOfYear] + " " + year;
                        editText.setText(dateValue);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void shareImage() {
        if (mFotoPassport.getDrawable().getConstantState()
                .equals(getContext().getResources().getDrawable(R.drawable.ic_profile_default_img)
                        .getConstantState())) {
            Toast.makeText(getContext(), "Tidak ada Foto yang dapat di bagikan", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Bitmap bitmap = ((BitmapDrawable)mFotoPassport.getDrawable()).getBitmap();
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
        agreeLayout = mViewOnCreated.findViewById(R.id.layoutAgreeTerms);
        errorText = mViewOnCreated.findViewById(R.id.errorText);
        agreeCheck = mViewOnCreated.findViewById(R.id.cbAgreeTerms);
        saveBtn = mViewOnCreated.findViewById(R.id.btnSaveProfilePassport);
        mFotoPassport = mViewOnCreated.findViewById(R.id.imgFotoPassport);
        mTakeFotoPassport = mViewOnCreated.findViewById(R.id.imgTakeFotoPassport);
        mSharePassport = mViewOnCreated.findViewById(R.id.imgShareFotoPassport);
        mNamaPassport = mViewOnCreated.findViewById(R.id.txtProfileNamaPassport);
        mNoPassport = mViewOnCreated.findViewById(R.id.txtProfileNoPassport);
        mKWNPassport = mViewOnCreated.findViewById(R.id.txtProfileKWNPassport);
        mTempatLahirPassport = mViewOnCreated.findViewById(R.id.txtProfileTempatLahirPassport);
        mTglLahirPassport = mViewOnCreated.findViewById(R.id.txtProfileTglLahirPassport);
        mTglLahirPassport.setInputType(InputType.TYPE_NULL);
        mBerlakuPassport = mViewOnCreated.findViewById(R.id.txtProfileBerlakuPassport);
        mBerlakuPassport.setInputType(InputType.TYPE_NULL);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {

            switch (requestCode) {
                case CAMERA_REQUEST:
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    mFotoPassport.setImageBitmap(imageBitmap);
                    break;
                case GALLERY_PICTURE:
                    Uri uri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                        bitmap = UtilitiesCore.getResizedBitmap(bitmap, 800);
                        mFotoPassport.setImageBitmap(bitmap);
                        fotoBase64 = UtilitiesCore.encodeImage(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
    
}
