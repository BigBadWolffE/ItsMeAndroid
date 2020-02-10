package com.indocyber.itsmeandroid.view.blockcc.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.view.blockallconfirmation.BlockAllConfirmationActivity;
import com.indocyber.itsmeandroid.view.blockcc.adapter.BlockCCAdapter;
import com.indocyber.itsmeandroid.viewmodel.BlockCcViewModel;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

public class BlockCCActivity extends AppCompatActivity {

    private BlockCCAdapter mAdapter = new BlockCCAdapter(this);

    private BlockCcViewModel viewModel;
    private AlertDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_cc);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Block Card");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setElevation(0f);
        }
        RecyclerView mRlvBlock = findViewById(R.id.rlvBlock);
        Button mBtnBlock = findViewById(R.id.btnBlock);
        mBtnBlock.setOnClickListener(view -> {
            Intent intent = new Intent(this, BlockAllConfirmationActivity.class);
            startActivity(intent);
        });
        mAdapter.refreshAdapter(new ArrayList<>());
        mRlvBlock.setAdapter(mAdapter);
        mRlvBlock.setLayoutManager(new LinearLayoutManager(this));
        mRlvBlock.setHasFixedSize(true);

        viewModel = ViewModelProviders.of(this).get(BlockCcViewModel.class);
        viewModel.fetchCardList();
        observeViewModel();

//        setDataBlockCard();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
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
                            .setContext(BlockCCActivity.this)
                            .build();
                }
                loader.show();
            } else {
                loader.dismiss();
            }
        });

        viewModel.getCardList().observe(this, list -> mAdapter.refreshAdapter(list));
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.fetchCardList();
    }
}
