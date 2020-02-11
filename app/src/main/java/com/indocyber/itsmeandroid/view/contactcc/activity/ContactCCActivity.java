package com.indocyber.itsmeandroid.view.contactcc.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.view.contactcc.adapter.ContactCCAdapter;
import com.indocyber.itsmeandroid.viewmodel.ContactCcViewModel;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;


public class ContactCCActivity extends AppCompatActivity {

    private Spinner mSpnrTag;
    public static int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 200;
    private ContactCCAdapter mAdapter;
    private ContactCcViewModel viewModel;
    private AlertDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_cc);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Contact Card");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setElevation(0f);
        }

        viewModel = ViewModelProviders.of(this).get(ContactCcViewModel.class);
        mAdapter = new ContactCCAdapter(
                this,
                (model) -> viewModel.insertNewTag(model),
                new ArrayList<>()
        );
        RecyclerView mRlvBlock = findViewById(R.id.rlvBlock);
        mRlvBlock.setLayoutManager(new LinearLayoutManager(this));
        mRlvBlock.setAdapter(mAdapter);
        mRlvBlock.setHasFixedSize(true);
        String[] emptyArray = {""};
        mSpnrTag = findViewById(R.id.spnrTag);
        mSpnrTag.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_item_text, emptyArray));
        viewModel.getAll();
        viewModel.getAllTaglist();
        observeViewModel();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);

    }

    private void observeViewModel() {
        viewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                if(loader == null){
                    loader = new SpotsDialog.Builder()
                            .setCancelable(false)
                            .setContext(ContactCCActivity.this)
                            .build();
                }
                loader.show();
            } else {
                loader.dismiss();
            }
        });

        viewModel.getAlldata().observe(this, list -> mAdapter.refreshCardList(list));
        viewModel.getTagList().observe(this, tags -> {
            if (tags == null) {
                return;
            }
            mSpnrTag.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_item_text, tags));
            mSpnrTag.setSelection(0, false);
            mSpnrTag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String tag = (String)mSpnrTag.getSelectedItem();
                    viewModel.getCardByTag(tag);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        });
    }

}
