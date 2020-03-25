package com.indocyber.itsmeandroid.viewremastered.blockallcard.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.BlockAllCardModel;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.model.ListBlockAllCard;

import java.util.ArrayList;
import java.util.List;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.INTENT_ID;

/*
 *
 *
 *@Author
 *@Version
 */
public class BlockAllCardListAdapter extends RecyclerView.Adapter<BlockAllCardListAdapter.CardViewHolder> {
    private List<BlockAllCardModel> cardList = new ArrayList<>();
    private Context context;

    public BlockAllCardListAdapter( Context context) {
        this.context = context;
    }
    public void insertCardList(List<BlockAllCardModel> newCardList) {
        cardList.clear();
        cardList = newCardList;
        notifyDataSetChanged();
    }

    public void refreshCardList(List<BlockAllCardModel> newCardList) {
        cardList.clear();
        cardList = newCardList;
        notifyDataSetChanged();
    }
    public void isSelectedCard(boolean isChecked){

        for (int i = 0; i < cardList.size(); i++){
            cardList.get(i).setCheckCard(isChecked);

        }
       notifyDataSetChanged();

    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.block_all_card_list_item, parent, false);
        CardViewHolder viewHolder = new CardViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        BlockAllCardModel model = cardList.get(position);

        if (model.isCheckCard()){
            holder.checkCard.setChecked(true);
        }else {
            holder.checkCard.setChecked(false);
        }
        holder.cardHolderLabel.setText(model.getNameCard());
        holder.cardExpiryLabel.setText(model.getExpireCard());
        holder.cardNumberLabel.setText(onCardNumberChange(model.getNumberCard()));

        holder.checkCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                buttonView.setOnClickListener(v -> {

                    model.setCheckCard(isChecked);
                });

            }
        });


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

    public ListBlockAllCard getDatCheckedTrue() {
        Log.e("BlockAllCard", cardList.size() + "");

        List<BlockAllCardModel> list = new ArrayList<>();
        for (int i = 0; i < cardList.size(); i++) {
            if (cardList.get(i).isCheckCard()) {
                list.add(cardList.get(i));
            }
        }
        ListBlockAllCard listBlockAllCard = new ListBlockAllCard(list);
        Log.e("BlockAllCard", list.size() + "");
        return listBlockAllCard;
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        ImageView cardImage;
        LinearLayout blockedLayout;
        //ImageView cardMenuButton;
        TextView cardNumberLabel;
        TextView cardHolderLabel;
        TextView cardExpiryLabel;
        private CheckBox checkCard;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.cardListImage);
            //cardMenuButton = itemView.findViewById(R.id.btnCardMoreMenu);
            cardNumberLabel = itemView.findViewById(R.id.lblCcNumber);
            cardHolderLabel = itemView.findViewById(R.id.lblCardHolder);
            cardExpiryLabel = itemView.findViewById(R.id.lblExpiry);
            blockedLayout = itemView.findViewById(R.id.blockLayout);
            checkCard = itemView.findViewById(R.id.checkCard);
        }


        public void formatCreditCard() {
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
            for (int i = 0; i < pad; i++) {
                padding.append(" ");
            }

            String paddedText = number + "";
            return paddedText.substring(0, 4) + padding + "XXXX" + padding
                    + "XXXX" + padding + paddedText.substring(12, 16);
        }
    }
    private String onCardNumberChange(final String text){
        StringBuilder paddedText = new StringBuilder(text + "");
        for(int i = 4; i <= 11; i++){
            paddedText.replace(i,i,"X");
        }

        String updatedText = paddedText.substring(0, 4) + "   " + paddedText.substring(4, 8) + "   "
                + paddedText.substring(8, 12) + "   " + paddedText.substring(12, 16);


       return updatedText;
    }

}
