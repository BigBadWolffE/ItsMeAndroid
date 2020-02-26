package com.indocyber.itsmeandroid.view;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.indocyber.itsmeandroid.R;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class MainActivity extends DaggerAppCompatActivity {

    @LayoutRes
    protected abstract int layoutRes();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutRes());
    }
}
