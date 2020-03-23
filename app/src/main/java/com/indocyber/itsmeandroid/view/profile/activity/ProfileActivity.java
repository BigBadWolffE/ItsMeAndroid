package com.indocyber.itsmeandroid.view.profile.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.services.network.Api;
import com.indocyber.itsmeandroid.utilities.Preference;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;
import com.indocyber.itsmeandroid.view.BaseActivity;
import com.indocyber.itsmeandroid.view.profile.adapter.TabAdapter;
import com.indocyber.itsmeandroid.view.profile.fragment.DetailProfileFragment;
import com.indocyber.itsmeandroid.view.profile.fragment.ProfileKTPFragment;
import com.indocyber.itsmeandroid.view.profile.fragment.ProfileNPWPFragment;
import com.indocyber.itsmeandroid.view.profile.fragment.ProfilePassportFragment;
import com.indocyber.itsmeandroid.viewmodel.ProfileDetailViewModel;
import com.indocyber.itsmeandroid.viewmodel.ViewModelFactory;

import java.io.IOException;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class ProfileActivity extends BaseActivity {

    protected static final int CAMERA_REQUEST = 0;
    protected static final int GALLERY_PICTURE = 1;
    private CircleImageView mFotoProfile;
    private ProfileDetailViewModel viewModel;
    private android.app.AlertDialog mLoader;
    private Preference preference;
    private String extension = ".jpg";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Inject
    ViewModelFactory factory;

    @Override
    protected int layoutRes() {
        return R.layout.activity_profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutRes());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Profile");
            getSupportActionBar().setElevation(0f);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mLoader = new SpotsDialog.Builder()
                .setCancelable(false)
                .setContext(this)
                .build();

        int fragmentId = getIntent().getIntExtra("GO_TO_KTP", 0);
        preference = new Preference(this);
        ViewPager mViewPager = findViewById(R.id.viewPagerProfile);
        TabLayout mTabLayout = findViewById(R.id.tabLayoutProfile);
        mFotoProfile = findViewById(R.id.imageFotoProfile);
        ImageView mTakePhotoBtn = findViewById(R.id.imageTakeFotoProfile);
        viewModel = ViewModelProviders.of(this, factory).get(ProfileDetailViewModel.class);

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

        UtilitiesCore.loadImageFromUri(
                mFotoProfile,
                this,
                Api.PROFILE_IMAGE,
                preference.getUserAuth());
        mTakePhotoBtn.setOnClickListener(view -> startTakePhotoDialog());
        observeViewModel();
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
            String authKey = preference.getUserAuth();
            switch (requestCode) {
                case CAMERA_REQUEST:
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    UtilitiesCore.loadImage(mFotoProfile, imageBitmap, this);
                    String encode = UtilitiesCore.encodeToBase64Only(imageBitmap, extension);
                    viewModel.updateProfilePicture(authKey, encode, ".jpg");
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
                        UtilitiesCore.loadImage(mFotoProfile, bitmap, this);
                        String end = UtilitiesCore.encodeBase64UsingStream(this, uri);
                        Log.d("Base64", end);
                        viewModel.updateProfilePicture(authKey, end, extension);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

    }

    private void observeViewModel() {
        viewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                mLoader.show();
            } else {
                mLoader.dismiss();
            }
        });

        viewModel.getProfileUpdated().observe(this, isUpdated -> {
            if (isUpdated) {
                UtilitiesCore.buildAlertDialog(
                        this,
                        "Profile Updated",
                        R.drawable.ic_approved,
                        dialogInterface -> dialogInterface.dismiss()
                );
            }
        });

        viewModel.getError().observe(this, error -> {
            if (error != null) {
                UtilitiesCore.buildAlertDialog(
                        this,
                        error,
                        R.drawable.ic_invalid,
                        dialogInterface -> dialogInterface.dismiss()
                );
            }
        });

    }

    private void saveToSharedPreferences(String encode) {
        editor.putString("ProfilePicture", encode);
        editor.commit();
    }
}
