package com.indocyber.itsmeandroid.view.contactcc.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.EditTag;
import com.indocyber.itsmeandroid.view.contactcc.adapter.ContactCCAdapter;
import com.indocyber.itsmeandroid.viewmodel.AddCcViewModel;
import com.indocyber.itsmeandroid.viewmodel.ContactCcViewModel;

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
    private ContactCcViewModel viewModel;

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

        viewModel = ViewModelProviders.of(this).get(ContactCcViewModel.class);

        /*dataImageCard().observe(Objects.requireNonNull(this), list -> {
            mAdapter = new ContactCCAdapter(this);
            mAdapter.setListNotes(list);
            mRlvBlock.setAdapter(mAdapter);
            mRlvBlock.setLayoutManager(new LinearLayoutManager(this));
            mRlvBlock.setHasFixedSize(true);

        });*/
        setDataContact();

    }

    private void setDataContact() {
        viewModel.getAll();
        ArrayList<EditTag> list = new ArrayList<>();
        list.add(new EditTag("1", "Business"));
        viewModel.getAlldata().observe(this, data -> {

            mAdapter = new ContactCCAdapter(this);
            mAdapter.setListNotes(data,list);
            mRlvBlock.setAdapter(mAdapter);
            mRlvBlock.setLayoutManager(new LinearLayoutManager(this));
            mRlvBlock.setHasFixedSize(true);
            setDataSpinner(mAdapter);
            setSpinnerTag(list);
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);

    }

    private void setDataSpinner(ContactCCAdapter mAdapter) {


        mAdapter.setSendItem(new ContactCCAdapter.sendItemClickListener() {
            @Override
            public void itemClick(List<EditTag> listTag) {
                setSpinnerTag(listTag);
            }
        });

    }

    private void setSpinnerTag(List<EditTag> list) {
        try {
            List<HashMap<String, String>> listSpinner = new ArrayList<HashMap<String, String>>();
            listSpinner.clear();
            /*String[] idSpinner = {"1", "2"};
            String[] nameSpinner = {"Business", "Home"};*/


            for (int i = 0; i < list.size(); i++) {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("id", list.get(i).getId());
                hm.put("level_name", list.get(i).getTag());
                listSpinner.add(hm);
            }

            String[] from = {"id", "level_name"};
            int[] to = {R.id.id_spinner, R.id.nama_spinner};
            SimpleAdapter adapter = new SimpleAdapter(this, listSpinner, R.layout.layout_spinner, from, to);
            mSpnrTag.setAdapter(adapter);
            adapter.notifyDataSetChanged();
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
