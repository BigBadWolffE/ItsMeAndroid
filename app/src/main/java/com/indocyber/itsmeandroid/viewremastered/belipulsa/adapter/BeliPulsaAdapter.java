package com.indocyber.itsmeandroid.viewremastered.belipulsa.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.Pulsa;

import java.util.ArrayList;
import java.util.List;


public class BeliPulsaAdapter extends RecyclerView.Adapter<BeliPulsaAdapter.ViewHolder> {

    private List<Pulsa> list = new ArrayList<>();
    private Context context;
    private onItemClickListener listener;

    public BeliPulsaAdapter(Context context) {
        this.context = context;

    }

    public void SetItemOnclickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public void setList(List<Pulsa> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BeliPulsaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BeliPulsaAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_layout_beli_pulsa, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        Pulsa data = list.get(position);

        holder.txtHargaPulsa.setText("Rp "+data.getHargaPulsa());
        holder.txtMasaBerlaku.setText("Masa Berlaku "+data.getMasaBerlaku());
        holder.txtHargaBayar.setText(data.getHargaBayar());

        holder.itemView.setOnClickListener(v ->{
            if (listener != null){
                listener.onItemClick(position,data);
            }
        });

    }


    @Override
    public int getItemCount() {
        return this.list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView txtHargaPulsa;
        private TextView txtMasaBerlaku;
        private TextView txtHargaBayar;



        public ViewHolder(View itemView) {
            super(itemView);


            txtHargaPulsa = itemView.findViewById(R.id.txtHargaPulsa);
            txtMasaBerlaku = itemView.findViewById(R.id.txtMasaBerlaku);
            txtHargaBayar = itemView.findViewById(R.id.txtHargaBayar);



        }
    }

    public interface onItemClickListener {
        void onItemClick(int position, Pulsa pulsa);

    }


}
