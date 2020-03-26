package com.indocyber.itsmeandroid.viewremastered.promo.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.EditTag;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.model.PromoCollectionModel;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;
import com.indocyber.itsmeandroid.utilities.core.Animations;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PromoCollectionAdapter extends RecyclerView.Adapter<PromoCollectionAdapter.ItemViewHolder> {
    private List<ImageCardModel> mPromoColl = new ArrayList<>();
    private Activity mContext;
    public static int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 200;

    public PromoCollectionAdapter(List<ImageCardModel> mPromoColl, Activity mContext) {
        this.mPromoColl = mPromoColl;
        this.mContext = mContext;
    }

    public void refreshCardList(List<ImageCardModel> newCardList) {
        mPromoColl.clear();
        mPromoColl.addAll(newCardList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PromoCollectionAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_promo_collection, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PromoCollectionAdapter.ItemViewHolder holder, int position) {
        ImageCardModel card = mPromoColl.get(position);
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.bg_gradient_soft)
                .error(R.drawable.bg_gradient_soft)
                .priority(Priority.HIGH);

        Glide.with(mContext)
                .load(mPromoColl.get(position).getImage())
                .apply(options)
                .into(holder.cardType);

        holder.cardHolder.setText(mPromoColl.get(position).getNameCard());
        holder.cardNumber.setText(
                UtilitiesCore.cardNumberSpacing(mPromoColl.get(position).getNumberCard(), 3));
        holder.cardExpiry.setText(mPromoColl.get(position).getExpireCard());

        if (card.isBlockedCard()) {
            holder.blockedLayout.setVisibility(View.VISIBLE);
            holder.blockedLayout.bringToFront();
        }

    }

    private String onCardNumberChange(String text){
        String paddedText = text;
        StringBuilder cuttingText = new StringBuilder(paddedText);

        for (int i=0 ; i<paddedText.length() ; i++) {
            if (i>=4 && i<=13) {
                if (paddedText.charAt(i) != ' ') {
                    cuttingText.setCharAt(i, 'X');
                }
            }
        }

        return cuttingText.toString();
    }

    @Override
    public int getItemCount() {
        return mPromoColl.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView cardType;
        TextView cardNumber, cardHolder, cardExpiry, cardHolderLabel, cardExpiryLabel;
        private RecyclerView recycle_EditTags;
        private RelativeLayout cardLayout, blockedLayout;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            cardType = itemView.findViewById(R.id.imgCollectionPromoCard);
            cardHolder = itemView.findViewById(R.id.lblPromoCollectionCardHolder);
            cardNumber = itemView.findViewById(R.id.lblPromoCollectionCardNumber);
            cardExpiry = itemView.findViewById(R.id.lblPromoCollectionCardExpiry);
            cardExpiryLabel = itemView.findViewById(R.id.lblPromoCollectionCardExpiryLabel);
            cardHolderLabel = itemView.findViewById(R.id.lblPromoCollectionCardHolderLabel);
            recycle_EditTags = itemView.findViewById(R.id.recycle_EditTags);
            cardLayout = itemView.findViewById(R.id.layoutPromoCollectionCardType);
            blockedLayout = itemView.findViewById(R.id.layoutPromoCollectionCardBlocked);
        }
    }
}
