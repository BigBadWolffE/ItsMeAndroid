package com.indocyber.itsmeandroid.view.profile.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.view.MainActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

public class ProfileKTPFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    protected static final int CAMERA_REQUEST = 0;
    protected static final int GALLERY_PICTURE = 1;

    private String mParam1;
    private String mParam2;
    private ImageView mFotoKTP, mTakeFotoKTP, mShareKTP;
    private EditText mNamaKTP, mAlamat, mNoKTP,
            mTglLahir, mRT, mRW;
    private int mYear, mMonth, mDay;
    private Spinner mAgamaSpinner, mStatusSpinner, mPekerjaanSpinner, mKWNSpinner, mKelurahanSpinner, mKecamatanSpinner;
    private String[] mMonthData = {"Januari", "Februari", "Maret", "April",
                                    "Mei", "Juni", "Juli", "Agustus", "September",
                                    "Oktober", "November", "Desember"};
    private String[] mAgamaValue = {"Pilih Agama", "Islam", "Kristen", "Katolik",
            "Buddha", "Hindu", "Kong Hu Cu"};
    private String[] mStatusValue = {"Pilih Status", "Lajang", "Menikah"};
    private String[] mPekerjaanValue = {"Pilih Pekerjaan"};
    private String[] mKWNValue = {"Pilih Kewarganegaraan"};
    private String[] mKelurahanValue = {"Pilih Kelurahan"};
    private String[] mKecamatanValue = {"Pilih Kecamatan"};

    public ProfileKTPFragment() {
        // Required empty public constructor
    }

    public static ProfileKTPFragment newInstance(String param1, String param2) {
        ProfileKTPFragment fragment = new ProfileKTPFragment();
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
        return inflater.inflate(R.layout.fragment_profile_ktp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFotoKTP = view.findViewById(R.id.imgFotoKTP);
        mTakeFotoKTP = view.findViewById(R.id.imgTakeFotoKTP);
        mShareKTP = view.findViewById(R.id.imgShareFotoKTP);
        mNamaKTP = view.findViewById(R.id.txtProfileNamaKTP);
        mNoKTP = view.findViewById(R.id.txtProfileNoKTP);
        mTglLahir = view.findViewById(R.id.txtProfileTanggalLahirKTP);
        mAlamat = view.findViewById(R.id.txtProfileAlamatKTP);
        mRT = view.findViewById(R.id.txtProfileRTKTP);
        mRW = view.findViewById(R.id.txtProfileRWKTP);
        mAgamaSpinner = view.findViewById(R.id.spinnerAgamaKTP);
        mStatusSpinner = view.findViewById(R.id.spinnerStatusKTP);
        mPekerjaanSpinner = view.findViewById(R.id.spinnerPekerjaanKTP);
        mKWNSpinner = view.findViewById(R.id.spinnerKWNKTP);
        mKelurahanSpinner = view.findViewById(R.id.spinnerKelurahanKTP);
        mKecamatanSpinner = view.findViewById(R.id.spinnerKecamatanKTP);

        initializeSpinner();

        mNamaKTP.setEnabled(false);
        mNamaKTP.setTextColor(Color.BLACK);
        mNoKTP.setEnabled(false);
        mNoKTP.setTextColor(Color.BLACK);
        mTglLahir.setInputType(InputType.TYPE_NULL);
        mAlamat.setEnabled(false);
        mAlamat.setTextColor(Color.BLACK);
        mRT.setEnabled(false);
        mRT.setTextColor(Color.BLACK);
        mRW.setEnabled(false);
        mRW.setTextColor(Color.BLACK);

        mTakeFotoKTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTakePhotoDialog();
            }
        });

        mTglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                                mTglLahir.setText(dayOfMonth + " " + mMonthData[monthOfYear] + " " + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        mShareKTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFotoKTP.getDrawable().getConstantState()
                        .equals(getContext().getResources().getDrawable(R.drawable.ic_profile_default_img)
                                .getConstantState())) {
                    Toast.makeText(getContext(), "Tidak ada Foto yang dapat di bagikan", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Bitmap bitmap = ((BitmapDrawable)mFotoKTP.getDrawable()).getBitmap();
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
        });
    }

    private void initializeSpinner() {
        ArrayAdapter<String> agamaAdapter
                = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mAgamaValue);
        agamaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAgamaSpinner.setAdapter(agamaAdapter);

        ArrayAdapter<String> statusAdapter
                = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mStatusValue);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStatusSpinner.setAdapter(statusAdapter);

        ArrayAdapter<String> pekerjaanAdapter
                = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mPekerjaanValue);
        pekerjaanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPekerjaanSpinner.setAdapter(pekerjaanAdapter);

        ArrayAdapter<String> kwnAdapter
                = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mKWNValue);
        kwnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mKWNSpinner.setAdapter(kwnAdapter);

        ArrayAdapter<String> kelurahanAdapter
                = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mKelurahanValue);
        kelurahanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mKelurahanSpinner.setAdapter(kelurahanAdapter);

        ArrayAdapter<String> kecamatanAdapter
                = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mKecamatanValue);
        kecamatanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mKecamatanSpinner.setAdapter(kecamatanAdapter);
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
                    mFotoKTP.setImageBitmap(imageBitmap);
                    break;
                case GALLERY_PICTURE:
                    Uri uri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                        // Log.d(TAG, String.valueOf(bitmap));
                        mFotoKTP.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

}
