package com.indocyber.itsmeandroid.view.contactcc.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.indocyber.itsmeandroid.BuildConfig;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;
import com.indocyber.itsmeandroid.utilities.core.Animations;
import com.indocyber.itsmeandroid.view.editcardsecuritycode.EditCardSecurityCodeActivity;

import com.indocyber.itsmeandroid.view.promo.activity.PromoActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.INTENT_ID;
import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.TAG_ADD;
import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.TAG_REMOVE;
import static com.indocyber.itsmeandroid.utilities.UtilitiesCore.hideKeyboard;
import static com.indocyber.itsmeandroid.view.contactcc.activity.ContactCCActivity.MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE;


public class ContactCCAdapter extends RecyclerView.Adapter<ContactCCAdapter.ContactViewHolder> {
    private List<ImageCardModel> listCard;
    private final Activity activity;
    private SaveTagClickListener listener;

    //// TODO: 05/02/2020 delete when real number provided
    private String customerServicePhone = "021595929";

    public ContactCCAdapter(Activity activity, SaveTagClickListener listener, List<ImageCardModel> cardList) {
        this.activity = activity;
        this.listener = listener;
        this.listCard = cardList;
    }

    public void refreshCardList(List<ImageCardModel> newCardList) {
        listCard.clear();
        listCard.addAll(newCardList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bind(listCard.get(position));
    }

    @Override
    public int getItemCount() {
        return listCard.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        final TextView txtNumberCard;
        final TextView txtNameCard;
        final TextView txtExpireCard;
        final ImageView imageBlock;
        private LinearLayout linear_expands_tags;
        private RelativeLayout rltvBlocked;
        private Button btnTag;
        private Button btnEdit;
        private Button btnPromo;
        private Button btnShare;
        private Button btnCall;
        private Button btnChat;
        private Button btnSave;
        private RecyclerView recycle_EditTags;
        private EditText edtxAddTag;
        private ImageButton imgSend;
        private EditTagsAdapter adapterTags;

        ContactViewHolder(View itemView) {
            super(itemView);
            txtNumberCard = itemView.findViewById(R.id.txtNumberCard);
            txtNameCard = itemView.findViewById(R.id.txtNameCard);
            txtExpireCard = itemView.findViewById(R.id.txtExpireCard);
            imageBlock = itemView.findViewById(R.id.imageBlock);
            linear_expands_tags = itemView.findViewById(R.id.linear_expands_tags);
            btnSave = itemView.findViewById(R.id.btnSave);
            rltvBlocked = itemView.findViewById(R.id.rltvBlocked);
//            LinearLayout linear_contact = itemView.findViewById(R.id.linear_contact);
            btnTag = itemView.findViewById(R.id.btnTag);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnPromo = itemView.findViewById(R.id.btnPromo);
            btnShare = itemView.findViewById(R.id.btnShare);
            btnCall = itemView.findViewById(R.id.btnCall);
            btnChat = itemView.findViewById(R.id.btnChat);
            recycle_EditTags = itemView.findViewById(R.id.recycle_EditTags);
            edtxAddTag = itemView.findViewById(R.id.edtxAddTag);
            imgSend = itemView.findViewById(R.id.imgSend);

        }

        private void bind(ImageCardModel model) {
            //setText
            txtNameCard.setText(model.getNameCard());
            txtNumberCard.setText(UtilitiesCore.cardNumberSpacing(model.getNumberCard(), 3));
            txtExpireCard.setText(model.getExpireCard());
            linear_expands_tags.setVisibility(View.GONE);
            rltvBlocked.bringToFront();

            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.bg_gradient_soft)
                    .error(R.drawable.bg_gradient_soft)
                    .priority(Priority.HIGH);

            Glide.with(activity)
                    .load(model.getImage())
                    .apply(options)
                    .into(imageBlock);


            //onClick
            btnTag.setOnClickListener(v -> showAndHideTag(linear_expands_tags));
            btnSave.setOnClickListener(v -> {
                model.setTagList(adapterTags.getListTag());
                listener.save(model);
                showAndHideTag(linear_expands_tags);
            });
            btnEdit.setOnClickListener(v -> {
                Intent intent = new Intent(activity, EditCardSecurityCodeActivity.class);
                intent.putExtra(INTENT_ID, model.getId());
                activity.startActivity(intent);
            });
            btnCall.setOnClickListener(v -> dialPhoneNumber(customerServicePhone));
            btnShare.setOnClickListener(v -> setRequestPermission(model));
            btnPromo.setOnClickListener(v -> {
                Intent intent = new Intent(activity, PromoActivity.class);
                activity.startActivity(intent);
            });

            //logic block card
            if (!model.isBlockedCard()) {
                rltvBlocked.setVisibility(View.GONE);
                btnTag.setEnabled(true);
                btnEdit.setEnabled(true);
                btnPromo.setEnabled(true);
                btnShare.setEnabled(true);
                btnCall.setEnabled(true);
                btnChat.setEnabled(true);
            } else {
                rltvBlocked.setVisibility(View.VISIBLE);
                btnTag.setEnabled(false);
                btnEdit.setEnabled(false);
                btnPromo.setEnabled(false);
                btnShare.setEnabled(false);
                btnCall.setEnabled(false);
                btnChat.setEnabled(false);
            }

            adapterTags = new EditTagsAdapter(activity, model.getTagList());
            LinearLayoutManager lm =
                    new LinearLayoutManager(activity,  LinearLayoutManager.HORIZONTAL, false);
            recycle_EditTags.setLayoutManager(lm);
            recycle_EditTags.setAdapter(adapterTags);
            recycle_EditTags.setHasFixedSize(true);

            adapterTags.setOnBlockListener(tagPosition ->
                    adapterTags.tagAction(edtxAddTag.getText().toString(), TAG_REMOVE));

            //Add Tag Button
            imgSend.setOnClickListener(v ->{
                if (edtxAddTag.length() > 0){
                    hideKeyboard(activity);
                    adapterTags.tagAction(edtxAddTag.getText().toString(), TAG_ADD);
                } else {
                    Toast.makeText(activity, "field empty", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(intent);
        }
    }

    private void showAndHideTag(LinearLayout view) {
        if (view.getVisibility() == View.GONE) {
            Animations.expand(view, () -> {
                //empty
            });
        } else {
            Animations.collapse(view);
        }
    }

    static public void shareImage(int url, final Activity context) {

        Picasso.get().load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap, context));
                //i.putExtra(Intent.EXTRA_TEXT, "Card Membership");
                context.startActivity(Intent.createChooser(i, "Share Image"));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                Toast.makeText(context, e.getMessage() + "", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });
    }

    static public Uri getLocalBitmapUri(Bitmap bmp, Context context) {
        Uri bmpUri = null;
        try {
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = FileProvider.getUriForFile(
                    context, BuildConfig.APPLICATION_ID + ".provider",
                    file
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    private void setRequestPermission(ImageCardModel model) {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
            }
        } else {
            shareImage(model.getImage(), activity);

            // Permission has already been granted
        }
    }


    public ArrayList<String> reverseArrayList(ArrayList<String> alist)
    {
        // Arraylist for storing reversed elements
        ArrayList<String> revArrayList = new ArrayList<>();
        for (int i = alist.size() - 1; i >= 0; i--) {

            // Append the elements in reverse order
            revArrayList.add(alist.get(i));
        }

        // Return the reversed arraylist
        return revArrayList;
    }

    public interface SaveTagClickListener {
        void save(ImageCardModel model);
    }

    static String getAlphaNumericString(int n)
    {

        // length is bounded by 256 Character
        byte[] array = new byte[256];
        new Random().nextBytes(array);

        String randomString
                = new String(array, Charset.forName("UTF-8"));

        // Create a StringBuffer to store the result
        StringBuffer r = new StringBuffer();

        // remove all spacial char
        String  AlphaNumericString
                = randomString
                .replaceAll("[^A-Za-z0-9]", "");

        // Append first 20 alphanumeric characters
        // from the generated random String into the result
        for (int k = 0; k < AlphaNumericString.length(); k++) {

            if (Character.isLetter(AlphaNumericString.charAt(k))
                    && (n > 0)
                    || Character.isDigit(AlphaNumericString.charAt(k))
                    && (n > 0)) {

                r.append(AlphaNumericString.charAt(k));
                n--;
            }
        }

        // return the resultant string
        return r.toString();
    }


}
