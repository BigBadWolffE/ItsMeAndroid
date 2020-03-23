package com.indocyber.itsmeandroid.viewremastered.blockallcard.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.BlockAllCardModel;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.viewremastered.blockallcard.adapter.BlockAllCardListAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.INTENT_PARCELABLE;

public class BlockAllCardRemasterActivity extends AppCompatActivity {

    private ImageButton mImageBtnBack;
    private CheckBox mCheckPlhSmuaKartu;
    private RecyclerView mRecycBlockAllCard;
    private Button mBtnSelanjutnya;
    private BlockAllCardListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_all_card_remaster);

        initView();
        initData();
        onClick();

    }

    private void initData() {

        adapter = new BlockAllCardListAdapter(generateCardList(), this);
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
            }else {
                Toast.makeText(this, "Please Select a Card", Toast.LENGTH_SHORT).show();
            }
        });

        mCheckPlhSmuaKartu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.setOnClickListener(v ->{
                    adapter.isSelectedCard(isChecked);
                });
            }
        });
    }

    private List<BlockAllCardModel> generatedBlockAllCard() {
        List<BlockAllCardModel> cardList = new ArrayList<>();

        return cardList;
    }

    private List<BlockAllCardModel> generateCardList() {
        List<BlockAllCardModel> cardList = new ArrayList<>();
        cardList.add(new BlockAllCardModel(R.drawable.img_blank_kartukredit_bca, "1234123412341234", "Johan Sundstein", "12/25", "Rp 15.000.000", "12/20", "12/21", false));
        cardList.add(new BlockAllCardModel(R.drawable.img_blank_kartukredit_bca, "4321432143214321", "Johan Sundstein", "12/25", "Rp 15.000.000", "12/20", "12/21", false));
        /*ImageCardModel blockedModel = new ImageCardModel(R.drawable.img_blank_kartukredit_bca, "4321432143214321", "Johan Sundstein", "12/25", "Rp 15.000.000", "12/20", "12/21", false);
        blockedModel.setBillingAddress("Cicalengka, Rt 02 / Rw /04 Kecamatan Jatiluhur");
        blockedModel.setBlockedCard(true);
        cardList.add(blockedModel);*/
        return cardList;
    }

}
