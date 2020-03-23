package com.indocyber.itsmeandroid.viewremastered.metodepembayaran;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indocyber.itsmeandroid.R;

import java.util.ArrayList;
import java.util.List;

public class TambahPembayaranAdapter extends RecyclerView.Adapter<TambahPembayaranAdapter.ItemViewHolder> {

    private List<TambahPembayaranModel> mPembayaran = new ArrayList<>();
    private Context mContext;
    Listener mlistener;
    public static boolean isSelectedAll;


    public interface Listener {
        void onClick(int position);
    }

    public TambahPembayaranAdapter(List<TambahPembayaranModel> mPembayaran, Context mContext, Listener mlistener) {
        this.mPembayaran = mPembayaran;
        this.mContext = mContext;
        this.mlistener = mlistener;
    }

    public void selectAll(){
        isSelectedAll=true;
        notifyDataSetChanged();
    }
    public void unselectall(){
        isSelectedAll=false;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TambahPembayaranAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_tambah_kartu_item, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TambahPembayaranAdapter.ItemViewHolder holder, final int position) {
        holder.kartu.setImageResource(mPembayaran.get(position).getBanner());
        final TambahPembayaranModel list = mPembayaran.get(position);
        if (!isSelectedAll){
            holder.checkBox.setChecked(false);
        }
        else {
            holder.checkBox.setChecked(true);
        }

    }

    @Override
    public int getItemCount() {
        return mPembayaran.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView kartu;
        CheckBox checkBox;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checked_item);
            kartu = itemView.findViewById(R.id.img_kartu);

        }
    }
}
