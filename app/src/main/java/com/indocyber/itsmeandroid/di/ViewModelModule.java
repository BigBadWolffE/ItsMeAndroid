package com.indocyber.itsmeandroid.di;

import androidx.lifecycle.ViewModel;

import com.indocyber.itsmeandroid.viewmodel.HomeViewModel;
import com.indocyber.itsmeandroid.viewmodel.LoginViewModel;
import com.indocyber.itsmeandroid.viewmodel.ProfileDetailViewModel;
import com.indocyber.itsmeandroid.viewmodel.RegisterViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/*
 *
 *
 *@Author
 *@Version
 */
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsRegisterViewModel(RegisterViewModel registerViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsLoginViewModel(LoginViewModel loginViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProfileDetailViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsProfileDetailViewModel(ProfileDetailViewModel profileDetailViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsHomeViewModel(HomeViewModel homeViewModel);
}
