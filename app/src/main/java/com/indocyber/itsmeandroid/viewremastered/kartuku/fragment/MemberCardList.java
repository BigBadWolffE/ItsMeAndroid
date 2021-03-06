package com.indocyber.itsmeandroid.viewremastered.kartuku.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.viewremastered.kartuku.adapter.CardFilterAdapter;
import com.indocyber.itsmeandroid.viewremastered.kartuku.adapter.CardListAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.MEMBER_CARD;
import static com.indocyber.itsmeandroid.utilities.core.Animations.fadeOutIn;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MemberCardList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MemberCardList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CardFilterAdapter cardFilterAdapter;
    private CardListAdapter cardAdapter;
    private RecyclerView cardListRecycler;
    public MemberCardList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MemberCardList.
     */
    // TODO: Rename and change types and number of parameters
    public static MemberCardList newInstance(String param1, String param2) {
        MemberCardList fragment = new MemberCardList();
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
        View view = inflater.inflate(R.layout.fragment_member_card_list, container, false);
        RecyclerView filterRecycler = view.findViewById(R.id.recyclerCardFilter);
        cardListRecycler = view.findViewById(R.id.recyclerCardList);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        cardListRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        cardAdapter = new CardListAdapter(new ArrayList<>(), getActivity(),MEMBER_CARD);
        filterRecycler.setLayoutManager(horizontalLayoutManager);
        cardFilterAdapter = new CardFilterAdapter(generateCardFilter(), getActivity(), tag -> {
            return;
        }, position -> {
            if (position == 0) {
                fadeOutIn(cardListRecycler);
                cardAdapter.refreshCardList(generateCardList());
            } else if (position == 1) {
                fadeOutIn(cardListRecycler);
                cardAdapter.refreshCardList(generateCardListBusiness());
            } else if (position == 2) {
                fadeOutIn(cardListRecycler);
                cardAdapter.refreshCardList(generateCardListVacation());
            }
        }, "");
        cardAdapter.refreshCardList(generateCardList());
        cardListRecycler.setAdapter(cardAdapter);
        filterRecycler.setAdapter(cardFilterAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        onClick();
    }

    private List<ImageCardModel> generateCardList() {
        List<ImageCardModel> cardList = new ArrayList<>();
        cardList.add(new ImageCardModel(R.drawable.img_membercard_starbuck, "", "", "", "Rp 15.000.000", "", "", false));
        cardList.add(new ImageCardModel(R.drawable.img_membercard_celebrity_fitnest, "", "", "", "Rp 15.000.000", "", "12/21", false));
        cardList.add(new ImageCardModel(R.drawable.img_membercard_ancol, "", "", "", "Rp 15.000.000", "", "", false));
        return cardList;
    }
    private List<ImageCardModel> generateCardListBusiness() {
        List<ImageCardModel> cardList = new ArrayList<>();
        cardList.add(new ImageCardModel(R.drawable.img_membercard_starbuck, "", "", "", "Rp 15.000.000", "", "", false));
        cardList.add(new ImageCardModel(R.drawable.img_membercard_celebrity_fitnest, "", "", "", "Rp 15.000.000", "", "12/21", false));
        return cardList;
    }

    private List<ImageCardModel> generateCardListVacation() {
        List<ImageCardModel> cardList = new ArrayList<>();
        cardList.add(new ImageCardModel(R.drawable.img_membercard_ancol, "", "", "", "Rp 15.000.000", "", "", false));
        return cardList;
    }



    private List<String> generateCardFilter() {
        List<String> filterList = new ArrayList<>();
        filterList.add("Semua");
        filterList.add("Bussiness");
        filterList.add("Vacation");

        return filterList;
    }

//    private void onClick() {
//        cardFilterAdapter.setListener(new CardFilterAdapter.Listener() {
//            @Override
//            public void onItemClick(int position) {
//                if (position == 0) {
//                    fadeOutIn(cardListRecycler);
//                    cardAdapter.refreshCardList(generateCardList());
//                } else if (position == 1) {
//                    fadeOutIn(cardListRecycler);
//                    cardAdapter.refreshCardList(generateCardListBusiness());
//                } else if (position == 2) {
//                    fadeOutIn(cardListRecycler);
//                    cardAdapter.refreshCardList(generateCardListVacation());
//                }
//            }
//        });
//    }
}
