package com.indocyber.itsmeandroid.view.profile.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.indocyber.itsmeandroid.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class ProfilePassportFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    protected static final int CAMERA_REQUEST = 0;
    protected static final int GALLERY_PICTURE = 1;

    private String mParam1;
    private String mParam2;

    private ImageView mFotoPassport, mTakeFotoPassport, mSharePassport;
    private EditText mNamaPassport, mKWNPassport, mNoPassport, mTglLahirPassport, mTempatLahirPassport;
    
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

        mFotoPassport = view.findViewById(R.id.imgFotoPassport);
        mTakeFotoPassport = view.findViewById(R.id.imgTakeFotoPassport);
        mSharePassport = view.findViewById(R.id.imgShareFotoPassport);
        mNamaPassport = view.findViewById(R.id.txtProfileNamaPassport);
        mNoPassport = view.findViewById(R.id.txtProfileNoPassport);
        mKWNPassport = view.findViewById(R.id.txtProfileKWNPassport);
        mTempatLahirPassport = view.findViewById(R.id.txtProfileTempatLahirPassport);
        mTglLahirPassport = view.findViewById(R.id.txtProfileTglLahirPassport);

        mNamaPassport.setEnabled(false);
        mNoPassport.setEnabled(false);
        mKWNPassport.setEnabled(false);
        mTempatLahirPassport.setEnabled(false);
        mTglLahirPassport.setEnabled(false);

        mTakeFotoPassport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTakePhotoDialog();
            }
        });

        mSharePassport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
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
                        // Log.d(TAG, String.valueOf(bitmap));
                        mFotoPassport.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
    
}
