package com.indocyber.itsmeandroid.viewremastered.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.viewremastered.morecard.activity.MoreCardRemasteredActivity;

import java.util.ArrayList;
import java.util.List;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.INTENT_ID;
import static com.indocyber.itsmeandroid.utilities.UtilitiesCore.loadImage;

public class CardRemasteredAdapter extends PagerAdapter {
    private Activity activity;
    private List<ImageCardModel> list = new ArrayList<>();

    public CardRemasteredAdapter(Activity activity) {
        this.activity = activity;
    }

    public void insertData(List<ImageCardModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = null;
        if (layoutInflater != null) {
            view = layoutInflater.inflate(R.layout.card_pager_layout, container, false);
        }
        assert view != null;

        ImageCardModel data = list.get(position);
        ImageView cardImage = view.findViewById(R.id.cardListImage);
        ImageView cardMenuButton = view.findViewById(R.id.btnCardMoreMenu);
        loadImage(cardImage,activity, data.getImage());

        cardMenuButton.setOnClickListener(v ->{
            Intent intent = new Intent(activity, MoreCardRemasteredActivity.class);
            intent.putExtra(INTENT_ID,data);
            activity.startActivity(intent);
        });
        //cardImage.setImageResource(R.drawable.img_bca_card_template);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
