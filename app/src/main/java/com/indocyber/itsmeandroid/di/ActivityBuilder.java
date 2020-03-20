package com.indocyber.itsmeandroid.di;

import com.indocyber.itsmeandroid.di.FragmentBindingModules.ProfileFragmentModule;
import com.indocyber.itsmeandroid.view.login.LoginWithEmailActivity;
import com.indocyber.itsmeandroid.view.profile.activity.ProfileActivity;
import com.indocyber.itsmeandroid.view.register.RegistrationActivity;
import com.indocyber.itsmeandroid.viewremastered.home.activity.HomeRemastered;

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
    abstract RegistrationActivity registrationActivity();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract LoginWithEmailActivity loginActivity();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector(modules = ProfileFragmentModule.class)
    abstract ProfileActivity profileActivity();

}
