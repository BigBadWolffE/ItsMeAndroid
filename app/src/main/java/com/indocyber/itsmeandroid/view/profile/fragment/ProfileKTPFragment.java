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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ProfileKTPModel;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

public class ProfileKTPFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    protected static final int CAMERA_REQUEST_KTP = 0;
    protected static final int GALLERY_PICTURE_KTP = 1;

    private String mParam1;
    private String mParam2;

    private ArrayAdapter<String> agamaAdapter, statusAdapter, pekerjaanAdapter,
            kecamatanAdapter, kelurahanAdapter, kwnAdapter;
    private LinearLayout agreeLayout;
    private RadioGroup mRadioGroupJK;
    private RadioButton radioSelected, male, female;
    private CheckBox agreeCheck;
    private Button saveBtn;
    private TextView mErrorText;
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
    private String[] mPekerjaanValue = {"Pilih Pekerjaan", "Karyawan Swasta"};
    private String[] mKWNValue = {"Pilih Kewarganegaraan", "Indonesia"};
    private String[] mKelurahanValue = {"Pilih Kelurahan", "Duri Kepa"};
    private String[] mKecamatanValue = {"Pilih Kecamatan", "Kebon Jeruk"};
    private ProfileKTPModel mKTPModel = new ProfileKTPModel();
    private View mViewOnCreate;
    private String fotoBase64;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

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
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mViewOnCreate = view;
        pref = getContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
//        editor.remove("ProfileKTP");
//        editor.apply();
        Gson gson = new Gson();
        String paramUserData = pref.getString("ProfileKTP", null);
        mKTPModel = gson.fromJson(paramUserData, ProfileKTPModel.class);
        Log.d("Cek", "Profile KTP "+mKTPModel);

        initializeView();
        initializeSpinner();

        if (mKTPModel != null) {
            setModelNotNull();
            agreeLayout.setVisibility(View.GONE);
            saveBtn.setBackground(getContext().getDrawable(R.drawable.button_primary));
            saveBtn.setEnabled(true);
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (formValidation()) {
                    ProfileKTPModel mKTPModelInside = new ProfileKTPModel();
                    mKTPModelInside = setToModel();
                    saveToSharedPreferences(mKTPModelInside);
                    Toast.makeText(getContext(), "Saved Sukses", Toast.LENGTH_SHORT).show();
                    mErrorText.setVisibility(View.GONE);
                }
            }
        });

        agreeCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAgreement();
            }
        });

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
                                String dateValue = dayOfMonth + " " + mMonthData[monthOfYear] + " " + year;
                                mTglLahir.setText(dateValue);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        mShareKTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImage();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        editor.remove("ProfileKTP");
