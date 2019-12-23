package com.indocyber.itsmeandroid.view.home.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ImageCardModel;

import java.util.ArrayList;
import java.util.List;

public class ImageCardAdapter extends PagerAdapter {
    private List<ImageCardModel> list = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;

    public ImageCardAdapter(Context context, List<ImageCardModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_image_card, container, false);
        ImageCardModel model = list.get(position);

        ImageView imageView = view.findViewById(R.id.imageHome);

        ((TextView) view.findViewById(R.id.txtCost)).setText(model.getCost());
        ((TextView) view.findViewById(R.id.txtNumberCard)).setText(model.getNumberCard());
        ((TextView) view.findViewById(R.id.txtNameCard)).setText(model.getNameCard());
        ((TextView) view.findViewById(R.id.txtExpireCard)).setText(model.getExpireCard());
        ((TextView) view.findViewById(R.id.txtPrintDate)).setText(model.getPrintDate());
        ((TextView) view.findViewById(R.id.txtPrintDueDate)).setText(model.getPrintDueDate());

        ((Button) view.findViewById(R.id.btnMore)).setOnClickListener(v -> {
            showCustomDialog();
        });


        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.bg_gradient_soft)
                .error(R.drawable.bg_gradient_soft)
                .priority(Priority.HIGH);

        Glide.with(context)
                .load(model.getImage())
                .apply(options)
                .into(imageView);





        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
        return view == obj;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    private void showCustomDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_detail_home_card);
        dialog.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        final ImageButton imgButtonClose = (ImageButton) dialog.findViewById(R.id.imgButtonClose);
        //final TextView txtNumberCard = (TextView) dialog.findViewById(R.id.txtNumberCard);


        imgButtonClose.setOnClickListener(v -> {
            dialog.dismiss();
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}
