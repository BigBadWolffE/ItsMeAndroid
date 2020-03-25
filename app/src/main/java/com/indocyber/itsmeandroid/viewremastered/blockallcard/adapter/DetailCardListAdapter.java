package com.indocyber.itsmeandroid.viewremastered.blockallcard.adapter;

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
import com.indocyber.itsmeandroid.model.BlockAllCardModel;

import java.util.ArrayList;
import java.util.List;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.INTENT_ID;

/*
 *
 *
 *@Author
 *@Version
 */
public class DetailCardListAdapter extends RecyclerView.Adapter<DetailCardListAdapter.CardViewHolder> {
    private List<BlockAllCardModel> cardList;
    private Context context;

    public DetailCardListAdapter(List<BlockAllCardModel> cardList, Context context) {
        this.cardList = cardList;
        this.context = context;
    }

    public void refreshCardList(List<BlockAllCardModel> newCardList) {
        cardList.clear();
        cardList = newCardList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_card_list_item, parent, false);
        CardViewHolder viewHolder = new CardViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        BlockAllCardModel model = cardList.get(position);
        holder.txtNamaKartu.setText(model.getNameCard());
        holder.txtNomorKartu.setText(onCardNumberChange(model.getNumberCard()));
        //holder.cardImage.setImageResource(cardList.get(position).getImage());

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

    @Override
    public int getItemCount() {
        return cardList.size();
    }


    public class CardViewHolder extends RecyclerView.ViewHolder {

        TextView txtNamaKartu;
        TextView txtNomorKartu;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);


            txtNamaKartu = itemView.findViewById(R.id.txtNamaKartu);
            txtNomorKartu = itemView.findViewById(R.id.txtNomorKartu);
        }


    }


}
