package com.indocyber.itsmeandroid.di.FragmentBindingModules;

import com.indocyber.itsmeandroid.viewremastered.kartuku.fragment.CreditCardList;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/*
 *
 *
 *@Author
 *@Version
 */

@Module
public abstract class KartukuFragmentModule {

    @ContributesAndroidInjector
    abstract CreditCardList provideCreditCardListFragment();
}
