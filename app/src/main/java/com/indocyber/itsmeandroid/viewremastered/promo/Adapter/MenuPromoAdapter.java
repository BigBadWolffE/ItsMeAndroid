package com.indocyber.itsmeandroid.viewremastered.promo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.PromoItemModel;
import com.indocyber.itsmeandroid.model.PromoMenuModel;
import com.indocyber.itsmeandroid.view.promo.adapter.PromoMenuAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuPromoAdapter extends RecyclerView.Adapter<MenuPromoAdapter.MenuViewHolder> {
    private List<PromoMenuModel> mPromoMenuList = new ArrayList<>();
    private Context context;
    private PromoMenuAdapter.Listener mlistener;

    public interface Listener {
        void onClick(int position);
    }

    public void refreshList(List<PromoMenuModel> newList) {
        mPromoMenuList.clear();
        mPromoMenuList = newList;
        notifyDataSetChanged();
    }

    public MenuPromoAdapter(List<PromoMenuModel> mPromoMenuList, Context context, PromoMenuAdapter.Listener listener) {
        this.mPromoMenuList = mPromoMenuList;
        this.context = context;
        this.mlistener = listener;
    }

    @NonNull
    @Override
    public MenuPromoAdapter.MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View menuPromoView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_menu_promo, parent, false);
        MenuPromoAdapter.MenuViewHolder view = new MenuPromoAdapter.MenuViewHolder(menuPromoView);
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        holder.btnPromoMenu.setBackgroundResource(mPromoMenuList.get(position).getBanner());
        holder.btnPromoMenu.setText(mPromoMenuList.get(position).getTitle());
        holder.btnPromoMenu.setTextColor(context.getResources().getColor(mPromoMenuList.get(position).getColor()));
        holder.btnPromoMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistener.onClick(position);

                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPromoMenuList.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btnPromoMenu)
        Button btnPromoMenu;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
