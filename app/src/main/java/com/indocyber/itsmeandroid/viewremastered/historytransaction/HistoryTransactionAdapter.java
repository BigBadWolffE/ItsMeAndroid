package com.indocyber.itsmeandroid.viewremastered.historytransaction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indocyber.itsmeandroid.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryTransactionAdapter extends RecyclerView.Adapter<HistoryTransactionAdapter.ItemViewHolder> {

    private List<HistoryTransactionModel> mHistoryTransaction = new ArrayList<>();
    private Context mContext;
    Listener mlistener;

    public interface Listener {
        void onClick(int position);
    }

    public HistoryTransactionAdapter(List<HistoryTransactionModel> mHistoryTransaction, Context mContext, Listener mlistener) {
        this.mHistoryTransaction = mHistoryTransaction;
        this.mContext = mContext;
        this.mlistener = mlistener;
    }

    @NonNull
    @Override
    public HistoryTransactionAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_transaction_item, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryTransactionAdapter.ItemViewHolder holder, final int position) {
        holder.tanggal.setText(mHistoryTransaction.get(position).getTanggal());
        holder.title.setText(mHistoryTransaction.get(position).getTitle());
        holder.isi.setText(mHistoryTransaction.get(position).getDetail());
    }

    @Override
    public int getItemCount() {
        return mHistoryTransaction.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tanggal,title,isi;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tanggal = itemView.findViewById(R.id.tanggal_item);
            title = itemView.findViewById(R.id.title_item);
            isi = itemView.findViewById(R.id.isi_item);
        }
    }
}
