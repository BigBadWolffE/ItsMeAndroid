package com.indocyber.itsmeandroid.view.home.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.view.blockcc.activity.BlockCCActivity;
import com.indocyber.itsmeandroid.view.chat.ChatActivity;
import com.indocyber.itsmeandroid.view.contactcc.activity.ContactCCActivity;
import com.indocyber.itsmeandroid.view.home.activity.HomeActivity;
import com.indocyber.itsmeandroid.view.home.adapter.ImageCardAdapter;
import com.indocyber.itsmeandroid.view.home.adapter.ImageHomeDashboardAdapter;
import com.indocyber.itsmeandroid.view.inbox.InboxActivity;
import com.indocyber.itsmeandroid.view.membershipsecuritycode.MembershipSecurityCodeActivity;
import com.indocyber.itsmeandroid.view.addcc.AddCcActivity;
import com.indocyber.itsmeandroid.view.profile.activity.ProfileActivity;
import com.indocyber.itsmeandroid.view.promo.activity.PromoActivity;
import com.indocyber.itsmeandroid.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

import static com.indocyber.itsmeandroid.utilities.core.Animations.initShowOut;
import static com.indocyber.itsmeandroid.utilities.core.Animations.rotateFab;
import static com.indocyber.itsmeandroid.utilities.core.Animations.showIn;
import static com.indocyber.itsmeandroid.utilities.core.Animations.showOut;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements ImageHomeDashboardAdapter.Listener {
    //private FloatingActionButton mFabMembership, mFabPersonal, mFabCreditCard;
    private int[] mResources = {
            R.drawable.imgsliderbannerstarbuck,
            R.drawable.imgsliderbannerstarbuck,
            R.drawable.imgsliderbannerstarbuck

    };
    private ViewPager mViewPager;
    private ViewPager mViewPagerCard;
    private ImageHomeDashboardAdapter mImageHomeDashboardAdapter;
    private TabLayout mTabLayout;
    private MaterialRippleLayout btnBlock,btnCall,btnChat,btnContact;
    private AlertDialog loader;
    private RelativeLayout mPlaceHolder;
    private Button btnMemberhip, btnPersonal, btnCreditCard;
    private FloatingActionButton fab_add;
    private ImageView imgDefault;
    private LinearLayout linearLayoutViewPager;
    private LinearLayout linearLayoutTab;

    private boolean rotate = false;
    private HomeViewModel viewModel;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnMemberhip = view.findViewById(R.id.btnMemberhip);
        btnPersonal = view.findViewById(R.id.btnPersonal);
        btnCreditCard = view.findViewById(R.id.btnCreditCard);
        fab_add = view.findViewById(R.id.fab_add);
        mPlaceHolder = view.findViewById(R.id.rlPlaceHolder);
        mViewPager = view.findViewById(R.id.imagePage);
        mTabLayout = view.findViewById(R.id.tabDots);
        mViewPagerCard = view.findViewById(R.id.viewPagerCard);

        btnBlock = view.findViewById(R.id.btnBlock);
        btnContact = view.findViewById(R.id.btnContact);
        btnCall = view.findViewById(R.id.btnCall);
        btnChat = view.findViewById(R.id.btnChat);

        imgDefault = view.findViewById(R.id.imgDefault);
        linearLayoutViewPager = view.findViewById(R.id.view);
        linearLayoutTab = view.findViewById(R.id.linearLayout2);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.home_menu, menu);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity) Objects.requireNonNull(getActivity())).setmToolbarHomeFragment();
        viewModel.fetchCardList();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {

            setImageHomeSlider();
            viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
            viewModel.fetchCardList();
            observeViewModel();
            fabAnimations();
           /* mFabMembership.setOnClickListener(v -> {
                    Intent i = new Intent(getActivity(), MembershipSecurityCodeActivity.class);
                    startActivity(i);
            });*/

            btnMemberhip.setOnClickListener(v -> {
                Intent i = new Intent(getActivity(), MembershipSecurityCodeActivity.class);
                startActivity(i);
            });

            btnPersonal.setOnClickListener(v -> {

            });

            btnCreditCard.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), AddCcActivity.class);
                startActivity(intent);
            });

            btnPersonal.setOnClickListener(view -> {
                Intent intent  = new Intent(getActivity(), ProfileActivity.class);
                intent.putExtra("GO_TO_KTP", 1);
                startActivity(intent);
            });

            btnBlock.setOnClickListener(v -> {
                Intent i = new Intent(getActivity(), BlockCCActivity.class);
                startActivity(i);
            });

            btnContact.setOnClickListener(v -> {
                Intent i = new Intent(getActivity(), ContactCCActivity.class);
                startActivity(i);
            });

            btnChat.setOnClickListener(v -> {
                Intent i = new Intent(getActivity(), ChatActivity.class);
              startActivity(i);
            });

            btnCall.setOnClickListener(v -> {
                dialPhoneNumber("123456789");
            });
        }
    }

    private void dimBackground(int visibility) {
        getActivity().findViewById(R.id.llTransparentLayer).setVisibility(visibility);
    }

    private void fabAnimations() {
        initShowOut(btnMemberhip);
        initShowOut(btnPersonal);
        initShowOut(btnCreditCard);

        fab_add.setOnClickListener(v -> {
            rotate = rotateFab(v, !rotate);
            if (rotate) {
                showIn(btnMemberhip);
                showIn(btnPersonal);
                showIn(btnCreditCard);
                dimBackground(View.VISIBLE);
            } else {
                showOut(btnMemberhip);
                showOut(btnPersonal);
                showOut(btnCreditCard);
                dimBackground(View.GONE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.actionMessage) {
            Intent intent = new Intent(getActivity(), InboxActivity.class);
            startActivity(intent);
        } else if (id == R.id.actionProfile) {
            Intent intent = new Intent(getActivity(), ProfileActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void setImageHomeSlider() {
        mImageHomeDashboardAdapter = new ImageHomeDashboardAdapter(getActivity(), mResources, this);
        mViewPager.setAdapter(mImageHomeDashboardAdapter);
        mTabLayout.setupWithViewPager(mViewPager, true);
    }

    // TODO: 13/01/2020 delete when confirmed safe
//    private void setImageCardSlider() {
//        dataImageCard().observe(Objects.requireNonNull(getActivity()), list -> {
//            ImageCardAdapter adapter = new ImageCardAdapter(getActivity(), list);
//            mViewPagerCard.setAdapter(adapter);
//            mViewPagerCard.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin_overlap));
//            mViewPagerCard.setOffscreenPageLimit(list.size());
//        });
//
//    }

//    public static LiveData<List<ImageCardModel>> dataImageCard() {
//        MutableLiveData<List<ImageCardModel>> list = new MutableLiveData<>();
//        List<ImageCardModel> listModel = new ArrayList<>();
//
//        listModel.add(new ImageCardModel(1, R.drawable.imgblankccanz, "9383 XXXX XXXX 6196",
//                "Jordan Setiawan", "08/21", "Rp 20.000.000",
//                "28 November 2019", "1 November 2019", true));
//        listModel.add(new ImageCardModel(2, R.drawable.imgblankccbca, "5158 XXXX XXXX 6929",
//                "Jordan Setiawan", "08/21", "Rp 35.000.000",
//                "15 November 2019", "1 November 2019", false));
//        listModel.add(new ImageCardModel(3, R.drawable.imgblankccmandiri, "5152 XXXX XXXX 1252",
//                "Jordan Setiawan", "08/21", "Rp 10.000.000",
//                "10 November 2019", "16 Desember 2019", false));
//
//        list.setValue(listModel);
//
//        return list;
//    }

    private void observeViewModel() {
        viewModel.getIsLoading().observe(getActivity(), isLoading -> {
            if (isLoading) {
                if (loader == null) {
                    loader = new SpotsDialog.Builder()
                            .setCancelable(false)
                            .setContext(getContext())
                            .build();
                }
                loader.show();
            } else {
                loader.dismiss();
            }
        });

        viewModel.getCardList().observe(getActivity(), list -> {
            if (list.size() > 0) {
                ImageCardAdapter adapter = new ImageCardAdapter(getActivity(), list);
                mPlaceHolder.setVisibility(View.GONE);
                mViewPagerCard.setVisibility(View.VISIBLE);
                mViewPagerCard.setAdapter(adapter);
                mViewPagerCard.setPageMargin(getResources()
                        .getDimensionPixelOffset(R.dimen.viewpager_margin_overlap));
                mViewPagerCard.setOffscreenPageLimit(list.size());
                imgDefault.setVisibility(View.GONE);
            } else {
                mViewPagerCard.setVisibility(View.GONE);
                mPlaceHolder.setVisibility(View.VISIBLE);
                imgDefault.setVisibility(View.VISIBLE);
                linearLayoutTab.setVisibility(View.GONE);
                linearLayoutViewPager.setVisibility(View.GONE);
            }
        });
    }

    private void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getActivity(), PromoActivity.class);
        startActivity(intent);
    }
}

