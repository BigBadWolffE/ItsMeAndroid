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

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ItemPromoNearbyModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemPromoNearbyAdapter extends RecyclerView.Adapter<ItemPromoNearbyAdapter.ItemViewHolder> {
    private List<ItemPromoNearbyModel> mItemPromoNearbyList = new ArrayList<>();
    private Context mContext;
    Listener mListener;

    public interface Listener {
        void ItemNearbyonClick(ItemPromoNearbyModel itemPromoNearbyModel);
    }

    public ItemPromoNearbyAdapter(List<ItemPromoNearbyModel> mItemPromoNearbyList, Context mContext, Listener mListner) {
        this.mItemPromoNearbyList = mItemPromoNearbyList;
        this.mContext = mContext;
        this.mListener = mListner;
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
        holder.imgPromoItem.setImageResource(mItemPromoNearbyList.get(position).getBanner());
        holder.lblAllPromoTittle.setText(mItemPromoNearbyList.get(position).getTitle());
        holder.lblAllPromoTglPeriode.setText(mItemPromoNearbyList.get(position).getPeriode());
        holder.mLblJarak.setText(mItemPromoNearbyList.get(position).getJarak() + " km");
        holder.mLblDiskon.setText(mItemPromoNearbyList.get(position).getDiskon() + " %");

        if (!mItemPromoNearbyList.get(position).getDiskon().equals("")) {
            holder.mLblDiskon.setVisibility(View.VISIBLE);
            holder.mLblJarak.setVisibility(View.GONE);
        } else {
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
