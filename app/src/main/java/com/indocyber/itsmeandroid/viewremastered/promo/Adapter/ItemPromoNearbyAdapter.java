package com.indocyber.itsmeandroid.viewremastered.promo.Adapter;

import android.content.Context;
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
import com.indocyber.itsmeandroid.model.ItemPromoNearbyModel;
import com.indocyber.itsmeandroid.model.PromoItemModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemPromoNearbyAdapter extends RecyclerView.Adapter<ItemPromoNearbyAdapter.ItemViewHolder> {
    private List<PromoItemModel> mItemPromoNearbyList = new ArrayList<>();
    private Context mContext;
    Listener mListener;
    int promoType;
    public final static int NEARBY = 0;
    public final static int SPECIAL_DISCOUNT = 1;

    public interface Listener {
        void ItemNearbyonClick(PromoItemModel promoItemModel);
    }

    public ItemPromoNearbyAdapter(List<PromoItemModel> mItemPromoNearbyList, Context mContext, Listener mListner, int promoType) {
        this.mItemPromoNearbyList = mItemPromoNearbyList;
        this.mContext = mContext;
        this.mListener = mListner;
        this.promoType = promoType;
    }

    public void refreshList(List<PromoItemModel> newList) {
        mItemPromoNearbyList.clear();
        mItemPromoNearbyList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_semua_promo, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Glide.with(mContext)
                .load("http://" + mItemPromoNearbyList.get(position).getUrl())
                .into(holder.imgPromoItem);
//        holder.imgPromoItem.setImageResource(mItemPromoNearbyList.get(position).getBanner());
        holder.lblAllPromoTittle.setText(mItemPromoNearbyList.get(position).getTitle());
        holder.lblAllPromoTglPeriode.setText(mItemPromoNearbyList.get(position).getPeriode());
        String distance = mItemPromoNearbyList.get(position).getDistance().substring(0, 3);
        holder.mLblJarak.setText(distance + " km");
        String diskon = mItemPromoNearbyList.get(position).getTitle().replaceAll("\\D+","");
        holder.mLblDiskon.setText(diskon + " %");

        if (promoType == SPECIAL_DISCOUNT) {
            holder.mLblDiskon.setVisibility(View.VISIBLE);
            holder.mLblJarak.setVisibility(View.GONE);
        } else if (promoType == NEARBY){
            holder.mLblDiskon.setVisibility(View.GONE);
            holder.mLblJarak.setVisibility(View.VISIBLE);
        }
        holder.mCardviewPromoItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.ItemNearbyonClick(mItemPromoNearbyList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemPromoNearbyList.size();
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
