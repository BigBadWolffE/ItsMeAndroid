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
import static com.indocyber.itsmeandroid.utilities.GlobalVariabel.PERSONAL_CARD;
import static com.indocyber.itsmeandroid.utilities.core.Animations.fadeOutIn;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonalCardList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalCardList extends Fragment {
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

    public PersonalCardList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonalCardList.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonalCardList newInstance(String param1, String param2) {
        PersonalCardList fragment = new PersonalCardList();
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
        View view = inflater.inflate(R.layout.fragment_personal_card_list, container, false);
        RecyclerView filterRecycler = view.findViewById(R.id.recyclerCardFilter);
        cardListRecycler = view.findViewById(R.id.recyclerCardList);

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        cardListRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        cardAdapter = new CardListAdapter(new ArrayList<>(), getActivity(), PERSONAL_CARD);

        filterRecycler.setLayoutManager(horizontalLayoutManager);

        cardFilterAdapter = new CardFilterAdapter(generateCardFilter(), getActivity(), tag -> {
            return;
        }, position -> {
            if (position == 0) {
                fadeOutIn(cardListRecycler);
                cardAdapter.refreshCardList(generateCardList());
            } else if (position == 1) {
                fadeOutIn(cardListRecycler);
                cardAdapter.refreshCardList(generateCardListKtp());
            } else if (position == 2) {
                fadeOutIn(cardListRecycler);
                cardAdapter.refreshCardList(generateCardListNpwp());
            } else if (position == 3) {
                fadeOutIn(cardListRecycler);
                cardAdapter.refreshCardList(generateCardListSim());
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
        cardList.add(new ImageCardModel(R.drawable.img_ktp, "", "", "", "Rp 15.000.000", "", "", false));
        cardList.add(new ImageCardModel(R.drawable.img_npwp, "", "", "", "Rp 15.000.000", "", "", false));
        cardList.add(new ImageCardModel(R.drawable.img_sim, "", "", "", "Rp 15.000.000", "", "", false));
        return cardList;
    }

    private List<ImageCardModel> generateCardListKtp() {
        List<ImageCardModel> cardList = new ArrayList<>();
        cardList.add(new ImageCardModel(R.drawable.img_ktp, "", "", "", "Rp 15.000.000", "", "", false));

        return cardList;
    }

    private List<ImageCardModel> generateCardListNpwp() {
        List<ImageCardModel> cardList = new ArrayList<>();
        cardList.add(new ImageCardModel(R.drawable.img_npwp, "", "", "", "Rp 15.000.000", "", "", false));
        return cardList;
    }

    private List<ImageCardModel> generateCardListSim() {
        List<ImageCardModel> cardList = new ArrayList<>();
        cardList.add(new ImageCardModel(R.drawable.img_sim, "", "", "", "Rp 15.000.000", "", "", false));
        return cardList;
    }

    private List<String> generateCardFilter() {
        List<String> filterList = new ArrayList<>();
        filterList.add("Semua");
        filterList.add("Ktp");
        filterList.add("Npwp");
        filterList.add("Sim");

        return filterList;
    }

//    private void onClick() {
//        cardFilterAdapter.SetItemOnclickListener(new CardFilterAdapter.onItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                if (position == 0) {
//                    fadeOutIn(cardListRecycler);
//                    cardAdapter.refreshCardList(generateCardList());
//                } else if (position == 1) {
//                    fadeOutIn(cardListRecycler);
//                    cardAdapter.refreshCardList(generateCardListKtp());
//                } else if (position == 2) {
//                    fadeOutIn(cardListRecycler);
//                    cardAdapter.refreshCardList(generateCardListNpwp());
//                } else if (position == 3) {
//                    fadeOutIn(cardListRecycler);
//                    cardAdapter.refreshCardList(generateCardListSim());
//                }
//            }
//        });
//    }
}
