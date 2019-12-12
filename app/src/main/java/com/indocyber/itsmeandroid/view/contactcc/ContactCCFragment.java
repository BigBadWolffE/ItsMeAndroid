package com.indocyber.itsmeandroid.view.contactcc;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.indocyber.itsmeandroid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactCCFragment extends Fragment {


    public ContactCCFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_cc, container, false);
    }

}
