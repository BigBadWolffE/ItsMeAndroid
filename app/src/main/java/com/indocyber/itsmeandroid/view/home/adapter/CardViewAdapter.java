package com.indocyber.itsmeandroid.view.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ImageCardModel;

import java.util.List;

/*
 *
 *
 *@Author
 *@Version
 */
public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.CardViewHolder> {
    private List<ImageCardModel> cardList;
    private Context context;

    public CardViewAdapter(List<ImageCardModel> cardList, Context context) {
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
                .inflate(R.layout.card_pager_layout, parent, false);
        CardViewHolder viewHolder = new CardViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.bind(cardList.get(position));
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        ImageView cardImage;
        ImageView cardMenuButton;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.cardListImage);
            cardMenuButton = itemView.findViewById(R.id.btnCardMoreMenu);
        }

        public void bind(ImageCardModel model) {
            cardImage.setImageResource(R.drawable.img_bca_card_template);
            cardMenuButton.setOnClickListener(view ->
                    Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show());
        }
    }
}
