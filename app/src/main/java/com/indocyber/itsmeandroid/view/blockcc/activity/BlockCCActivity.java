package com.indocyber.itsmeandroid.view.blockcc.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.view.blockcc.adapter.BlockCCAdapter;

import java.util.Objects;

import static com.indocyber.itsmeandroid.view.home.fragment.HomeFragment.dataImageCard;

public class BlockCCActivity extends AppCompatActivity {

    private RecyclerView mRlvBlock;
    private Button mBtnBlock;
    private BlockCCAdapter mAdapter;

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

        setDataBlockCard();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setDataBlockCard(){
        dataImageCard().observe(this, list -> {
            mAdapter = new BlockCCAdapter(this);
            mAdapter.setListNotes(list);
            mRlvBlock.setAdapter(mAdapter);
            mRlvBlock.setLayoutManager(new LinearLayoutManager(this));
            mRlvBlock.setHasFixedSize(true);
               /* mBtnBlock.setOnClickListener(v -> {
                    list.remove(0);
                    mAdapter.setListNotes(list);

                });*/
        });
    }
}
