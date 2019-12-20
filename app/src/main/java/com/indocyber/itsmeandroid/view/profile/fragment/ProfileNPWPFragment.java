package com.indocyber.itsmeandroid.view.profile.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.indocyber.itsmeandroid.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;


public class ProfileNPWPFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    protected static final int CAMERA_REQUEST = 0;
    protected static final int GALLERY_PICTURE = 1;

    private String mParam1;
    private String mParam2;

    private ImageView mFotoNPWP, mTakeFotoNPWP, mShareNPWP;
    private EditText mNamaNPWP, mAlamatKPP, mNoNPWP, mNIK, mAlamatNPWP;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_npwp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFotoNPWP = view.findViewById(R.id.imgFotoNPWP);
        mTakeFotoNPWP = view.findViewById(R.id.imgTakeFotoNPWP);
        mShareNPWP = view.findViewById(R.id.imgShareFotoNPWP);
        mNamaNPWP = view.findViewById(R.id.txtProfileNamaNPWP);
        mNoNPWP = view.findViewById(R.id.txtProfileNoNPWP);
        mNIK = view.findViewById(R.id.txtProfileNIKNPWP);
        mAlamatNPWP = view.findViewById(R.id.txtProfileAlamatNPWP);
        mAlamatKPP = view.findViewById(R.id.txtProfileAlamatKPP);

        mNamaNPWP.setEnabled(false);
        mNamaNPWP.setTextColor(Color.BLACK);
        mNoNPWP.setEnabled(false);
        mNoNPWP.setTextColor(Color.BLACK);
        mAlamatNPWP.setEnabled(false);
        mAlamatNPWP.setTextColor(Color.BLACK);
        mAlamatKPP.setEnabled(false);
        mAlamatKPP.setTextColor(Color.BLACK);
        mNIK.setEnabled(false);
        mNIK.setTextColor(Color.BLACK);

        mTakeFotoNPWP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTakePhotoDialog();
            }
        });

        mShareNPWP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    mFotoNPWP.setImageBitmap(imageBitmap);
                    break;
                case GALLERY_PICTURE:
                    Uri uri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                        // Log.d(TAG, String.valueOf(bitmap));
                        mFotoNPWP.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

}
