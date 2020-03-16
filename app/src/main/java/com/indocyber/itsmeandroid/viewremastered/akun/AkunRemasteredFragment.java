package com.indocyber.itsmeandroid.viewremastered.akun;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.indocyber.itsmeandroid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AkunRemasteredFragment extends Fragment {

    public AkunRemasteredFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_akun_remastered, container, false);
    }
}
