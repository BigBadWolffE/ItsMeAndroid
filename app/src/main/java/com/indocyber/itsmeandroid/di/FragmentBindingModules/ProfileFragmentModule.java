package com.indocyber.itsmeandroid.di.FragmentBindingModules;

import com.indocyber.itsmeandroid.view.profile.fragment.DetailProfileFragment;
import com.indocyber.itsmeandroid.view.profile.fragment.ProfileKTPFragment;
import com.indocyber.itsmeandroid.view.profile.fragment.ProfileNPWPFragment;
import com.indocyber.itsmeandroid.view.profile.fragment.ProfilePassportFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/*
 *
 *
 *@Author
 *@Version
 */
@Module
public abstract class ProfileFragmentModule {

    @ContributesAndroidInjector
    abstract DetailProfileFragment provideDetailFragment();

    @ContributesAndroidInjector
    abstract ProfileKTPFragment provideKtpFragment();

    @ContributesAndroidInjector
    abstract ProfileNPWPFragment provideNpwpFragment();

    @ContributesAndroidInjector
    abstract ProfilePassportFragment providePassportFragment();
//
//    @ContributesAndroidInjector
}
