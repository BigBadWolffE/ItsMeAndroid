package com.indocyber.itsmeandroid.view.contactcc;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.view.blockcc.adapter.BlockCCAdapter;
import com.indocyber.itsmeandroid.view.contactcc.adapter.ContactCCAdapter;
import com.indocyber.itsmeandroid.view.home.activity.HomeActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.indocyber.itsmeandroid.view.contactcc.adapter.ContactCCAdapter.getLocalBitmapUri;
import static com.indocyber.itsmeandroid.view.home.fragment.HomeFragment.dataImageCard;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactCCFragment extends Fragment {

    private RecyclerView mRlvBlock;
    private Spinner mSpnrTag;

    public ContactCCFragment() {
        // Required empty public constructor
    }

    public static int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 200;
    private ContactCCAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_cc, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRlvBlock = view.findViewById(R.id.rlvBlock);
        mSpnrTag = view.findViewById(R.id.spnrTag);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (getActivity() != null) {
            ((HomeActivity) getActivity()).setmToolbarHomeFragment();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            ((HomeActivity) getActivity()).setmToolbarContactFragment();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            dataImageCard().observe(Objects.requireNonNull(getActivity()), list -> {
                mAdapter = new ContactCCAdapter(getActivity());
                mAdapter.setListNotes(list);
                mRlvBlock.setAdapter(mAdapter);
                mRlvBlock.setLayoutManager(new LinearLayoutManager(getActivity()));
                mRlvBlock.setHasFixedSize(true);

            });

            setSpinnerTag();

        }
    }


    private void setSpinnerTag() {
        try {
            List<HashMap<String, String>> listSpinner = new ArrayList<HashMap<String, String>>();

            String[] idSpinner = {"1", "2"};
            String[] nameSpinner = {"Business", "Home"};


            for (int i = 0; i < idSpinner.length; i++) {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("id", idSpinner[i]);
                hm.put("level_name", nameSpinner[i]);
                listSpinner.add(hm);
            }
            String[] from = {"id", "level_name"};
            int[] to = {R.id.id_spinner, R.id.nama_spinner};
            SimpleAdapter adapter = new SimpleAdapter(getActivity(), listSpinner, R.layout.layout_spinner, from, to);
            mSpnrTag.setAdapter(adapter);
            mSpnrTag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long idLong) {
                    HashMap<String, String> hm = (HashMap<String, String>) parent.getAdapter().getItem(position);
                    String id = hm.get("id");
                    String level_name = hm.get("level_name");
                   /* typeId = id;
                    typeName = level_name;*/
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
