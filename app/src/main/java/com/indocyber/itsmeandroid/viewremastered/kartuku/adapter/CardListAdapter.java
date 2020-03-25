package com.indocyber.itsmeandroid.viewremastered.kartuku.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.viewremastered.billinginfo.BillingInfo;
import com.indocyber.itsmeandroid.viewremastered.blockkartu.BlockKartu;
import com.indocyber.itsmeandroid.viewremastered.editcard.activity.editkartu;
import com.indocyber.itsmeandroid.viewremastered.morecard.activity.MoreCardRemasteredActivity;


import java.io.ByteArrayOutputStream;
import java.util.List;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.CARD_TYPE;
import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.CREDIT_CARD;
import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.INTENT_ID;
import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.MEMBER_CARD;

/*
 *
 *
 *@Author
 *@Version
 */
public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardViewHolder> {
    private List<ImageCardModel> cardList;
    private Context context;
    private int cardType;

    public CardListAdapter(List<ImageCardModel> cardList, Context context, int cardType) {
        this.cardList = cardList;
        this.context = context;
        this.cardType = cardType;
    }

    public void refreshCardList(List<ImageCardModel> newCardList) {
        cardList.clear();
        cardList = newCardList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_list_item, parent, false);
        CardViewHolder viewHolder = new CardViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        ImageCardModel model = cardList.get(position);
        View.OnClickListener listener = view -> {
            Intent intent = new Intent(context, MoreCardRemasteredActivity.class);

//            intent.putExtra("cardNumber", model.getNumberCard());
//            intent.putExtra("cardHolder", model.getNameCard());
//            intent.putExtra("cardImage", model.getImage());
//            intent.putExtra("expiryDate", model.getExpireCard());
//            intent.putExtra("billingAddress", model.getBillingAddress());
            holder.toggleButtonVisibility();
            Bitmap sharable = getBitmapFromView(holder.itemView);
            intent.putExtra("BITMAP_KARTU", convertBitmapToBytes(sharable));
            holder.toggleButtonVisibility();
            intent.putExtra(INTENT_ID, model);
            intent.putExtra(CARD_TYPE,cardType);
            context.startActivity(intent);
        };
        holder.bind(model, listener);
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

    public class CardViewHolder extends RecyclerView.ViewHolder {
        ImageView cardImage;
        LinearLayout blockedLayout;
        ImageView cardMenuButton;
        TextView cardNumberLabel;
        TextView cardHolderLabel;
        TextView cardExpiryLabel;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.cardListImage);
            cardMenuButton = itemView.findViewById(R.id.btnCardMoreMenu);
            cardNumberLabel = itemView.findViewById(R.id.lblCcNumber);
            cardHolderLabel = itemView.findViewById(R.id.lblCardHolder);
            cardExpiryLabel = itemView.findViewById(R.id.lblExpiry);
            blockedLayout = itemView.findViewById(R.id.blockLayout);
        }

        public void toggleButtonVisibility() {
            if (cardMenuButton.getVisibility() == View.VISIBLE)
                cardMenuButton.setVisibility(View.INVISIBLE);
            else
                cardMenuButton.setVisibility(View.VISIBLE);
        }

        public void bind(ImageCardModel model, View.OnClickListener cardMenuListener) {
            cardImage.setImageResource(R.drawable.img_bca_card_template);
            cardMenuButton.setOnClickListener(cardMenuListener);
            cardNumberLabel.setText(padHalfCardNumber(model.getNumberCard(), 3));
            cardHolderLabel.setText(model.getNameCard());
            cardExpiryLabel.setText(model.getExpireCard());
            if (model.isBlockedCard()) {
                blockedLayout.setVisibility(View.VISIBLE);
                cardMenuButton.setVisibility(View.GONE);
                cardNumberLabel.setVisibility(View.GONE);
                cardHolderLabel.setVisibility(View.GONE);
                cardExpiryLabel.setVisibility(View.GONE);
            } else {
                blockedLayout.setVisibility(View.GONE);
                cardMenuButton.setVisibility(View.VISIBLE);
            }
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

    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    private byte[] convertBitmapToBytes(Bitmap bitmap) {
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        return bStream.toByteArray();
    }
}
