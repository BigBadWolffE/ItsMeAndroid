package com.indocyber.itsmeandroid.viewremastered.kartuku.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.viewremastered.billinginfo.BillingInfo;
import com.indocyber.itsmeandroid.viewremastered.blockkartu.BlockKartu;
import com.indocyber.itsmeandroid.viewremastered.editcard.activity.editkartu;
import com.indocyber.itsmeandroid.viewremastered.morecard.MoreCardRemasteredActivity;

import java.util.List;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.INTENT_ID;

/*
 *
 *
 *@Author
 *@Version
 */
public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardViewHolder> {
    private List<ImageCardModel> cardList;
    private Context context;

    public CardListAdapter(List<ImageCardModel> cardList, Context context) {
        this.cardList = cardList;
        this.context = context;
    }

    public void refreshCardList(List<ImageCardModel> newCardList) {
        cardList.clear();
        cardList = newCardList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_list_item, parent, false);
        CardViewHolder viewHolder = new CardViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        ImageCardModel model = cardList.get(position);
        View.OnClickListener listener = view -> {
            Intent intent = new Intent(context, MoreCardRemasteredActivity.class);
//            intent.putExtra("cardNumber", model.getNumberCard());
//            intent.putExtra("cardHolder", model.getNameCard());
//            intent.putExtra("cardImage", model.getImage());
//            intent.putExtra("expiryDate", model.getExpireCard());
//            intent.putExtra("billingAddress", model.getBillingAddress());
            intent.putExtra(INTENT_ID, model);
            context.startActivity(intent);
        };
        View.OnClickListener cardListener = view -> {
            Intent intent = new Intent(context, MoreCardRemasteredActivity.class);
            intent.putExtra("cardNumber", model.getNumberCard());
            intent.putExtra("cardHolder", model.getNameCard());
            intent.putExtra("cardImage", model.getImage());
            intent.putExtra("expiryDate", model.getExpireCard());
            intent.putExtra("billingAddress", model.getBillingAddress());
            context.startActivity(intent);
        };
        holder.bind(model, listener, cardListener);
        holder.cardImage.setImageResource(cardList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        ImageView cardImage;
        LinearLayout blockedLayout;
        ImageView cardMenuButton;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.cardListImage);
            cardMenuButton = itemView.findViewById(R.id.btnCardMoreMenu);
            blockedLayout = itemView.findViewById(R.id.blockLayout);
        }

        public void bind(ImageCardModel model, View.OnClickListener cardMenuListener, View.OnClickListener cardListener) {
            cardImage.setImageResource(R.drawable.img_bca_card_template);
            cardImage.setOnClickListener(cardListener);
            cardMenuButton.setOnClickListener(cardMenuListener);
            if (model.isBlockedCard()) {
                blockedLayout.setVisibility(View.VISIBLE);
                cardMenuButton.setVisibility(View.GONE);
            } else {
                blockedLayout.setVisibility(View.GONE);
                cardMenuButton.setVisibility(View.VISIBLE);
            }
        }
    }
}
