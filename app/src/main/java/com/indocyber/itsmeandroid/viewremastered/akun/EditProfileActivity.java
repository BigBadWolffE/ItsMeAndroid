package com.indocyber.itsmeandroid.viewremastered.akun;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.indocyber.itsmeandroid.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.imgback)
    void finis(){
        finish();
    }
}
