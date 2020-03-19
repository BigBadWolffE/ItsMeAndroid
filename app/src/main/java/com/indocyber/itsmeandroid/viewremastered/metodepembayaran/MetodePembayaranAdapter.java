package com.indocyber.itsmeandroid.viewremastered.metodepembayaran;

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

public class MetodePembayaranAdapter extends RecyclerView.Adapter<MetodePembayaranAdapter.ItemViewHolder> {
    private List<MetodePembayaranModel> mMetodePembayaran = new ArrayList<>();
    private Context mContext;
    Listener mlistener;

    public interface Listener {
        void onClick(int position);
    }

    public MetodePembayaranAdapter(List<MetodePembayaranModel> mMetodePembayaran, Context mContext, Listener mlistener) {
        this.mMetodePembayaran = mMetodePembayaran;
        this.mContext = mContext;
        this.mlistener = mlistener;
    }

    @NonNull
    @Override
    public MetodePembayaranAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.metode_pembayaran_item_remastered, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MetodePembayaranAdapter.ItemViewHolder holder, final int position) {
        holder.namaBank.setText(mMetodePembayaran.get(position).getBankNames());
        holder.nomorKartu.setText(mMetodePembayaran.get(position).getCardId());

    }

    @Override
    public int getItemCount() {
        return mMetodePembayaran.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView namaBank,nomorKartu;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            namaBank = itemView.findViewById(R.id.namabank);
            nomorKartu = itemView.findViewById(R.id.nomorkartu);

        }
    }
}
