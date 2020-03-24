package com.indocyber.itsmeandroid.viewremastered.home.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.model.PromoItemModel;
import com.indocyber.itsmeandroid.services.network.Api;
import com.indocyber.itsmeandroid.utilities.Preference;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;
import com.indocyber.itsmeandroid.view.BaseFragment;
import com.indocyber.itsmeandroid.view.home.adapter.CardViewAdapter;
import com.indocyber.itsmeandroid.view.home.adapter.PromoPagerAdapter;
import com.indocyber.itsmeandroid.view.otp.OtpActivity;
import com.indocyber.itsmeandroid.viewmodel.HomeViewModel;
import com.indocyber.itsmeandroid.viewmodel.ViewModelFactory;
import com.indocyber.itsmeandroid.viewremastered.belipulsa.activity.BeliPulsaActivity;
import com.indocyber.itsmeandroid.viewremastered.blockallcard.activity.BlockAllCardRemasterActivity;
import com.indocyber.itsmeandroid.viewremastered.home.activity.HomeRemastered;
import com.indocyber.itsmeandroid.viewremastered.home.adapter.CardRemasteredAdapter;
import com.indocyber.itsmeandroid.viewremastered.notification.Activity.NotificationRemasteredActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

import static com.indocyber.itsmeandroid.utilities.UtilitiesCore.dpToPx;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeRemasteredFragment extends BaseFragment {

    public HomeRemasteredFragment() {
        // Required empty public constructor
    }

    private ViewPager mViewPagerCard;
    private RecyclerView mPromoRecyclerView;
    private CardRemasteredAdapter mCardAdapter;
    private TabLayout mTabDots;
    private RelativeLayout blockButton;
    @BindView(R.id.imageView4)
    FrameLayout mBtnNotif;
    private RelativeLayout mRltvBeliPulsa;
    private RelativeLayout mRltvBlockAllCard;
    private RelativeLayout mRltvEmpty;
    private TextView txtTambhKartu;
    private HomeViewModel viewModel;
    private AlertDialog loader;
    private ImageView userImage;

    @Override
    protected int layoutRes() {
        return R.layout.fragment_home_remastered;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(layoutRes(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPagerCard = view.findViewById(R.id.viewPagerCard);
        mPromoRecyclerView = view.findViewById(R.id.recPromoView);
        mCardAdapter = new CardRemasteredAdapter(getActivity());
        mTabDots = view.findViewById(R.id.tabDots);
        mRltvEmpty = view.findViewById(R.id.rltvEmpty);
        mRltvBeliPulsa = view.findViewById(R.id.rltvBeliPulsa);
        txtTambhKartu = view.findViewById(R.id.txtTambhKartu);
        mRltvBlockAllCard = view.findViewById(R.id.rltvBlockAllCard);
        Preference preference = new Preference(getActivity());
        userImage = view.findViewById(R.id.circleImageView);
        UtilitiesCore.loadImageFromUri(userImage, getActivity(), Api.PROFILE_IMAGE,
                preference.getUserAuth(), preference.getMetaData());
        TextView userDisplayName = view.findViewById(R.id.userName);
        userDisplayName.setText(preference.getLoggedUserFullname());
        viewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
        viewModel.fetchAllCardList();
        observeViewModel();
        //mDotsLayout = view.findViewById(R.id.layoutDots);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        initCard();
        initPromo();
        onClick();
    }

    private void onClick() {
        mRltvBeliPulsa.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), BeliPulsaActivity.class);
            startActivity(intent);
        });

        mRltvBlockAllCard.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), BlockAllCardRemasterActivity.class);
            startActivity(intent);
        });

    }


    private void initCard() {

//        if (generateCardList().size() > 0){
//            dataCardAvailable();
//        }else {
//            dataCardEmpty();
//        }
    }

    private void dataCardEmpty() {
        mRltvEmpty.setVisibility(View.VISIBLE);
        txtTambhKartu.bringToFront();
        mViewPagerCard.setVisibility(View.GONE);
        mTabDots.setVisibility(View.GONE);
    }

    private void dataCardAvailable(List<ImageCardModel> list) {
        mRltvEmpty.setVisibility(View.GONE);
        mViewPagerCard.setVisibility(View.VISIBLE);
        mTabDots.setVisibility(View.VISIBLE);
        mCardAdapter.insertData(list);
        mViewPagerCard.setAdapter(mCardAdapter);
        mTabDots.setupWithViewPager(mViewPagerCard, true);
        int paddingValue = dpToPx(getActivity(), 16);
        int marginValue = dpToPx(getActivity(), 8);
        mViewPagerCard.setPadding(paddingValue, 0, marginValue, 0);
        mViewPagerCard.setPageMargin(marginValue);
        // mViewPagerCard.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin_overlap_payment));
        mViewPagerCard.setOffscreenPageLimit(mCardAdapter.getCount());
    }

    private void initPromo() {
        mPromoRecyclerView.setLayoutManager( new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        PromoPagerAdapter promoAdapter = new PromoPagerAdapter(new ArrayList<>(), getActivity());
        promoAdapter.refreshPromoList(generatePromoList());


        mPromoRecyclerView.setAdapter(promoAdapter);
        promoAdapter.SetItemOnclickListener(new PromoPagerAdapter.onItemClickListener() {
            @Override
            public void onItemClick() {
                ((HomeRemastered) Objects.requireNonNull(getActivity())).onClickPromo();
            }
        });

    }

    private void observeViewModel() {
        viewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                loader = new SpotsDialog.Builder()
                        .setCancelable(false)
                        .setContext(getActivity())
                        .build();
                loader.show();
            } else {
                loader.dismiss();
            }
        });

        viewModel.getCardList().observe(this, imageCardModels -> {
            if (imageCardModels.size() > 0){
                dataCardAvailable(imageCardModels);
            }else {
                dataCardEmpty();
            }
        });
    }

    private List<ImageCardModel> generateCardList() {
        List<ImageCardModel> cardList = new ArrayList<>();
        cardList.add(new ImageCardModel(1, "1234123412341234", "Johan Sundstein", "12/25", "Rp 15.000.000", "12/20", "12/21", false));
        cardList.add(new ImageCardModel(2, "4321432143214321", "Johan Sundstein", "12/25", "Rp 15.000.000", "12/20", "12/21", false));
        return cardList;
    }

    private List<PromoItemModel> generatePromoList() {
        List<PromoItemModel> promoList = new ArrayList<>();
        promoList.add(new PromoItemModel("Promo Starbuck", "Promo Starbuck", "27 Desember 2020",
                R.drawable.img_banner_starbuck));
        promoList.add(new PromoItemModel("Promo Starbuck2", "Promo Starbuck2", "27 Desember 2020",
                R.drawable.img_banner_starbuck));
        return promoList;
    }

    @OnClick(R.id.imageView4)
    void openNotifikasi(){
        Intent i = new Intent(getActivity(), NotificationRemasteredActivity.class);
        startActivity(i);
    }
}
