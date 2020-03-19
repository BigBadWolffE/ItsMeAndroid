package com.indocyber.itsmeandroid.viewremastered.promo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ItemShareModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SharePromoAdapter extends RecyclerView.Adapter<SharePromoAdapter.ItemViewHolder> {
    private List<ItemShareModel> itemShareModelList = new ArrayList<>();
    private Context mContext;
    Listener mListener;
    public interface Listener {
        void onClick(int position);
    }

    public SharePromoAdapter(List<ItemShareModel> itemShareModelList, Context mContext, Listener mListener){
        this.itemShareModelList = itemShareModelList;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public SharePromoAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_dialog_item, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SharePromoAdapter.ItemViewHolder holder, int position) {
        holder.mImageShare.setBackground(itemShareModelList.get(position).icon);
        holder.mLblTitle.setText(itemShareModelList.get(position).app);
    }

    @Override
    public int getItemCount() {
        return itemShareModelList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgShare)
        ImageView mImageShare;
        @BindView(R.id.lblTittle)
        TextView mLblTitle;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
