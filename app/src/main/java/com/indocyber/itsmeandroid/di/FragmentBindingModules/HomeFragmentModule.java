package com.indocyber.itsmeandroid.di.FragmentBindingModules;

import com.indocyber.itsmeandroid.view.profile.fragment.DetailProfileFragment;
import com.indocyber.itsmeandroid.view.profile.fragment.ProfileKTPFragment;
import com.indocyber.itsmeandroid.view.profile.fragment.ProfileNPWPFragment;
import com.indocyber.itsmeandroid.view.profile.fragment.ProfilePassportFragment;
import com.indocyber.itsmeandroid.viewremastered.akun.AkunRemasteredFragment;
import com.indocyber.itsmeandroid.viewremastered.home.fragment.HomeRemasteredFragment;
import com.indocyber.itsmeandroid.viewremastered.kartuku.fragment.KartukuRemasteredFragment;
import com.indocyber.itsmeandroid.viewremastered.promo.fragment.PromoRemasteredFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/*
 *
 *
 *@Author
 *@Version
 */
@Module
public abstract class HomeFragmentModule {

    @ContributesAndroidInjector
    abstract HomeRemasteredFragment provideHomeFragment();

    @ContributesAndroidInjector
    abstract PromoRemasteredFragment providePromoFragment();

    @ContributesAndroidInjector(modules = KartukuFragmentModule.class)
    abstract KartukuRemasteredFragment provideKartukuFragment();

    @ContributesAndroidInjector
    abstract AkunRemasteredFragment provideAkunFragment();
}
