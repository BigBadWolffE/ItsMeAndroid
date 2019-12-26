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
import com.indocyber.itsmeandroid.view.blockcc.adapter.BlockCCAdapter;
import com.indocyber.itsmeandroid.view.home.adapter.ImageCardAdapter;
import com.indocyber.itsmeandroid.viewmodel.BlockCcViewModel;

import java.util.ArrayList;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

import static com.indocyber.itsmeandroid.view.home.fragment.HomeFragment.dataImageCard;

public class BlockCCActivity extends AppCompatActivity {

    private RecyclerView mRlvBlock;
    private Button mBtnBlock;
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

        mRlvBlock = findViewById(R.id.rlvBlock);
        mBtnBlock = findViewById(R.id.btnBlock);
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

//    private void setDataBlockCard(){
//        dataImageCard().observe(this, list -> {
//            mAdapter = new BlockCCAdapter(this);
//            mAdapter.setListNotes(list);
//            mRlvBlock.setAdapter(mAdapter);
//            mRlvBlock.setLayoutManager(new LinearLayoutManager(this));
//            mRlvBlock.setHasFixedSize(true);
//               /* mBtnBlock.setOnClickListener(v -> {
//                    list.remove(0);
//                    mAdapter.setListNotes(list);
//
//                });*/
//        });
//    }

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

        viewModel.getCardList().observe(this, list -> {
            mAdapter.refreshAdapter(list);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.fetchCardList();
    }
}
