package com.indocyber.itsmeandroid.view.contactcc.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.view.contactcc.adapter.ContactCCAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.indocyber.itsmeandroid.view.home.fragment.HomeFragment.dataImageCard;

public class ContactCCActivity extends AppCompatActivity {

    private RecyclerView mRlvBlock;
    private Spinner mSpnrTag;
    public static int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 200;
    private ContactCCAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_cc);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Contact Card");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setElevation(0f);
        }

        mRlvBlock = findViewById(R.id.rlvBlock);
        mSpnrTag = findViewById(R.id.spnrTag);

        dataImageCard().observe(Objects.requireNonNull(this), list -> {
            mAdapter = new ContactCCAdapter(this);
            mAdapter.setListNotes(list);
            mRlvBlock.setAdapter(mAdapter);
            mRlvBlock.setLayoutManager(new LinearLayoutManager(this));
            mRlvBlock.setHasFixedSize(true);

        });

        setSpinnerTag();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       if (item.getItemId() == android.R.id.home){
           finish();
       }
        return super.onOptionsItemSelected(item);

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
            SimpleAdapter adapter = new SimpleAdapter(this, listSpinner, R.layout.layout_spinner, from, to);
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
