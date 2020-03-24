package com.indocyber.itsmeandroid.viewremastered.promo.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.PromoItemModel;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.internal.Util;

public class ItemPromoAdapter extends RecyclerView.Adapter<ItemPromoAdapter.ItemViewHolder> {
    private List<PromoItemModel> mItemPromo = new ArrayList<>();
    private Context mContext;
    Listener mListener;

    public interface Listener {
        void onClick(PromoItemModel promo);
    }

    public ItemPromoAdapter(List<PromoItemModel> mPromoItem, Context mContext, Listener mListner) {
        this.mItemPromo = mPromoItem;
        this.mContext = mContext;
        this.mListener = mListner;
    }

    public void refreshList(List<PromoItemModel> newList) {
        mItemPromo.clear();
        mItemPromo = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemPromoAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_semua_promo, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemPromoAdapter.ItemViewHolder holder, int position) {
//        holder.imgPromoItem.setImageResource(mItemPromo.get(position).getBanner());
        Glide.with(mContext)
                .load("http://" + mItemPromo.get(position).getUrl())
                .into(holder.imgPromoItem);
        holder.lblAllPromoTittle.setText(mItemPromo.get(position).getTitle());
        holder.lblAllPromoTglPeriode.setText(mItemPromo.get(position).getPeriode());
        holder.mLblJarak.setVisibility(View.GONE);
        holder.mLblDiskon.setVisibility(View.GONE);
        holder.mCardviewPromoItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(mItemPromo.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItemPromo.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgPromoItem)
        ImageView imgPromoItem;
        @BindView(R.id.lblAllPromoTitle)
        TextView lblAllPromoTittle;
        @BindView(R.id.lblAllPromoTglPeriode)
        TextView lblAllPromoTglPeriode;
        @BindView(R.id.cardviewPromoItem)
        CardView mCardviewPromoItem;
        @BindView(R.id.lblJarak)
        TextView mLblJarak;
        @BindView(R.id.lblDiskon)
        TextView mLblDiskon;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
