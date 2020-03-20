package com.indocyber.itsmeandroid.viewremastered.notification.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.indocyber.itsmeandroid.viewremastered.notification.fragment.BillingFragment;
import com.indocyber.itsmeandroid.viewremastered.notification.fragment.NotificationFragment;

public class TabLayoutAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    public TabLayoutAdapter(@NonNull FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new NotificationFragment();
            case 1:
                return new BillingFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
