package com.indocyber.itsmeandroid.viewremastered.notification.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.viewremastered.notification.Adapter.TabLayoutAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationRemasteredActivity extends AppCompatActivity {
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    TabLayoutAdapter mTabLayoutAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_remastered);
        ButterKnife.bind(this);
        setupTabLayout();

    }

    private void setupTabLayout(){
        mTabLayout.addTab(mTabLayout.newTab().setText("Notification (5)"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Billing (20/100)"));
        mTabLayout.setTabGravity(mTabLayout.GRAVITY_FILL);
        mTabLayoutAdapter = new TabLayoutAdapter(getSupportFragmentManager(),mTabLayout.getTabCount());
        mViewPager.setAdapter(mTabLayoutAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                switch (tab.getPosition()){
//                    case 0:
//                        mTabLayout.setBackgroundColor(Color.parseColor("#3F51B5"));
//                        break;
//                    case 1:
//                        mTabLayout.setBackgroundColor(Color.parseColor("#007a0e"));
//                        break;
//
//                }
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }
        });
    }
}
