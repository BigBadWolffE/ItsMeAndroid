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
import com.indocyber.itsmeandroid.model.PromoItemModel;
import com.indocyber.itsmeandroid.viewremastered.kartuku.fragment.CreditCardList;

import java.util.ArrayList;
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
    private int activePosition;
    private Listener listener;
    private OtherListener otherCardListener;
    private String type = "";

    public static final String CREDIT_CARD = "credit";

    public interface Listener {
        void onClick(String tag);
    }

    public interface OtherListener {
        void onClick(int position);
    }

    public CardFilterAdapter(List<String> filterList, Context context, Listener listener,
                             OtherListener otherCardListener, String type) {
        this.filterList = filterList;
        this.context = context;
        this.listener = listener;
        this.type = type;
        this.otherCardListener = otherCardListener;

    }

    public void refreshFilterList(final List<String> newFilterList) {
        filterList.clear();
        List<String> nextFilterList = new ArrayList<>();
        nextFilterList.add("Semua");
        nextFilterList.addAll(newFilterList);
        filterList.addAll(nextFilterList);
        notifyDataSetChanged();
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
            if (this.type.equals(CREDIT_CARD)) {
                this.listener.onClick(filterList.get(position));
            } else {
                this.otherCardListener.onClick(position);
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
}
