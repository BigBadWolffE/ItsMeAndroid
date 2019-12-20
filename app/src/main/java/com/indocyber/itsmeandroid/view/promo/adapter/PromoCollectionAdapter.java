package com.indocyber.itsmeandroid.view.promo.adapter;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.PromoCollectionModel;

import java.util.ArrayList;
import java.util.List;

public class PromoCollectionAdapter extends RecyclerView.Adapter<PromoCollectionAdapter.ItemViewHolder> {
    private List<PromoCollectionModel> mPromoColl = new ArrayList<>();
    private Context mContext;
    private ItemViewHolder mViewHolder;

    public PromoCollectionAdapter(List<PromoCollectionModel> mPromoColl, Context mContext) {
        this.mPromoColl = mPromoColl;
        this.mContext = mContext;
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
        this.mViewHolder = holder;
        holder.cardType.setImageResource(mPromoColl.get(position).getCardType());
        holder.cardHolder.setText(mPromoColl.get(position).getCardHolder());
        holder.cardNumber.setText(onCardNumberChange(mPromoColl.get(position).getCardNumber()));
        holder.cardExpiry.setText(mPromoColl.get(position).getCardExpiry());
        if (position == 3) {
            formatCardLabel(holder);
            notifyDataSetChanged();
        }
    }

    private void formatCardLabel(ItemViewHolder holder) {
        int[] position = {0,0};
        holder.cardType.getLocationOnScreen(position);
        int paddingLeft = (holder.cardType.getWidth() * 8 / 100);
        int startYaxis  = (holder.cardType.getHeight() / 2);

        holder.cardNumber.setX(position[0] + paddingLeft);
        holder.cardNumber.setY(startYaxis + mContext.getResources().getDimension(R.dimen.spacing_medium));
        holder.cardNumber.bringToFront();

        holder.cardHolderLabel.setX(position[0] + paddingLeft);
        holder.cardHolderLabel.setY(holder.cardNumber.getY() + holder.cardNumber.getHeight()
                + mContext.getResources().getDimension(R.dimen.spacing_large));

        holder.cardExpiryLabel.setX(position[0] + paddingLeft
                + holder.cardNumber.getWidth() - holder.cardExpiryLabel.getWidth());
        holder.cardExpiryLabel.setY(holder.cardNumber.getY() + holder.cardNumber.getHeight()
                + mContext.getResources().getDimension(R.dimen.spacing_large));
    }

    private String onCardNumberChange(String text){
        String paddedText = text;
        StringBuilder cuttingText = new StringBuilder(paddedText);

        for (int i=0 ; i<paddedText.length() ; i++) {
            if (i>=3 && i<=11) {
                cuttingText.setCharAt(i, 'X');
            }
        }

        String updatedText = cuttingText.substring(0, 4) + "   " + cuttingText.substring(4, 8) + "   "
                + cuttingText.substring(8, 12) + "   " + cuttingText.substring(12, 16);

        return updatedText;
    }

    @Override
    public int getItemCount() {
        return mPromoColl.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView cardType;
        TextView cardNumber, cardHolder, cardExpiry, cardHolderLabel, cardExpiryLabel;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            cardType = itemView.findViewById(R.id.imgCollectionPromoCard);
            cardHolder = itemView.findViewById(R.id.lblPromoCollectionCardHolder);
            cardNumber = itemView.findViewById(R.id.lblPromoCollectionCardNumber);
            cardExpiry = itemView.findViewById(R.id.lblPromoCollectionCardExpiry);
            cardExpiryLabel = itemView.findViewById(R.id.lblPromoCollectionCardExpiryLabel);
            cardHolderLabel = itemView.findViewById(R.id.lblPromoCollectionCardHolderLabel);
        }
    }
}
