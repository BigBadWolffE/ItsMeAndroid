package com.indocyber.itsmeandroid.viewremastered.kartuku.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
    private int activePosition = 0;
    private onItemClickListener clicklistener;

    public CardFilterAdapter(List<String> filterList, Context context) {
        this.filterList = filterList;
        this.context = context;
    }

    public void refreshCardList(List<String> newFilterList) {
        filterList.clear();
        filterList = newFilterList;
    }

    public void SetItemOnclickListener(onItemClickListener clicklistener) {
        this.clicklistener = clicklistener;
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

        View.OnClickListener listener = view -> {
            notifyItemChanged(activePosition);
            activePosition = position;
            notifyItemChanged(activePosition);
            if (clicklistener != null){
                clicklistener.onItemClick(position);
            }
        };
        holder.bind(filterList.get(position), listener);
        holder.activateButton(position == activePosition);
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

        public void activateButton(Boolean active) {
            if (active) {
                filterButton.setBackground(context.getDrawable(R.drawable.btn_rounded));
                filterButton.setTextColor(ContextCompat.getColor(context, R.color.colorwhite));
            } else {
                filterButton.setBackground(context.getDrawable(R.drawable.button_border_grey));
                filterButton.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            }
        }

        public void bind(String label, View.OnClickListener listener) {
            filterButton.setText(label);
            filterButton.setOnClickListener(listener);
        }
    }

    public interface onItemClickListener {
        void onItemClick(int position);

    }
}
