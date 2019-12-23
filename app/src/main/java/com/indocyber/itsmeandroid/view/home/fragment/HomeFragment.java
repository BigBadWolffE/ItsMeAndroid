package com.indocyber.itsmeandroid.view.home.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.indocyber.itsmeandroid.R;

import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.view.blockcc.activity.BlockCCActivity;

import com.indocyber.itsmeandroid.view.contactcc.activity.ContactCCActivity;
import com.indocyber.itsmeandroid.view.home.activity.HomeActivity;
import com.indocyber.itsmeandroid.view.home.adapter.ImageCardAdapter;
import com.indocyber.itsmeandroid.view.home.adapter.ImageHomeDashboardAdapter;
import com.indocyber.itsmeandroid.view.membershipsecuritycode.MembershipSecurityCodeActivity;

import com.indocyber.itsmeandroid.view.addcc.AddCcActivity;

import com.indocyber.itsmeandroid.view.message.MessageActivity;
import com.indocyber.itsmeandroid.view.notification.activity.NotificationActivity;
import com.indocyber.itsmeandroid.view.profile.activity.ProfileActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.indocyber.itsmeandroid.utilities.core.Animations.initShowOut;
import static com.indocyber.itsmeandroid.utilities.core.Animations.rotateFab;
import static com.indocyber.itsmeandroid.utilities.core.Animations.showIn;
import static com.indocyber.itsmeandroid.utilities.core.Animations.showOut;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
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

    private Button btnMemberhip, btnPersonal, btnCreditCard;
    private FloatingActionButton fab_add;

    private boolean rotate = false;
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


        mViewPager = view.findViewById(R.id.imagePage);
        mTabLayout = view.findViewById(R.id.tabDots);
        mViewPagerCard = view.findViewById(R.id.viewPagerCard);

        btnBlock = view.findViewById(R.id.btnBlock);
        btnContact = view.findViewById(R.id.btnContact);
        btnCall = view.findViewById(R.id.btnCall);
        btnChat = view.findViewById(R.id.btnChat);


    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.home_menu, menu);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity) Objects.requireNonNull(getActivity())).setmToolbarHomeFragment();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {

            setImageHomeSlider();
            setImageCardSlider();
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

            btnBlock.setOnClickListener(v -> {
                Intent i = new Intent(getActivity(), BlockCCActivity.class);
                startActivity(i);
            });

            btnContact.setOnClickListener(v -> {
                Intent i = new Intent(getActivity(), ContactCCActivity.class);
                startActivity(i);
            });
        }
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
            } else {
                showOut(btnMemberhip);
                showOut(btnPersonal);
                showOut(btnCreditCard);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.actionMessage) {
            Intent intent = new Intent(getActivity(), NotificationActivity.class);
            startActivity(intent);
        } else if (id == R.id.actionProfile) {
            Intent intent = new Intent(getActivity(), ProfileActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void setImageHomeSlider() {
        mImageHomeDashboardAdapter = new ImageHomeDashboardAdapter(getActivity(), mResources);
        mViewPager.setAdapter(mImageHomeDashboardAdapter);
        mTabLayout.setupWithViewPager(mViewPager, true);
    }

    private void setImageCardSlider() {
        dataImageCard().observe(Objects.requireNonNull(getActivity()), list -> {
            ImageCardAdapter adapter = new ImageCardAdapter(getActivity(), list);
            mViewPagerCard.setAdapter(adapter);
            mViewPagerCard.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin_overlap));
            mViewPagerCard.setOffscreenPageLimit(list.size());
        });

    }


    public static LiveData<List<ImageCardModel>> dataImageCard() {
        MutableLiveData<List<ImageCardModel>> list = new MutableLiveData<>();
        List<ImageCardModel> listModel = new ArrayList<>();

        listModel.add(new ImageCardModel(1, R.drawable.imgblankccanz, "9383 XXXX XXXX 6196",
                "Jordan Setiawan", "08/21", "Rp 20.000.000",
                "28 November 2019", "1 November 2019", true));
        listModel.add(new ImageCardModel(2, R.drawable.imgblankccbca, "5158 XXXX XXXX 6929",
                "Jordan Setiawan", "08/21", "Rp 35.000.000",
                "15 November 2019", "1 November 2019", false));
        listModel.add(new ImageCardModel(3, R.drawable.imgblankccmandiri, "5152 XXXX XXXX 1252",
                "Jordan Setiawan", "08/21", "Rp 10.000.000",
                "10 November 2019", "16 Desember 2019", false));

        list.setValue(listModel);

        return list;
    }
}
