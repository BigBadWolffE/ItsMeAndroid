package com.indocyber.itsmeandroid.viewremastered.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

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
    private View view = null;

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

        if (layoutInflater != null) {
            view = layoutInflater.inflate(R.layout.card_pager_layout, container, false);
        }
        assert view != null;

        ImageCardModel data = list.get(position);
        ImageView cardImage = view.findViewById(R.id.cardListImage);
        ImageView cardMenuButton = view.findViewById(R.id.btnCardMoreMenu);
        TextView cardNumberLabel = view.findViewById(R.id.lblCcNumber);
        cardNumberLabel.setText(padHalfCardNumber(data.getNumberCard(), 3));
        TextView cardHolderLabel = view.findViewById(R.id.lblCardHolder);
        cardHolderLabel.setText(data.getNameCard());
        TextView cardExpiryLabel = view.findViewById(R.id.lblExpiry);
        cardExpiryLabel.setText(data.getExpireCard());
        loadImage(cardImage,activity, data.getImage());

        cardMenuButton.setOnClickListener(v ->{
            Intent intent = new Intent(activity, MoreCardRemasteredActivity.class);
            intent.putExtra(INTENT_ID,data);
            activity.startActivity(intent);
        });
        container.addView(view);

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Do what you need to do here.
                // Then remove the listener:
                int[] position = {0, 0};
                cardImage.getLocationOnScreen(position);

                int paddingLeft = (cardImage.getWidth() * 8 / 100);
                int startYAxis = (cardImage.getHeight() / 2);

                cardNumberLabel.setX(paddingLeft);
                cardNumberLabel.setY(startYAxis
                        + activity.getResources().getDimension(R.dimen.spacing_medium));
                cardNumberLabel.bringToFront();
                cardHolderLabel.setX(paddingLeft);
                cardHolderLabel.setY(cardImage.getHeight() * 80 / 100);
                cardExpiryLabel.setX(cardImage.getWidth() / 2);
                cardExpiryLabel.setY(cardImage.getHeight() * 70 / 100);
                if (cardImage.getHeight() > 0 && cardImage.getWidth() > 0)
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }

        });
        //cardImage.setImageResource(R.drawable.img_bca_card_template);
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
