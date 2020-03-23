package com.indocyber.itsmeandroid.viewremastered.promo.Activity;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ItemShareModel;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;
import com.indocyber.itsmeandroid.viewremastered.promo.Adapter.SharePromoAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailPromoActivity extends AppCompatActivity implements SharePromoAdapter.Listener {
    @BindView(R.id.btnShare)
    LinearLayout mBtnShaere;
    @BindView(R.id.bottom_sheet_share)
    CardView mBottomSheetShare;
    @BindView(R.id.recyclerSharePromo)
    RecyclerView mRecyclerSharePromo;
    @BindView(R.id.lblJarak)
    TextView mLblJarak;
    @BindView(R.id.lblDiskon)
    TextView mLblDiskon;
    @BindView(R.id.layoutDiskon)
    LinearLayout mLayoutDiskon;
    @BindView(R.id.layoutJarak)
    LinearLayout mLayoutJarak;

    BottomSheetBehavior mBottomSheetBehaviorShare;
    GridLayoutManager gridLayourManager;
    SharePromoAdapter mSharePromoAdapter;

    String jarak;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_promo);
        ButterKnife.bind(this);

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

    }


    @OnClick(R.id.btnShare)
    void sharePromo() {
        mBottomSheetBehaviorShare.setPeekHeight(UtilitiesCore.dpToPx(DetailPromoActivity.this, 250));
        String[] removePackageName = new String[]{"com.android.bluetooth", "com.facebook.orca", "com.google.android.apps.docs", "com.discord",
                "com.google.android.apps.messaging", "com.google.android.gm", "com.google.android.apps.keep",
                "com.linkedin.android", "com.skype.raider", "com.roidapp.photogrid", "com.rarlab.rar"};
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "share promo");
        sendIntent.setType("text/plain");
        final List<ResolveInfo> activities = getPackageManager().queryIntentActivities(sendIntent, 0);

        List<ItemShareModel> appNames = new ArrayList<ItemShareModel>();
        for (ResolveInfo info : activities) {
            if (!Arrays.asList(removePackageName).contains(info.activityInfo.packageName)) {
                appNames.add(new ItemShareModel(info.loadLabel(getPackageManager()).toString(), info.activityInfo.packageName, info.loadIcon(getPackageManager())));
            }
        }
//        final List<ItemShareModel> newItem = appNames;
        mSharePromoAdapter = new SharePromoAdapter(appNames, getApplicationContext(), this);
        mRecyclerSharePromo.setLayoutManager(gridLayourManager);
        mRecyclerSharePromo.setAdapter(mSharePromoAdapter);


//        ListAdapter adapter = new ArrayAdapter<ItemShareModel>(this, R.layout.select_dialog_item,R.id.lblTittle, newItem) {
//            public View getView(int position, View convertView, ViewGroup parent) {
//                //Use super class to create the View
//                View v = super.getView(position, convertView, parent);
//                TextView tv = v.findViewById(R.id.lblTittle);
//                tv.setText(newItem.get(position).app);
//                tv.setTextSize(15.0f);
//                //Put the image on the TextView
//                tv.setCompoundDrawablesWithIntrinsicBounds(newItem.get(position).icon, null, null, null);
//
//                //Add margin between image and text (support various screen densities)
//                int dp5 = (int) (5 * getResources().getDisplayMetrics().density + 0.5f);
//                tv.setCompoundDrawablePadding(dp5);
//
//                return v;
//            }
//        };

//        mRecyclerSharePromo.setLayoutManager(gridLayourManager);
//        mRecyclerSharePromo.setAdapter(adapter);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Custom Sharing Dialog");
//        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int item) {
//                ResolveInfo info = activities.get(item);
//
//                if (info.activityInfo.packageName.equals("com.facebook.katana")) {
//                    Toast.makeText(getApplicationContext(), "Facebook Selected ", Toast.LENGTH_LONG).show();
//                } else {
//
//                    // start the selected activity
//                    Log.i("tes", "Hi..hello. Intent is selected");
//
//                    sendIntent.setPackage(info.activityInfo.packageName);
//                    startActivity(sendIntent);
//                }
//            }
//        });
//
//        AlertDialog alert = builder.create();
//        alert.show();


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
    public void onClick(ItemShareModel mItemShareModel) {
        Toast.makeText(this, "Ini package " + mItemShareModel.packageName, Toast.LENGTH_LONG).show();
    }
}
