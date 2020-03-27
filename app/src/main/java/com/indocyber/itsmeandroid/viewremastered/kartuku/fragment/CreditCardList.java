package com.indocyber.itsmeandroid.viewremastered.kartuku.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.model.PromoMenuModel;
import com.indocyber.itsmeandroid.view.BaseFragment;
import com.indocyber.itsmeandroid.viewmodel.HomeViewModel;
import com.indocyber.itsmeandroid.viewremastered.kartuku.adapter.CardFilterAdapter;
import com.indocyber.itsmeandroid.viewremastered.kartuku.adapter.CardListAdapter;
import com.indocyber.itsmeandroid.viewremastered.promo.Adapter.MenuPromoAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.CREDIT_CARD;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreditCardList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreditCardList extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private HomeViewModel viewModel;
//    private LinearLayout emptyLayout;
    private LinearLayout collectionLayout;
    private CardListAdapter cardAdapter;
    private List<String> cardFilterList;
    private CardFilterAdapter cardFilterAdapter;
    private RecyclerView cardListRecycler;
    private RecyclerView filterRecycler;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreditCardList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreditCardList.
     */
    // TODO: Rename and change types and number of parameters
    public static CreditCardList newInstance(String param1, String param2) {
        CreditCardList fragment = new CreditCardList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        cardFilterList = new ArrayList<>();
//        setRetainInstance(true);
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_credit_card_list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cardFilterList.add("Semua");
        View view = inflater.inflate(layoutRes(), container, false);
        filterRecycler = view.findViewById(R.id.recyclerCardFilter);
        cardListRecycler = view.findViewById(R.id.recyclerCardList);
//        emptyLayout = view.findViewById(R.id.emptyCard);
        collectionLayout = view.findViewById(R.id.collection);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        cardListRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        filterRecycler.setLayoutManager(horizontalLayoutManager);
        cardAdapter = new CardListAdapter(new ArrayList<>(), getActivity(), CREDIT_CARD);
//        cardAdapter = new CardListAdapter(new ArrayList<>(), getActivity());
        cardFilterAdapter = new CardFilterAdapter(cardFilterList, getActivity(), tag -> {
            if (tag.equalsIgnoreCase("Semua")) {
                viewModel.fetchAllCardList();
            } else {
                viewModel.getCardByTag(tag);
            }
        }, position -> {
            return;
        }, CardFilterAdapter.CREDIT_CARD);
        cardListRecycler.setAdapter(cardAdapter);
        filterRecycler.setAdapter(cardFilterAdapter);
        cardListRecycler.setHasFixedSize(true);
        filterRecycler.setHasFixedSize(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(getParentFragment().getActivity()).get(HomeViewModel.class);
        viewModel.getAllTaglist();
//        viewModel.getCardList();
        observeViewModel();
    }

    private List<ImageCardModel> generateCardList() {
        List<ImageCardModel> cardList = new ArrayList<>();
        cardList.add(new ImageCardModel(R.drawable.img_blank_kartukredit_bca, "1234123412341234", "Johan Sundstein", "12/25", "Rp 15.000.000", "12/20", "12/21", false));
        cardList.add(new ImageCardModel(R.drawable.img_blank_kartukredit_bca, "4321432143214321", "Johan Sundstein", "12/25", "Rp 15.000.000", "12/20", "12/21", false));
        ImageCardModel blockedModel = new ImageCardModel(R.drawable.img_blank_kartukredit_bca, "4321432143214321", "Johan Sundstein", "12/25", "Rp 15.000.000", "12/20", "12/21", false);
        blockedModel.setBillingAddress("Cicalengka, Rt 02 / Rw /04 Kecamatan Jatiluhur");
        blockedModel.setBlockedCard(true);
        cardList.add(blockedModel);
        return cardList;
    }

    private List<String> generateCardFilter() {
        List<String> filterList = new ArrayList<>();
        filterList.add("Semua");
        filterList.add("Bussiness");
        filterList.add("Vacation");

        return filterList;
    }

    private void observeViewModel() {
        viewModel.getCardList().observe(this, imageCardModels -> {
//            if (imageCardModels != null) {
//                if (imageCardModels.size() > 0)
                cardAdapter.refreshCardList(imageCardModels);
//            } else {
//                emptyLayout.setVisibility(View.VISIBLE);
//                collectionLayout.setVisibility(View.GONE);
//            }
        });

        viewModel.getTagList().observe(this, strings -> {
            if (strings != null) {
                cardFilterAdapter.refreshFilterList(strings);
            }
        });
    }

}
