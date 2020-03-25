package com.indocyber.itsmeandroid.viewremastered.promo.Activity;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ItemShareModel;
import com.indocyber.itsmeandroid.utilities.Preference;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;
import com.indocyber.itsmeandroid.view.BaseActivity;
import com.indocyber.itsmeandroid.viewmodel.DetailPromoViewModel;
import com.indocyber.itsmeandroid.viewmodel.ViewModelFactory;
import com.indocyber.itsmeandroid.viewremastered.promo.Adapter.SharePromoAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

public class DetailPromoActivity extends BaseActivity implements SharePromoAdapter.Listener {
    @BindView(R.id.btnShare)
    LinearLayout mBtnShaere;
    @BindView(R.id.bottom_sheet_share)
    CardView mBottomSheetShare;
    @BindView(R.id.recyclerSharePromo)
    RecyclerView mRecyclerSharePromo;
    @BindView(R.id.lblJarak)
    TextView mLblJarak;
    @BindView(R.id.imgBanner)
    ImageView promoImage;
    @BindView(R.id.lblTitlePromo)
    TextView titlePromo;
    @BindView(R.id.lblDeskripsi)
    TextView promoDesc;
    @BindView(R.id.lblDiskon)
    TextView mLblDiskon;
    @BindView(R.id.titleToolbar)
    TextView titleToolbar;
    @BindView(R.id.layoutDiskon)
    LinearLayout mLayoutDiskon;
    @BindView(R.id.layoutJarak)
    LinearLayout mLayoutJarak;
    private DetailPromoViewModel viewModel;
    private AlertDialog dialog;
    @Inject
    ViewModelFactory factory;
    private String promoId = null;
    BottomSheetBehavior mBottomSheetBehaviorShare;
    GridLayoutManager gridLayourManager;
    SharePromoAdapter mSharePromoAdapter;

    String jarak;


    @Override
    protected int layoutRes() {
        return R.layout.activity_detail_promo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutRes());
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this, factory).get(DetailPromoViewModel.class);
        promoId = getIntent().getStringExtra("idPromo");
        dialog = new SpotsDialog.Builder()
                .setCancelable(false)
                .setContext(DetailPromoActivity.this)
                .build();
        Preference preference = new Preference(this);
        if (promoId != null) {
            viewModel.fetchPromo(preference.getUserAuth(), promoId);
        }
        if (!getIntent().getStringExtra("jarak").equals("")) {
            mLayoutJarak.setVisibility(View.VISIBLE);
            mLayoutDiskon.setVisibility(View.GONE);

        } else if (!getIntent().getStringExtra("diskon").equals("")) {
            mLayoutDiskon.setVisibility(View.VISIBLE);
            mLayoutJarak.setVisibility(View.GONE);
        } else {
            mLayoutJarak.setVisibility(View.GONE);
            mLayoutDiskon.setVisibility(View.GONE);
        }

        mBottomSheetBehaviorShare = BottomSheetBehavior.from(mBottomSheetShare);
        mBottomSheetBehaviorShare.setHideable(false);
        mBottomSheetBehaviorShare.setPeekHeight(UtilitiesCore.dpToPx(DetailPromoActivity.this, 0));
        mBottomSheetBehaviorShare.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBottomSheetBehaviorShare.setPeekHeight(UtilitiesCore.dpToPx(DetailPromoActivity.this, 250));

                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        gridLayourManager = new GridLayoutManager(this, 4);
        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading) dialog.show();
            else dialog.dismiss();
        });

        viewModel.getPromo().observe(this, promoItemModel -> {
            Glide.with(this).load("http://" + promoItemModel.getUrl()).into(promoImage);
            titlePromo.setText(promoItemModel.getTitle());
            titleToolbar.setText(promoItemModel.getTitle());
            promoDesc.setText(promoItemModel.getDesc());
        });
    }

    @OnClick(R.id.btnShare)
    void sharePromo() {
        mBottomSheetBehaviorShare.setPeekHeight(UtilitiesCore.dpToPx(DetailPromoActivity.this, 250));

        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "ini promo");
        shareIntent.setType("text/plain");

// Ask Android to create the chooser for us
        final Intent chooser = Intent.createChooser(shareIntent, getString(R.string.share));
        startActivity(chooser);
        Intent customSharer = new Intent(this, DetailPromoActivity.class);
        Intent[] initialIntents = new Intent[]{customSharer};
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, initialIntents);


    }

    @OnClick(R.id.btnAmbilPromo)
    void ambilPromo() {
        UtilitiesCore.buildAlertConfirmation(
                this, "Apakah anda yakin akan mngambil promo ini,",
                View -> {
                    Intent i = new Intent(this, PinPromoActivity.class);
                    startActivity(i);
                    finish();
                },
                DialogInterface -> DialogInterface.dismiss()

        );
    }

    @OnClick(R.id.imageBtnBack)
    void back() {
        finish();
    }

    @OnClick(R.id.btnClose)
    void close() {
        mBottomSheetBehaviorShare.setPeekHeight(UtilitiesCore.dpToPx(DetailPromoActivity.this, 0));

    }


    @Override
    public void SharePromoonClick(ItemShareModel mItemShareModel) {
//        sendIntent.setPackage(info.activityInfo.packageName);
//                    startActivity(sendIntent);
    }
}
