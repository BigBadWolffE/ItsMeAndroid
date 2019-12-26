package com.indocyber.itsmeandroid.view.promo.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.EditTag;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.model.PromoCollectionModel;
import com.indocyber.itsmeandroid.utilities.core.Animations;
import com.indocyber.itsmeandroid.view.contactcc.adapter.EditTagsAdapter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PromoCollectionAdapter extends RecyclerView.Adapter<PromoCollectionAdapter.ItemViewHolder> {
    private List<ImageCardModel> mPromoColl = new ArrayList<>();
    private Activity mContext;
    private ItemViewHolder mViewHolder;
    private EditTagsAdapter adapterTags;
    private List<EditTag> lisTag = new ArrayList<>();
    public static int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 200;

    public PromoCollectionAdapter(List<ImageCardModel> mPromoColl, Activity mContext) {
        this.mPromoColl = mPromoColl;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PromoCollectionAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_promo_collection, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PromoCollectionAdapter.ItemViewHolder holder, int position) {
        this.mViewHolder = holder;
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.bg_gradient_soft)
                .error(R.drawable.bg_gradient_soft)
                .priority(Priority.HIGH);

        Glide.with(mContext)
                .load(mPromoColl.get(position).getImage())
                .apply(options)
                .into(holder.cardType);

        holder.cardHolder.setText(mPromoColl.get(position).getNameCard());
        holder.cardNumber.setText(onCardNumberChange(mPromoColl.get(position).getNumberCard()));
        holder.cardExpiry.setText(mPromoColl.get(position).getExpireCard());

        holder.btnTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAndHideTag(holder.layoutExpandTags);
            }
        });

        holder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Call someone", Toast.LENGTH_SHORT).show();
//                dialPhoneNumber("089695658002");
            }
        });

        holder.btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Chat someone", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "show promo here", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Edit Something", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRequestPermission(holder);
            }
        });

        //recycleEditTags
        lisTag.clear();
        lisTag.add(new EditTag(1, "Family"));
        lisTag.add(new EditTag(2, "Business"));
        adapterTags = new EditTagsAdapter(mContext);
        LinearLayoutManager lm = new LinearLayoutManager(mContext,  LinearLayoutManager.HORIZONTAL, false);
        adapterTags.setListTags(lisTag);
        holder.recycle_EditTags.setLayoutManager(lm);
        holder.recycle_EditTags.setAdapter(adapterTags);
        holder.recycle_EditTags.setHasFixedSize(true);

    }

    private void setRequestPermission(ItemViewHolder cardType) {
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(mContext,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);

            if (ActivityCompat.shouldShowRequestPermissionRationale(mContext,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(mContext,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
            }
        } else {
            shareImage(cardType);
        }
    }

    private void shareImage(ItemViewHolder cardType) {
        if (cardType.cardType.getDrawable().getConstantState()
                .equals(mContext.getResources().getDrawable(R.drawable.ic_profile_default_img)
                        .getConstantState())) {
            Toast.makeText(mContext, "Tidak ada Foto yang dapat di bagikan", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Bitmap cardBitmap = loadBitmapFromView(cardType.cardLayout);
            Intent shareIntent = new Intent();
            if (cardBitmap != null) {
                Uri uri = getImageUri(mContext, cardBitmap);
                shareIntent = new Intent(Intent.ACTION_SEND);
//                    shareIntent.setType("text/plain");
//                    shareIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                shareIntent.setType("image/jpeg");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }

            try {
                mContext.startActivity(shareIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(mContext, "Gagal membagikan foto", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private Uri getImageUri(Activity context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),
                bitmap, UUID.randomUUID().toString() + ".png", "drawing");
        return Uri.parse(path);
    }

    public static Bitmap loadBitmapFromView(View v) {
        v.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(v.getMeasuredWidth(),
                v.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(bitmap);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        return bitmap;
    }

    private void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            mContext.startActivity(intent);
        }
    }

    private void showAndHideTag(LinearLayout view) {
        if (view.getVisibility() == View.GONE) {
            Animations.expand(view, new Animations.AnimListener() {
                @Override
                public void onFinish() {
                    //empty
                }
            });
        } else {
            Animations.collapse(view);
        }
    }

    private String onCardNumberChange(String text){
        String paddedText = text;
        StringBuilder cuttingText = new StringBuilder(paddedText);

        for (int i=0 ; i<paddedText.length() ; i++) {
            if (i>=4 && i<=13) {
                if (paddedText.charAt(i) != ' ') {
                    cuttingText.setCharAt(i, 'X');
                }
            }
        }

        return cuttingText.toString();
    }

    @Override
    public int getItemCount() {
        return mPromoColl.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView cardType;
        TextView cardNumber, cardHolder, cardExpiry, cardHolderLabel, cardExpiryLabel;
        private ImageView btnTag, btnEdit, btnPromo, btnShare, btnCall, btnChat;
        private LinearLayout layoutExpandTags;
        private RecyclerView recycle_EditTags;
        private RelativeLayout cardLayout;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            cardType = itemView.findViewById(R.id.imgCollectionPromoCard);
            cardHolder = itemView.findViewById(R.id.lblPromoCollectionCardHolder);
            cardNumber = itemView.findViewById(R.id.lblPromoCollectionCardNumber);
            cardExpiry = itemView.findViewById(R.id.lblPromoCollectionCardExpiry);
            cardExpiryLabel = itemView.findViewById(R.id.lblPromoCollectionCardExpiryLabel);
            cardHolderLabel = itemView.findViewById(R.id.lblPromoCollectionCardHolderLabel);
            btnCall = itemView.findViewById(R.id.imgCallPromoCollection);
            btnChat = itemView.findViewById(R.id.imgChatPromoCollection);
            btnEdit =  itemView.findViewById(R.id.imgEditPromoCollection);
            btnPromo = itemView.findViewById(R.id.imgPromoPromoCollection);
            btnTag = itemView.findViewById(R.id.imgTagPromoCollection);
            btnShare = itemView.findViewById(R.id.imgSharePromoCollection);
            layoutExpandTags = itemView.findViewById(R.id.layoutExpandTagsPromoCollection);
            recycle_EditTags = itemView.findViewById(R.id.recycle_EditTags);
            cardLayout = itemView.findViewById(R.id.layoutPromoCollectionCardType);
        }
    }
}
