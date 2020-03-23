package com.indocyber.itsmeandroid.viewremastered.promo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.model.PromoMenuModel;
import com.indocyber.itsmeandroid.view.promo.adapter.PromoMenuAdapter;
import com.indocyber.itsmeandroid.viewremastered.morecard.activity.MoreCardRemasteredActivity;
import com.indocyber.itsmeandroid.viewremastered.promo.Adapter.CreditCardAdapter;
import com.indocyber.itsmeandroid.viewremastered.promo.Adapter.MenuPromoAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.INTENT_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class KoleksiPromoFragment extends Fragment implements PromoMenuAdapter.Listener, CreditCardAdapter.Listener {
    @BindView(R.id.recyclerMenuKoleksiPromoHorizontal)
    RecyclerView mMenuPromoKoleksiRecycler;
    @BindView(R.id.recyclerCardCollection)
    RecyclerView mRecyclerCardCollection;
    private MenuPromoAdapter mMenuSemuaKartuAdapter, mMenuBussinesAdapter, mMenuVacationAdapter;
    private CreditCardAdapter mCardViewAdapter;
    private Fragment mFragmentIndicator = null;

    public KoleksiPromoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View koleksiPromoFragment = inflater.inflate(R.layout.fragment_koleksi_promo, container, false);
        ButterKnife.bind(this, koleksiPromoFragment);
        mMenuSemuaKartuAdapter = new MenuPromoAdapter(semuaKartuMenu(), getActivity(), this);
        mMenuBussinesAdapter = new MenuPromoAdapter(bussinesMenu(), getActivity(), this);
        mMenuVacationAdapter = new MenuPromoAdapter(vacationMenu(), getActivity(), this);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mMenuPromoKoleksiRecycler.setLayoutManager(horizontalLayoutManager);
        mMenuPromoKoleksiRecycler.setAdapter(mMenuSemuaKartuAdapter);
        initCard();

        return koleksiPromoFragment;
    }

    private List<PromoMenuModel> semuaKartuMenu() {
        List<PromoMenuModel> semuaKartuList = new ArrayList<>();
        semuaKartuList.clear();
        semuaKartuList.add(new PromoMenuModel("Semua Kartu", R.color.colorPrimary, R.drawable.button_coloraccent));
        semuaKartuList.add(new PromoMenuModel("Bussines", R.color.grey2, R.drawable.button_border_grey));
        semuaKartuList.add(new PromoMenuModel("Vacation", R.color.grey2, R.drawable.button_border_grey));

        return semuaKartuList;
    }

    private void initCard(){
        mRecyclerCardCollection.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mCardViewAdapter = new CreditCardAdapter(new ArrayList<>(), getActivity(),this);
        mCardViewAdapter.refreshCardList(generateCardList());
        mRecyclerCardCollection.setAdapter(mCardViewAdapter);
    }

    private List<ImageCardModel> generateCardList(){
        List<ImageCardModel> cardList =  new ArrayList<>();
        cardList.add(new ImageCardModel(R.drawable.img_kartukredit_bca,"12345","","","","","",false));
        cardList.add(new ImageCardModel(R.drawable.img_kartukredit_citi,"12345","","","","","",false));
        cardList.add(new ImageCardModel(R.drawable.img_kartukredit_anz,"12345","","","","","",false));

        return cardList;
    }

    private List<PromoMenuModel> bussinesMenu() {
        List<PromoMenuModel> bussinesMenuList = new ArrayList<>();
        bussinesMenuList.clear();
        bussinesMenuList.add(new PromoMenuModel("Semua Kartu", R.color.grey2, R.drawable.button_border_grey));
        bussinesMenuList.add(new PromoMenuModel("Bussines", R.color.colorPrimary, R.drawable.button_coloraccent));
        bussinesMenuList.add(new PromoMenuModel("Vacation", R.color.grey2, R.drawable.button_border_grey));

        return bussinesMenuList;
    }

    private List<PromoMenuModel> vacationMenu() {
        List<PromoMenuModel> vacationMenuList = new ArrayList<>();
        vacationMenuList.clear();
        vacationMenuList.add(new PromoMenuModel("Semua Kartu", R.color.grey2, R.drawable.button_border_grey));
        vacationMenuList.add(new PromoMenuModel("Bussines", R.color.grey2, R.drawable.button_border_grey));
        vacationMenuList.add(new PromoMenuModel("Vacation", R.color.colorPrimary, R.drawable.button_coloraccent));

        return vacationMenuList;
    }

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
        intent.putExtra(INTENT_ID,imgCardModel);
        startActivity(intent);
    }
}
