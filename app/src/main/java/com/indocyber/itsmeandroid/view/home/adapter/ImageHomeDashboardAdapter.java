package com.indocyber.itsmeandroid.view.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.indocyber.itsmeandroid.R;

public class ImageHomeDashboardAdapter extends PagerAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private  int[] mData;
    Listener mlistener;

    public interface Listener {
        void onClick(int position);
    }


    public ImageHomeDashboardAdapter(Context context, int[] mData, Listener mlistener) {
        mContext = context;
        this.mlistener = mlistener;
        this.mData = mData;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        RequestOptions options = new RequestOptions()
//                .centerCrop()
//                .placeholder(R.drawable.bg_gradient_soft)
//                .error(R.drawable.bg_gradient_soft);
    }

    @Override
    public int getCount() {
        return mData.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item_image_home, container, false);

        ImageView imageView = itemView.findViewById(R.id.imgPager);
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.bg_gradient_soft)
                .error(R.drawable.bg_gradient_soft)
                .priority(Priority.HIGH);
        Glide.with(mContext)
                .load(mData[position])
                .apply(options)
                .into(imageView);

        imageView.setOnClickListener(v -> mlistener.onClick(position));
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
