package com.indocyber.itsmeandroid.viewremastered.promo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.PromoItemModel;

import java.util.ArrayList;
import java.util.List;

public class PromoItemAdapter extends RecyclerView.Adapter<PromoItemAdapter.ItemViewHolder> {
    private List<PromoItemModel> mPromoItem = new ArrayList<>();
    private Context mContext;
    Listener mlistener;

    public interface Listener {
        void onClick(int position);
    }

    public PromoItemAdapter(List<PromoItemModel> mPromoItem, Context mContext, Listener mlistener) {
        this.mPromoItem = mPromoItem;
        this.mContext = mContext;
        this.mlistener = mlistener;
    }

    @NonNull
    @Override
    public PromoItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_promo_all, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PromoItemAdapter.ItemViewHolder holder, final int position) {
        holder.banner.setImageResource(mPromoItem.get(position).getBanner());
        holder.title.setText(mPromoItem.get(position).getTitle());
        holder.desc.setText(mPromoItem.get(position).getDesc());
        holder.periode.setText(mPromoItem.get(position).getPeriode());
        holder.mauDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPromoItem.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        CardView promoItem;
        TextView title, desc, periode;
        ImageView banner;
        Button mauDong;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            promoItem = itemView.findViewById(R.id.cardviewPromoItem);
            banner = itemView.findViewById(R.id.imgPromoItem);
            title = itemView.findViewById(R.id.lblAllPromoTitle);
            desc = itemView.findViewById(R.id.lblAllPromoDesc);
            periode = itemView.findViewById(R.id.lblAllPromoTglPeriode);
            mauDong = itemView.findViewById(R.id.btnMauDong);
        }
    }
}
