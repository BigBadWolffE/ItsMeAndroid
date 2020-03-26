package com.indocyber.itsmeandroid.viewremastered.historytransaction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.PromoItemModel;
import com.indocyber.itsmeandroid.viewremastered.promo.Adapter.PromoItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class HistoryTransactionActivityRemastered extends AppCompatActivity implements HistoryTransactionAdapter.Listener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView rcHistoryTransaction;
    private List<HistoryTransactionModel> mHistoryTransaction = new ArrayList<>();
    private HistoryTransactionAdapter mHistoryTransactionAdapter;
    public static ImageView backButton;

    private String[] tanggal = {

            "12 Januari 2020, 18.00 WIB",
            "11 Januari 2020, 08.00 WIB",
            "10 Januari 2020, 12.00 WIB",
            "10 Januari 2020, 08.00 WIB"
    };
    private String[] title = {

            "Pembelian Pulsa No 081728813566 berhasil",
            "Pembelian Pulsa No 08128111551 berhasil",
            "Pembelian Pulsa No 08128555331 berhasil",
            "Pembelian Pulsa No 08128555331 berhasil"

    };
    private String[] detail = {

            "Pembelian Pulsa Rp 100.000,00 ke no 081728813566 berhasil",
            "Pembelian Pulsa Rp 50.000,00 ke no 081728813566 berhasil",
            "Pembelian Pulsa Rp 100.000,00 ke no 081728813566 berhasil",
            "Pembelian Pulsa Rp 100.000,00 ke no 081728813566 berhasil",
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_transaction_remastered);

        mHistoryTransaction = initialize();
        rcHistoryTransaction = findViewById(R.id.rc_detail_transaction);
        mHistoryTransactionAdapter = new HistoryTransactionAdapter(initialize(),HistoryTransactionActivityRemastered.this, this);
        LinearLayoutManager horizontalLayoutManager =
                new LinearLayoutManager(HistoryTransactionActivityRemastered.this);
        rcHistoryTransaction.setLayoutManager(horizontalLayoutManager);
        rcHistoryTransaction.setAdapter(mHistoryTransactionAdapter);
        backButton = findViewById(R.id.imageView5);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private List<HistoryTransactionModel> initialize() {
        List<HistoryTransactionModel> allList = new ArrayList<>();
        for (int i=0 ; i<tanggal.length ; i++) {
            allList.add(new HistoryTransactionModel(tanggal[i], title[i], detail[i]));
        }
        return allList;
    }

    @Override
    public void onClick(int position) {

    }
}
