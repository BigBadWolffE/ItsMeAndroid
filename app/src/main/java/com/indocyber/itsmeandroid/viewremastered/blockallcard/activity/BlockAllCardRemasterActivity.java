package com.indocyber.itsmeandroid.viewremastered.blockallcard.activity;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.BlockAllCardModel;
import com.indocyber.itsmeandroid.view.BaseActivity;
import com.indocyber.itsmeandroid.viewmodel.HomeViewModel;
import com.indocyber.itsmeandroid.viewmodel.ViewModelFactory;
import com.indocyber.itsmeandroid.viewremastered.blockallcard.adapter.BlockAllCardListAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.INTENT_PARCELABLE;

public class BlockAllCardRemasterActivity extends BaseActivity {

    private ImageButton mImageBtnBack;
    private CheckBox mCheckPlhSmuaKartu;
    private RecyclerView mRecycBlockAllCard;
    private Button mBtnSelanjutnya;
    private BlockAllCardListAdapter adapter;
    private HomeViewModel viewModel;

    @Inject
    ViewModelFactory factory;

    @Override
    protected int layoutRes() {
        return R.layout.activity_block_all_card_remaster;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViewModel();
        initView();
        initRecycleView();
        onClick();
        observeViewModel();
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel.class);
        viewModel.fetchBlockAllCardList();


    }

    private void initRecycleView() {

        adapter = new BlockAllCardListAdapter( this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycBlockAllCard.setLayoutManager(linearLayoutManager);
        mRecycBlockAllCard.setItemAnimator(new DefaultItemAnimator());
        mRecycBlockAllCard.setNestedScrollingEnabled(false);
        mRecycBlockAllCard.setAdapter(adapter);
    }


    private void initView() {
        mImageBtnBack = findViewById(R.id.imageBtnBack);
        mCheckPlhSmuaKartu = findViewById(R.id.checkPlhSmuaKartu);
        mRecycBlockAllCard = findViewById(R.id.recycBlockAllCard);
        mBtnSelanjutnya = findViewById(R.id.btnSelanjutnya);
    }

    private void onClick() {
        mBtnSelanjutnya.setOnClickListener(v -> {
            if (adapter.getDatCheckedTrue().getList().size() > 0) {
                Intent intent = new Intent(this, DetailBlockAllCardActivity.class);
                intent.putExtra(INTENT_PARCELABLE, adapter.getDatCheckedTrue());
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please Select a Card", Toast.LENGTH_SHORT).show();
            }
        });

        mCheckPlhSmuaKartu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.setOnClickListener(v -> {
                    adapter.isSelectedCard(isChecked);
                });
            }
        });

        mImageBtnBack.setOnClickListener(v -> {
            finish();
        });
    }
    private void observeViewModel() {

        viewModel.getBlockCardList().observe(this, blockAllCardModels -> {

            adapter.insertCardList(blockAllCardModels);
        });
    }


  /*  private List<BlockAllCardModel> generateCardList() {
        List<BlockAllCardModel> cardList = new ArrayList<>();
        cardList.add(new BlockAllCardModel(R.drawable.img_blank_kartukredit_bca, "1234123412341234", "Johan Sundstein", "12/25", "Rp 15.000.000", "12/20", "12/21", false));
        cardList.add(new BlockAllCardModel(R.drawable.img_blank_kartukredit_bca, "4321432143214321", "Johan Sundstein", "12/25", "Rp 15.000.000", "12/20", "12/21", false));
        *//*ImageCardModel blockedModel = new ImageCardModel(R.drawable.img_blank_kartukredit_bca, "4321432143214321", "Johan Sundstein", "12/25", "Rp 15.000.000", "12/20", "12/21", false);
        blockedModel.setBillingAddress("Cicalengka, Rt 02 / Rw /04 Kecamatan Jatiluhur");
        blockedModel.setBlockedCard(true);
        cardList.add(blockedModel);*//*
        return cardList;
    }*/

}
