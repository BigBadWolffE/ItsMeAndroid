package com.indocyber.itsmeandroid.view.kartuku.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.view.BaseActivity;

public class Kartuku extends BaseActivity {

    @Override
    protected int layoutRes() {
        return R.layout.activity_kartuku;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutRes());
    }
}
