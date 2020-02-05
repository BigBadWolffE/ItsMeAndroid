package com.indocyber.itsmeandroid.view.profile.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;
import com.indocyber.itsmeandroid.view.profile.adapter.TabAdapter;
import com.indocyber.itsmeandroid.view.profile.fragment.DetailProfileFragment;
import com.indocyber.itsmeandroid.view.profile.fragment.ProfileKTPFragment;
import com.indocyber.itsmeandroid.view.profile.fragment.ProfileNPWPFragment;
import com.indocyber.itsmeandroid.view.profile.fragment.ProfilePassportFragment;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    protected static final int CAMERA_REQUEST = 0;
    protected static final int GALLERY_PICTURE = 1;
    private CircleImageView mFotoProfile;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Profile");
            getSupportActionBar().setElevation(0f);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        pref = getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        String paramProfPic = pref.getString("ProfilePicture", null);

        int fragmentId = getIntent().getIntExtra("GO_TO_KTP", 0);

        ViewPager mViewPager = findViewById(R.id.viewPagerProfile);
        TabLayout mTabLayout = findViewById(R.id.tabLayoutProfile);
        mFotoProfile = findViewById(R.id.imageFotoProfile);
        ImageView mTakePhotoBtn = findViewById(R.id.imageTakeFotoProfile);

        TabAdapter mTabAdapter = new TabAdapter(getSupportFragmentManager());
        mTabAdapter.addFragment(new DetailProfileFragment(), "Detail Profile");
        mTabAdapter.addFragment(new ProfileKTPFragment(), "KTP");
        mTabAdapter.addFragment(new ProfileNPWPFragment(), "NPWP");
        mTabAdapter.addFragment(new ProfilePassportFragment(), "Passport");
        mViewPager.setAdapter(mTabAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
        if (fragmentId != 0) {
            mViewPager.setCurrentItem(fragmentId);
        }

        if (paramProfPic != null) {
            Bitmap newProfpic = UtilitiesCore.decodeImage(paramProfPic);
            mFotoProfile.setImageBitmap(newProfpic);
        }

        mTakePhotoBtn.setOnClickListener(view -> startTakePhotoDialog());
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case CAMERA_REQUEST:
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    mFotoProfile.setImageBitmap(imageBitmap);
                    String encode = UtilitiesCore.encodeImage(imageBitmap);
                    saveToSharedPreferences(encode);
                    break;
                case GALLERY_PICTURE:
                    Uri uri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        bitmap = UtilitiesCore.getResizedBitmap(bitmap, 800);
                        mFotoProfile.setImageBitmap(bitmap);
                        String end = UtilitiesCore.encodeImage(bitmap);
                        saveToSharedPreferences(end);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private void saveToSharedPreferences(String encode) {
        editor.putString("ProfilePicture", encode);
        editor.commit();
    }
}
