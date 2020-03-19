package com.indocyber.itsmeandroid.viewremastered.kartuku.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.model.PromoMenuModel;
import com.indocyber.itsmeandroid.viewremastered.kartuku.adapter.CardFilterAdapter;
import com.indocyber.itsmeandroid.viewremastered.kartuku.adapter.CardListAdapter;
import com.indocyber.itsmeandroid.viewremastered.promo.Adapter.MenuPromoAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreditCardList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreditCardList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_credit_card_list, container, false);
        RecyclerView filterRecycler = view.findViewById(R.id.recyclerCardFilter);
        RecyclerView cardListRecycler = view.findViewById(R.id.recyclerCardList);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        cardListRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        CardListAdapter cardAdapter = new CardListAdapter(new ArrayList<>(), getActivity());
        filterRecycler.setLayoutManager(horizontalLayoutManager);
        CardFilterAdapter cardFilterAdapter = new CardFilterAdapter(generateCardFilter(), getActivity());
        cardAdapter.refreshCardList(generateCardList());
        cardListRecycler.setAdapter(cardAdapter);
        filterRecycler.setAdapter(cardFilterAdapter);
        return view;
    }

    private List<ImageCardModel> generateCardList() {
        List<ImageCardModel> cardList = new ArrayList<>();
        cardList.add(new ImageCardModel(R.drawable.img_kartukredit_bca, "1234123412341234", "Johan Sundstein", "12/25", "Rp 15.000.000", "12/20", "12/21", false));
        cardList.add(new ImageCardModel(R.drawable.img_kartukredit_bca, "4321432143214321", "Johan Sundstein", "12/25", "Rp 15.000.000", "12/20", "12/21", false));
        ImageCardModel blockedModel = new ImageCardModel(R.drawable.img_kartukredit_bca, "4321432143214321", "Johan Sundstein", "12/25", "Rp 15.000.000", "12/20", "12/21", false);
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

}
