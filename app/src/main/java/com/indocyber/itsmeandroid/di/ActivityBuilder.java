package com.indocyber.itsmeandroid.di;

import com.indocyber.itsmeandroid.di.FragmentBindingModules.HomeFragmentModule;
import com.indocyber.itsmeandroid.viewremastered.akun.EditProfileActivity;
import com.indocyber.itsmeandroid.viewremastered.blockallcard.activity.BlockAllCardRemasterActivity;
import com.indocyber.itsmeandroid.viewremastered.blockallcard.activity.SetPinBlockCardActivity;
import com.indocyber.itsmeandroid.viewremastered.home.activity.HomeRemastered;
import com.indocyber.itsmeandroid.viewremastered.loginandregister.LoginActivityRemastered;
import com.indocyber.itsmeandroid.viewremastered.loginandregister.LoginAuthActivityRemastered;
import com.indocyber.itsmeandroid.viewremastered.loginandregister.RegisterActivityRemastered;
import com.indocyber.itsmeandroid.viewremastered.loginandregister.SetPinActivityRemastered;
import com.indocyber.itsmeandroid.viewremastered.promo.Activity.DetailPromoActivity;
import com.indocyber.itsmeandroid.viewremastered.tagkartu.TagKartu;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/*
 *
 *
 *@Author
 *@Version
 */
@Module
public abstract class ActivityBuilder {

    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract RegisterActivityRemastered registerActivityRemastered();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector(modules = HomeFragmentModule.class)
    abstract HomeRemastered homeActivity();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract SetPinActivityRemastered pinActivity();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract TagKartu tagKartu();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract LoginAuthActivityRemastered loginAuthActivityRemastered();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract DetailPromoActivity detailPromoActivity();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract EditProfileActivity editProfileActivity();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract BlockAllCardRemasterActivity blockAllCardRemasterActivity();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract SetPinBlockCardActivity setPinBlockCardActivity();
}
