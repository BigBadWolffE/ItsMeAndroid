package com.indocyber.itsmeandroid.di.FragmentBindingModules;

import com.indocyber.itsmeandroid.viewremastered.promo.fragment.KoleksiPromoFragment;
import com.indocyber.itsmeandroid.viewremastered.promo.fragment.PromoSpesialDiscountFragment;
import com.indocyber.itsmeandroid.viewremastered.promo.fragment.PromoTerdekatFragment;
import com.indocyber.itsmeandroid.viewremastered.promo.fragment.SemuaPromoFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/*
 *
 *
 *@Author
 *@Version
 */
@Module
public abstract class PromoFragmentModule {

    @ContributesAndroidInjector
    abstract SemuaPromoFragment semuaPromoFragment();

    @ContributesAndroidInjector
    abstract PromoTerdekatFragment promoTerdekatFragment();

    @ContributesAndroidInjector
    abstract KoleksiPromoFragment koleksiPromoFragment();

    @ContributesAndroidInjector
    abstract PromoSpesialDiscountFragment promoSpesialDiscountFragment();
}
