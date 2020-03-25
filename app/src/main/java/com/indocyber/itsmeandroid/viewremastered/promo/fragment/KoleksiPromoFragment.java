package com.indocyber.itsmeandroid.viewremastered.promo.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.model.PromoMenuModel;
import com.indocyber.itsmeandroid.view.BaseFragment;
import com.indocyber.itsmeandroid.view.promo.adapter.PromoMenuAdapter;
import com.indocyber.itsmeandroid.viewmodel.HomeViewModel;
import com.indocyber.itsmeandroid.viewmodel.ViewModelFactory;
import com.indocyber.itsmeandroid.viewremastered.kartuku.adapter.CardFilterAdapter;
import com.indocyber.itsmeandroid.viewremastered.kartuku.adapter.CardListAdapter;
import com.indocyber.itsmeandroid.viewremastered.morecard.activity.MoreCardRemasteredActivity;
import com.indocyber.itsmeandroid.viewremastered.promo.Adapter.CreditCardAdapter;
import com.indocyber.itsmeandroid.viewremastered.promo.Adapter.MenuPromoAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.CARD_TYPE;
import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.CREDIT_CARD;
import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.INTENT_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class KoleksiPromoFragment extends BaseFragment implements PromoMenuAdapter.Listener, CreditCardAdapter.Listener {
    @BindView(R.id.recyclerMenuKoleksiPromoHorizontal)
    RecyclerView cardFilterRecycler;
    @BindView(R.id.recyclerCardCollection)
    RecyclerView cardListRecycler;
    private CardFilterAdapter cardFilterAdapter;
    private CardListAdapter cardListAdapter;
    private Fragment mFragmentIndicator = null;
    private HomeViewModel viewModel;
    private List<String> cardFilterList;
    @Inject
    ViewModelFactory factory;
    private AlertDialog loader;

    @Override
    protected int layoutRes() {
        return R.layout.fragment_koleksi_promo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cardFilterList = new ArrayList<>();
        cardFilterList.add("Semua");
        View koleksiPromoFragment = inflater.inflate(layoutRes(), container, false);
        ButterKnife.bind(this, koleksiPromoFragment);
//        mMenuSemuaKartuAdapter = new MenuPromoAdapter(semuaKartuMenu(), getActivity(), this);
//        mMenuBussinesAdapter = new MenuPromoAdapter(bussinesMenu(), getActivity(), this);
//        mMenuVacationAdapter = new MenuPromoAdapter(vacationMenu(), getActivity(), this);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        cardFilterRecycler.setLayoutManager(horizontalLayoutManager);
        cardListAdapter = new CardListAdapter(new ArrayList<>(), getActivity(), CREDIT_CARD);
        cardFilterAdapter = new CardFilterAdapter(cardFilterList, getActivity(), tag -> {
            if (tag.equalsIgnoreCase("Semua")) {
                viewModel.fetchAllCardList();
            } else {
                viewModel.getCardByTag(tag);
            }
        }, position -> {
            return;
        }, CardFilterAdapter.CREDIT_CARD);
        cardListRecycler.setAdapter(cardListAdapter);
        cardFilterRecycler.setAdapter(cardFilterAdapter);
//        initCard();

        return koleksiPromoFragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(getParentFragment().getActivity()).get(HomeViewModel.class);
        viewModel.getAllTaglist();
//        viewModel.getCardList();
        observeViewModel();
    }

    private List<PromoMenuModel> semuaKartuMenu() {
        List<PromoMenuModel> semuaKartuList = new ArrayList<>();
        semuaKartuList.clear();
        semuaKartuList.add(new PromoMenuModel("Semua Kartu", R.color.colorPrimary, R.drawable.button_coloraccent));
        semuaKartuList.add(new PromoMenuModel("Bussines", R.color.grey2, R.drawable.button_border_grey));
        semuaKartuList.add(new PromoMenuModel("Vacation", R.color.grey2, R.drawable.button_border_grey));

        return semuaKartuList;
    }

//    private void initCard(){
//        mRecyclerCardCollection.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//        mCardViewAdapter = new CreditCardAdapter(new ArrayList<>(), getActivity(),this);
//        mCardViewAdapter.refreshCardList(generateCardList());
//        mRecyclerCardCollection.setAdapter(mCardViewAdapter);
//    }

    private List<ImageCardModel> generateCardList(){
        List<ImageCardModel> cardList =  new ArrayList<>();
        cardList.add(new ImageCardModel(R.drawable.img_kartukredit_bca,"1112113131611512","mudiono ","12/13","20.000.000","12/19 12/24",null,false));
        cardList.add(new ImageCardModel(R.drawable.img_kartukredit_bca,"1112113131611512","mudiono ","12/13","20.000.000","12/19 12/24",null,false));
        cardList.add(new ImageCardModel(R.drawable.img_kartukredit_bca,"1112113131611512","mudiono ","12/13","20.000.000","12/19 12/24",null,false));

        return cardList;
    }

//    private List<PromoMenuModel> bussinesMenu() {
//        List<PromoMenuModel> bussinesMenuList = new ArrayList<>();
//        bussinesMenuList.clear();
//        bussinesMenuList.add(new PromoMenuModel("Semua Kartu", R.color.grey2, R.drawable.button_border_grey));
//        bussinesMenuList.add(new PromoMenuModel("Bussines", R.color.colorPrimary, R.drawable.button_coloraccent));
//        bussinesMenuList.add(new PromoMenuModel("Vacation", R.color.grey2, R.drawable.button_border_grey));
//
//        return bussinesMenuList;
//    }
//
//    private List<PromoMenuModel> vacationMenu() {
//        List<PromoMenuModel> vacationMenuList = new ArrayList<>();
//        vacationMenuList.clear();
//        vacationMenuList.add(new PromoMenuModel("Semua Kartu", R.color.grey2, R.drawable.button_border_grey));
//        vacationMenuList.add(new PromoMenuModel("Bussines", R.color.grey2, R.drawable.button_border_grey));
//        vacationMenuList.add(new PromoMenuModel("Vacation", R.color.colorPrimary, R.drawable.button_coloraccent));
//
//        return vacationMenuList;
//    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        if (fragment == null) {
            fragment = new SemuaPromoFragment();
        }
        fragmentTransaction.replace(R.id.frameLayoutPromo, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(int position) {

    }

    @Override
    public void MoreCardonClick(ImageCardModel imgCardModel) {
        Intent intent = new Intent(getActivity(), MoreCardRemasteredActivity.class);
        intent.putExtra(CARD_TYPE,CREDIT_CARD);
        intent.putExtra(INTENT_ID,(Parcelable)imgCardModel);
        startActivity(intent);
    }

    private void observeViewModel() {
        viewModel.getCardList().observe(this, imageCardModels -> {
            cardListAdapter.refreshCardList(imageCardModels);
        });

        viewModel.getTagList().observe(this, strings -> {
            if (strings != null) {
                cardFilterAdapter.refreshFilterList(strings);
            }
        });
    }
}
