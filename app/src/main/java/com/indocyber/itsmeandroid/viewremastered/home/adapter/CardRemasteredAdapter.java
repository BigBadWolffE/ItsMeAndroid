package com.indocyber.itsmeandroid.viewremastered.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.viewremastered.morecard.activity.MoreCardRemasteredActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.CARD_TYPE;
import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.CREDIT_CARD;
import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.INTENT_ID;
import static com.indocyber.itsmeandroid.utilities.UtilitiesCore.loadImage;

public class CardRemasteredAdapter extends PagerAdapter {
    private Activity activity;
    private List<ImageCardModel> list = new ArrayList<>();
    private View view = null;
    Bitmap bitmap = null;

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
        loadImage(cardImage, activity, data.getImage());

        cardMenuButton.setOnClickListener(v ->{
            Intent intent = new Intent(activity, MoreCardRemasteredActivity.class);
            intent.putExtra(INTENT_ID,data);
            intent.putExtra(CARD_TYPE,CREDIT_CARD);
            cardMenuButton.setVisibility(View.INVISIBLE);
            View view = container.findViewWithTag("Layout" + position);
            Bitmap sharable = getBitmapFromView(view);
            intent.putExtra("BITMAP_KARTU", convertBitmapToBytes(sharable));
            cardMenuButton.setVisibility(View.VISIBLE);
            activity.startActivity(intent);
//            Log.d("data image" , "datanya " +data.getId() + " "+data.getImage() + " "+data.getNumberCard() + " "+data.getNameCard() + " "+data.getExpireCard() + " "+data.getCost() + " "+data.getPrintDate() + " "+data.getPrintDueDate() + " "+data.isBlockedCard() + " "+data.getBillingAddress() + " "+data.getCountry() + " "+data.getCity() + " "+data.getPostalCode() + " "+data.getLastBill() + " "+data.getMinPayment() + " "+data.getAvailableCredit() + " "+data.getTagList() + " "+data.getNewTagList() + " " );
        });
        container.addView(view);

        /*view.post(new Runnable() {
            @Override
            public void run() {
                getBitmapFromView(view);
            }
        });*/

//        bitmap = getBitmapFromView(view);


//        view.setDrawingCacheEnabled(true);
//        view.buildDrawingCache();
//        Bitmap bitmap = view.getDrawingCache();
//        BitmapDrawable drawable = new BitmapDrawable(bitmap);

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
        view.setTag("Layout" + position);
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

    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight)
    {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix(); // CREATE A MATRIX FOR THE MANIPULATION
        matrix.postScale(scaleWidth, scaleHeight); // RESIZE THE BIT MAP

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

        return resizedBitmap;
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
