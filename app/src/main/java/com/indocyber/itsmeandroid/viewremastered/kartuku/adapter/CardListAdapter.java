package com.indocyber.itsmeandroid.viewremastered.kartuku.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.viewremastered.billinginfo.BillingInfo;
import com.indocyber.itsmeandroid.viewremastered.blockkartu.BlockKartu;
import com.indocyber.itsmeandroid.viewremastered.editcard.activity.editkartu;
import com.indocyber.itsmeandroid.viewremastered.morecard.activity.MoreCardRemasteredActivity;


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
        notifyDataSetChanged();
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
        holder.bind(model, listener);
        holder.cardImage.setImageResource(cardList.get(position).getImage());
        final View view = holder.itemView;
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                // Do what you need to do here.
                // Then remove the listener:
                holder.formatCreditCard();
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }

        });
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        ImageView cardImage;
        LinearLayout blockedLayout;
        ImageView cardMenuButton;
        TextView cardNumberLabel;
        TextView cardHolderLabel;
        TextView cardExpiryLabel;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.cardListImage);
            cardMenuButton = itemView.findViewById(R.id.btnCardMoreMenu);
            cardNumberLabel = itemView.findViewById(R.id.lblCcNumber);
            cardHolderLabel = itemView.findViewById(R.id.lblCardHolder);
            cardExpiryLabel = itemView.findViewById(R.id.lblExpiry);
            blockedLayout = itemView.findViewById(R.id.blockLayout);
        }

        public void bind(ImageCardModel model, View.OnClickListener cardMenuListener) {
            cardImage.setImageResource(R.drawable.img_bca_card_template);
            cardMenuButton.setOnClickListener(cardMenuListener);
            cardNumberLabel.setText(padHalfCardNumber(model.getNumberCard(), 3));
            cardHolderLabel.setText(model.getNameCard());
            cardExpiryLabel.setText(model.getExpireCard());
            if (model.isBlockedCard()) {
                blockedLayout.setVisibility(View.VISIBLE);
                cardMenuButton.setVisibility(View.GONE);
                cardNumberLabel.setVisibility(View.GONE);
                cardHolderLabel.setVisibility(View.GONE);
                cardExpiryLabel.setVisibility(View.GONE);
            } else {
                blockedLayout.setVisibility(View.GONE);
                cardMenuButton.setVisibility(View.VISIBLE);
            }
        }

        public void formatCreditCard(){
            int[] position = {0, 0};
            cardImage.getLocationOnScreen(position);

            int paddingLeft = (cardImage.getWidth() * 8 / 100);
            int startYAxis = (cardImage.getHeight() / 2);

            cardNumberLabel.setX(paddingLeft);
            cardNumberLabel.setY(startYAxis + context.getResources().getDimension(R.dimen.spacing_medium));
            cardNumberLabel.bringToFront();
            cardHolderLabel.setX(paddingLeft);
            cardHolderLabel.setY(cardImage.getHeight() * 80 / 100);
            cardExpiryLabel.setX(cardImage.getWidth() / 2);
            cardExpiryLabel.setY(cardImage.getHeight() * 70 / 100);
        }

        private String padHalfCardNumber(String number, int pad) {
            if (number.length() < 16) {
                return "";
            }

            StringBuilder padding = new StringBuilder();
            for(int i = 0; i < pad; i++){
                padding.append(" ");
            }

            String paddedText = number + "";
            return paddedText.substring(0, 4) + padding + "XXXX" + padding
                    + "XXXX" + padding + paddedText.substring(12, 16);
        }
    }
}
