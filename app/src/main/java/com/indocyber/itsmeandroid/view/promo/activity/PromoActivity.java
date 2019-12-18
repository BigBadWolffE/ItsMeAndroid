package com.indocyber.itsmeandroid.view.promo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Toast;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.view.promo.adapter.PromoMenuAdapter;
import com.indocyber.itsmeandroid.view.promo.fragment.AllPromoFragment;
import com.indocyber.itsmeandroid.view.promo.fragment.DiningPromoFragment;
import com.indocyber.itsmeandroid.view.promo.fragment.NearbyPromoFragment;

import java.util.ArrayList;
import java.util.List;

public class PromoActivity extends AppCompatActivity implements PromoMenuAdapter.Listener {

    private RecyclerView mPromoMenuRecycler;
    private List<Integer> mResourceList = new ArrayList<>();
    private PromoMenuAdapter mPromoMenuAdapter;
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
        mPromoMenuAdapter = new PromoMenuAdapter(mResourceList, getApplicationContext(), PromoActivity.this);
        LinearLayoutManager horizontalLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mPromoMenuRecycler.setLayoutManager(horizontalLayoutManager);
        mPromoMenuRecycler.setAdapter(mPromoMenuAdapter);

        initializeMenu();
        loadFragment(mFragmentIndicator);

    }

    private void initializeMenu() {
        mResourceList.add(R.drawable.btn_all_active);
        mResourceList.add(R.drawable.btn_nearby_unactive);
        mResourceList.add(R.drawable.btn_dinning_unactive);
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
        if (position == 0)
            loadFragment(new AllPromoFragment());
        else if (position == 1)
            loadFragment(new NearbyPromoFragment());
        else if (position == 2)
            loadFragment(new DiningPromoFragment());
    }
}
