package com.indocyber.itsmeandroid.viewremastered.promo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ImageCardModel;

import java.util.List;

public class CreditCardAdapter extends RecyclerView.Adapter<CreditCardAdapter.ItemViewHolder> {
    private List<ImageCardModel> cardList;
    private Context context;
    private Listener mListener;

    public CreditCardAdapter(List<ImageCardModel> cardList, Context context, Listener mListener) {
        this.cardList = cardList;
        this.context = context;
        this.mListener = mListener;
    }

    public void refreshCardList(List<ImageCardModel> newCardList) {
        cardList.clear();
        cardList = newCardList;
    }

    public interface Listener{
        void MoreCardonClick(ImageCardModel imgCardModel);
    }
    @NonNull
    @Override
    public CreditCardAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.credit_card_page_layout, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CreditCardAdapter.ItemViewHolder holder, int position) {
        holder.bind(cardList.get(position));
        holder.cardImage.setImageResource(cardList.get(position).getImage());
        holder.cardMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.MoreCardonClick(cardList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView cardImage;
        ImageView cardMenuButton;
        public ItemViewHolder(@NonNull View itemView) {
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