//        editor.apply();
    }

    private void setModelNotNull() {
        int spinnerPosition = 0;
        Bitmap bitmap = UtilitiesCore.decodeImage(mKTPModel.getFotoKTP());
        mFotoKTP.setImageBitmap(bitmap);
        mNamaKTP.setText(mKTPModel.getNamaLengkap());
        mNoKTP.setText(mKTPModel.getNoKtp());
        mTglLahir.setText(mKTPModel.getTglLahir());
        spinnerPosition = agamaAdapter.getPosition(mKTPModel.getAgama());
        mAgamaSpinner.setSelection(spinnerPosition);
        spinnerPosition = statusAdapter.getPosition(mKTPModel.getStatus());
        mStatusSpinner.setSelection(spinnerPosition);
        spinnerPosition = kwnAdapter.getPosition(mKTPModel.getKwn());
        mKWNSpinner.setSelection(spinnerPosition);
        spinnerPosition = pekerjaanAdapter.getPosition(mKTPModel.getPekerjaan());
        mPekerjaanSpinner.setSelection(spinnerPosition);
        spinnerPosition = kecamatanAdapter.getPosition(mKTPModel.getKecamatan());
        mKecamatanSpinner.setSelection(spinnerPosition);
        spinnerPosition = kelurahanAdapter.getPosition(mKTPModel.getKelurahan());
        mKelurahanSpinner.setSelection(spinnerPosition);
        mAlamat.setText(mKTPModel.getAlamat());
        mRT.setText(mKTPModel.getRt());
        mRW.setText(mKTPModel.getRw());
        if (mKTPModel.getJenisKelamin().equalsIgnoreCase("Perempuan")) {
            female.setChecked(true);
        } else {
            male.setChecked(true);
        }
    }

    private void shareImage() {
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

    private ProfileKTPModel setToModel() {
        ProfileKTPModel mKTPModelInside = new ProfileKTPModel();
        int jkSelected = mRadioGroupJK.getCheckedRadioButtonId();
        radioSelected = (RadioButton) mViewOnCreate.findViewById(jkSelected);
        mErrorText.setVisibility(View.GONE);
        mKTPModelInside.setNamaLengkap(mNamaKTP.getText().toString());
        mKTPModelInside.setNoKtp(mNoKTP.getText().toString());
        mKTPModelInside.setTglLahir(mTglLahir.getText().toString());
        mKTPModelInside.setJenisKelamin(radioSelected.getText().toString());
        mKTPModelInside.setAgama(mAgamaSpinner.getSelectedItem().toString());
        mKTPModelInside.setKwn(mKWNSpinner.getSelectedItem().toString());
        mKTPModelInside.setPekerjaan(mPekerjaanSpinner.getSelectedItem().toString());
        mKTPModelInside.setStatus(mStatusSpinner.getSelectedItem().toString());
        mKTPModelInside.setAlamat(mAlamat.getText().toString());
        mKTPModelInside.setRt(mRT.getText().toString());
        mKTPModelInside.setRw(mRW.getText().toString());
        mKTPModelInside.setKecamatan(mKecamatanSpinner.getSelectedItem().toString());
        mKTPModelInside.setKelurahan(mKelurahanSpinner.getSelectedItem().toString());
        mKTPModelInside.setFotoKTP(fotoBase64);
        return mKTPModelInside;
    }

    private void saveToSharedPreferences(ProfileKTPModel model) {
        Gson gson = new Gson();
        String json = gson.toJson(model);
        editor.putString("ProfileKTP", json);
        editor.commit();
    }

    private boolean formValidation() {
        if (mNamaKTP.getText().length() == 0) {
            setErrorText("Silahkan isi nama terlebih dahulu.");
            return false;
        } else if (mNoKTP.getText().length() == 0) {
            setErrorText("Silahkan isi nomor ktp terlebih dahulu.");
            return false;
        } else if (mTglLahir.getText().length() == 0) {
            setErrorText("Silahkan isi tanggal lahir terlebih dahulu.");
            return false;
        } else if (mRadioGroupJK.getCheckedRadioButtonId() == -1) {
            setErrorText("Silahkan pilih jenis kelamin terlebih dahulu.");
            return false;
        } else if (mAgamaSpinner.getSelectedItemId() == 0) {
            setErrorText("Silahkan pilih agama terlebih dahulu.");
            return false;
        } else if (mStatusSpinner.getSelectedItemId() == 0) {
            setErrorText("Silahkan pilih status terlebih dahulu.");
            return false;
        } else if (mPekerjaanSpinner.getSelectedItemId() == 0) {
            setErrorText("Silahkan pilih pekerjaan terlebih dahulu.");
            return false;
        } else if (mKWNSpinner.getSelectedItemId() == 0) {
            setErrorText("Silahkan pilih kewarganegaraan terlebih dahulu.");
            return false;
        } else if (mAlamat.getText().length() == 0) {
            setErrorText("Silahkan isi alamat terlebih dahulu.");
            return false;
        } else if (mRT.getText().length() == 0) {
            setErrorText("Silahkan isi RT terlebih dahulu.");
            return false;
        } else if (mRW.getText().length() == 0) {
            setErrorText("Silahkan isi RW terlebih dahulu.");
            return false;
        } else if (mKelurahanSpinner.getSelectedItemId() == 0) {
            setErrorText("Silahkan pilih kelurahan terlebih dahulu.");
            return false;
        } else if (mKecamatanSpinner.getSelectedItemId() == 0) {
            setErrorText("Silahkan pilih kecamatan terlebih dahulu.");
            return false;
        } else if (mFotoKTP.getDrawable().getConstantState()
                .equals(getContext().getResources().getDrawable(R.drawable.ic_profile_default_img)
                        .getConstantState())) {
            setErrorText("Silahkan ambil foto terlebih dahulu.");
            return false;
        } else {
            return true;
        }
    }

    private void setErrorText(String text) {
        mErrorText.setVisibility(View.VISIBLE);
        mErrorText.setText(text);
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


    private void initializeView() {
        mRadioGroupJK = mViewOnCreate.findViewById(R.id.radioJenisKelamin);
        mErrorText = mViewOnCreate.findViewById(R.id.errorText);
        agreeCheck = mViewOnCreate.findViewById(R.id.cbAgreeTerms);
        saveBtn = mViewOnCreate.findViewById(R.id.btnSaveProfileKTP);
        mFotoKTP = mViewOnCreate.findViewById(R.id.imgFotoKTP);
        mFotoKTP.setDrawingCacheEnabled(true);
        mTakeFotoKTP = mViewOnCreate.findViewById(R.id.imgTakeFotoKTP);
        mShareKTP = mViewOnCreate.findViewById(R.id.imgShareFotoKTP);
        mNamaKTP = mViewOnCreate.findViewById(R.id.txtProfileNamaKTP);
        mNoKTP = mViewOnCreate.findViewById(R.id.txtProfileNoKTP);
        mTglLahir = mViewOnCreate.findViewById(R.id.txtProfileTanggalLahirKTP);
        mTglLahir.setInputType(InputType.TYPE_NULL);
        mAlamat = mViewOnCreate.findViewById(R.id.txtProfileAlamatKTP);
        mRT = mViewOnCreate.findViewById(R.id.txtProfileRTKTP);
        mRW = mViewOnCreate.findViewById(R.id.txtProfileRWKTP);
        mAgamaSpinner = mViewOnCreate.findViewById(R.id.spinnerAgamaKTP);
        mStatusSpinner = mViewOnCreate.findViewById(R.id.spinnerStatusKTP);
        mPekerjaanSpinner = mViewOnCreate.findViewById(R.id.spinnerPekerjaanKTP);
        mKWNSpinner = mViewOnCreate.findViewById(R.id.spinnerKWNKTP);
        mKelurahanSpinner = mViewOnCreate.findViewById(R.id.spinnerKelurahanKTP);
        mKecamatanSpinner = mViewOnCreate.findViewById(R.id.spinnerKecamatanKTP);
        male = mViewOnCreate.findViewById(R.id.radioLakiLaki);
        female = mViewOnCreate.findViewById(R.id.radioPerempuan);
        agreeLayout = mViewOnCreate.findViewById(R.id.layoutAgreeTerms);
    }

    private void initializeSpinner() {
        agamaAdapter
                = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mAgamaValue);
        agamaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAgamaSpinner.setAdapter(agamaAdapter);

        statusAdapter
                = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mStatusValue);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStatusSpinner.setAdapter(statusAdapter);

        pekerjaanAdapter
                = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mPekerjaanValue);
        pekerjaanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPekerjaanSpinner.setAdapter(pekerjaanAdapter);

        kwnAdapter
                = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mKWNValue);
        kwnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mKWNSpinner.setAdapter(kwnAdapter);

        kelurahanAdapter
                = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mKelurahanValue);
        kelurahanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mKelurahanSpinner.setAdapter(kelurahanAdapter);

        kecamatanAdapter
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
        startActivityForResult(intent, GALLERY_PICTURE_KTP);
    }

    private void takeFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA_REQUEST_KTP);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case CAMERA_REQUEST_KTP:
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    mFotoKTP.setImageBitmap(imageBitmap);
                    fotoBase64 = UtilitiesCore.encodeImage(imageBitmap);
                    break;
                case GALLERY_PICTURE_KTP:
                    Uri uri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                        bitmap = UtilitiesCore.getResizedBitmap(bitmap, 800);
                        mFotoKTP.setImageBitmap(bitmap);
                        fotoBase64 = UtilitiesCore.encodeImage(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

}
