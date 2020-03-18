package com.indocyber.itsmeandroid.viewremastered.kartuku.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
public class CardFilterAdapter extends RecyclerView.Adapter<CardFilterAdapter.CardFilterViewHolder> {
    private List<String> filterList;
    private Context context;

    public CardFilterAdapter(List<String> filterList, Context context) {
        this.filterList = filterList;
        this.context = context;
    }

    public void refreshCardList(List<String> newFilterList) {
        filterList.clear();
        filterList = newFilterList;
    }

    @NonNull
    @Override
    public CardFilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_menu_promo, parent, false);
        CardFilterViewHolder viewHolder = new CardFilterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardFilterViewHolder holder, int position) {
        holder.bind(filterList.get(position));
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public class CardFilterViewHolder extends RecyclerView.ViewHolder {
        Button filterButton;

        public CardFilterViewHolder(@NonNull View itemView) {
            super(itemView);
            filterButton = itemView.findViewById(R.id.btnPromoMenu);
        }

        public void bind(String label) {
            filterButton.setText(label);
            filterButton.setOnClickListener(view ->
                    Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show());
        }
    }
}
