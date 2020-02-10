package com.indocyber.itsmeandroid.view.home.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ImageCardModel;

import java.util.List;
import java.util.Objects;

public class ImageCardAdapter extends PagerAdapter {
    private List<ImageCardModel> list;
    private Context context;

    public ImageCardAdapter(Context context, List<ImageCardModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        if (layoutInflater != null) {
            view = layoutInflater.inflate(R.layout.item_image_card, container, false);
            ImageCardModel model = list.get(position);
            ImageView imageView = view.findViewById(R.id.imageHome);
            ((TextView) view.findViewById(R.id.txtCost)).setText(model.getCost());
            ((TextView) view.findViewById(R.id.txtNumberCard)).setText(cardNumberSpacing(model.getNumberCard()));
            ((TextView) view.findViewById(R.id.txtNameCard)).setText(model.getNameCard());
            ((TextView) view.findViewById(R.id.txtExpireCard)).setText(model.getExpireCard());
            ((TextView) view.findViewById(R.id.txtPrintDate)).setText(model.getPrintDate());
            ((TextView) view.findViewById(R.id.txtPrintDueDate)).setText(model.getPrintDueDate());
            (view.findViewById(R.id.btnMore)).setOnClickListener(v -> showCustomDialog());

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
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        final ImageButton imgButtonClose = dialog.findViewById(R.id.imgButtonClose);
        LinearLayout cardDetailTextContainer = dialog.findViewById(R.id.llCardDetailContainer);
        int buttonWidth = cardDetailTextContainer.getLayoutParams().width;
        ViewGroup.LayoutParams buttonLayoutParam = imgButtonClose.getLayoutParams();
        buttonLayoutParam.width = buttonWidth;
        imgButtonClose.setLayoutParams(buttonLayoutParam);
        imgButtonClose.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private String cardNumberSpacing(String cardNumber) {
        StringBuilder paddedNumber = new StringBuilder();
        for (int i = 0; i < cardNumber.length(); i++) {
            paddedNumber.append(cardNumber.charAt(i));
            if (cardNumber.charAt(i) == ' ') {
                paddedNumber.append("  ");
            }
        }
        return paddedNumber.toString();
    }
}
