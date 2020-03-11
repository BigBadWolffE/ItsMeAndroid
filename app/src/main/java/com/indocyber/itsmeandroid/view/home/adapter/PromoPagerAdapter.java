package com.indocyber.itsmeandroid.view.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.PromoItemModel;

import java.util.List;

/*
 *
 *
 *@Author
 *@Version
 */
public class PromoPagerAdapter extends RecyclerView.Adapter<PromoPagerAdapter.PromoViewHolder> {

    List<PromoItemModel> listPromo;
    Context context;

    public void refreshPromoList(List<PromoItemModel> newPromoList) {
        listPromo.clear();
        listPromo = newPromoList;
    }

    public PromoPagerAdapter(List<PromoItemModel> listPromo, Context context) {
        this.listPromo = listPromo;
        this.context = context;
    }

    @NonNull
    @Override
    public PromoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.promo_pager_layout, parent, false);
        PromoViewHolder viewHolder = new PromoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PromoViewHolder holder, int position) {
        holder.bind(listPromo.get(position));
    }

    @Override
    public int getItemCount() {
        return listPromo.size();
    }

    public class PromoViewHolder extends RecyclerView.ViewHolder {

        public ImageView promoItemImage;
        public ImageView morePromoButton;

        public PromoViewHolder(@NonNull View itemView) {
            super(itemView);
            promoItemImage = itemView.findViewById(R.id.promoListImage);
            morePromoButton = itemView.findViewById(R.id.btnMorePromo);
        }

        public void bind(PromoItemModel model) {
            promoItemImage.setImageResource(R.drawable.img_banner_starbuck);
            morePromoButton.setOnClickListener(view ->
                    Toast.makeText(context, "More Promo", Toast.LENGTH_SHORT).show());
        }
    }
}
