package com.indocyber.itsmeandroid.view.promo.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indocyber.itsmeandroid.R;

import java.util.ArrayList;
import java.util.List;

public class PromoMenuAdapter extends RecyclerView.Adapter<PromoMenuAdapter.MenuViewHolder> {

    private List<Integer> mPromoMenuList = new ArrayList<>();
    private Context context;
    private Listener mlistener;

    public interface Listener {
        void onClick(int position);
    }

    public PromoMenuAdapter(List<Integer> mPromoMenuList, Context context, Listener listener) {
        this.mPromoMenuList = mPromoMenuList;
        this.context = context;
        this.mlistener = listener;
    }

    public void updateData(List<Integer> mNewPromoMenuList) {
        mPromoMenuList.clear();
        mPromoMenuList.addAll(mNewPromoMenuList);
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View promoMenuView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_promo_menu, parent, false);
        MenuViewHolder view = new MenuViewHolder(promoMenuView);
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, final int position) {
        holder.image.setImageResource(mPromoMenuList.get(position));
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistener.onClick(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPromoMenuList.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgPromoMenu);
        }
    }
}
