package com.indocyber.itsmeandroid.view.promo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.view.promo.adapter.PromoMenuAdapter;
import com.indocyber.itsmeandroid.view.promo.fragment.AllPromoFragment;
import com.indocyber.itsmeandroid.view.promo.fragment.CollectionPromoFragment;
import com.indocyber.itsmeandroid.view.promo.fragment.DiningPromoFragment;
import com.indocyber.itsmeandroid.view.promo.fragment.NearbyPromoFragment;

import java.util.ArrayList;
import java.util.List;

public class PromoActivity extends AppCompatActivity implements PromoMenuAdapter.Listener {

    private RecyclerView mPromoMenuRecycler;
    private PromoMenuAdapter mPromoMenuAdapter, mNearbyMenuAdapter, mDinningMenuAdapter, mCollectionMenuAdapter;
    private Fragment mFragmentIndicator = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Promo");
            getSupportActionBar().setElevation(0f);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mPromoMenuRecycler = findViewById(R.id.recyclerPromoHorizontalMenu);
        mPromoMenuAdapter = new PromoMenuAdapter(initializeMenu(), getApplicationContext(), PromoActivity.this);
        mNearbyMenuAdapter = new PromoMenuAdapter(nearbyActive(), getApplicationContext(), PromoActivity.this);
        mDinningMenuAdapter = new PromoMenuAdapter(dinningActive(), getApplicationContext(), PromoActivity.this);
        mCollectionMenuAdapter = new PromoMenuAdapter(collectionActive(), getApplicationContext(), PromoActivity.this);
        LinearLayoutManager horizontalLayoutManager =new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mPromoMenuRecycler.setLayoutManager(horizontalLayoutManager);
        mPromoMenuRecycler.setAdapter(mPromoMenuAdapter);

        loadFragment(mFragmentIndicator);

    }

    private List<Integer> initializeMenu() {
        List<Integer> allActiveList = new ArrayList<>();
        allActiveList.clear();
        allActiveList.add(R.drawable.btn_all_active);
        allActiveList.add(R.drawable.btn_nearby_unactive);
        allActiveList.add(R.drawable.btn_dinning_unactive);
        allActiveList.add(R.drawable.btn_collection_unactive);
        return allActiveList;
    }

    private List<Integer> nearbyActive() {
        List<Integer> nearbyActiveList = new ArrayList<>();
        nearbyActiveList.clear();
        nearbyActiveList.add(R.drawable.btn_all_unactive);
        nearbyActiveList.add(R.drawable.btn_nearby_active);
        nearbyActiveList.add(R.drawable.btn_dinning_unactive);
        nearbyActiveList.add(R.drawable.btn_collection_unactive);
        return nearbyActiveList;
    }

    private List<Integer> dinningActive() {
        List<Integer> dinningActiveList = new ArrayList<>();
        dinningActiveList.clear();
        dinningActiveList.add(R.drawable.btn_all_unactive);
        dinningActiveList.add(R.drawable.btn_nearby_unactive);
        dinningActiveList.add(R.drawable.btn_dinning_active);
        dinningActiveList.add(R.drawable.btn_collection_unactive);
        return dinningActiveList;
    }

    private List<Integer> collectionActive() {
        List<Integer> collectionActiveList = new ArrayList<>();
        collectionActiveList.clear();
        collectionActiveList.add(R.drawable.btn_all_unactive);
        collectionActiveList.add(R.drawable.btn_nearby_unactive);
        collectionActiveList.add(R.drawable.btn_dinning_unactive);
        collectionActiveList.add(R.drawable.btn_collection_active);
        return collectionActiveList;
    }

    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fm = getFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        if (fragment == null) {
            fragment = new AllPromoFragment();
        }
        fragmentTransaction.replace(R.id.frameLayoutPromo, fragment);
        fragmentTransaction.commit(); // save the changes
    }

    @Override
    public void onClick(int position) {
        if (position == 0) {
            loadFragment(new AllPromoFragment());
            mPromoMenuRecycler.swapAdapter(mPromoMenuAdapter, false);
        } else if (position == 1) {
            loadFragment(new NearbyPromoFragment());
            mPromoMenuRecycler.swapAdapter(mNearbyMenuAdapter, false);
        } else if (position == 2) {
            loadFragment(new DiningPromoFragment());
            mPromoMenuRecycler.swapAdapter(mDinningMenuAdapter, false);
        } else if (position == 3) {
            loadFragment(new CollectionPromoFragment());
            mPromoMenuRecycler.swapAdapter(mCollectionMenuAdapter, false);
        }
    }



}
