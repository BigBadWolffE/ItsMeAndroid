package com.indocyber.itsmeandroid.di;

import androidx.lifecycle.ViewModel;

import com.indocyber.itsmeandroid.viewmodel.DetailPromoViewModel;
import com.indocyber.itsmeandroid.viewmodel.EditCardViewModel;
import com.indocyber.itsmeandroid.viewmodel.EditProfileViewModel;
import com.indocyber.itsmeandroid.viewmodel.HomeViewModel;
import com.indocyber.itsmeandroid.viewmodel.LoginViewModel;
import com.indocyber.itsmeandroid.viewmodel.PinActivityViewModel;
import com.indocyber.itsmeandroid.viewmodel.ProfileDetailViewModel;
import com.indocyber.itsmeandroid.viewmodel.RegisterViewModel;
import com.indocyber.itsmeandroid.viewmodel.TagKartuViewModel;
import com.indocyber.itsmeandroid.viewremastered.loginandregister.LoginAuthActivityRemastered;

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

    @Binds
    @IntoMap
    @ViewModelKey(EditCardViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsEditCardViewModel(EditCardViewModel editCardViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PinActivityViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsPinActivityViewModel(PinActivityViewModel pinActivityViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TagKartuViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsTagKartuViewModel(TagKartuViewModel tagKartuViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailPromoViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsDetailPromoViewModel(DetailPromoViewModel detailPromoViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EditProfileViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsEditProfileViewModel(EditProfileViewModel editProfileViewModel);
}
