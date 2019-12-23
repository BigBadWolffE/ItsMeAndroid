package com.indocyber.itsmeandroid.view.blockcc.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.view.blockcc.adapter.BlockCCAdapter;
import com.indocyber.itsmeandroid.view.home.activity.HomeActivity;

import java.util.Objects;

import static com.indocyber.itsmeandroid.view.home.fragment.HomeFragment.dataImageCard;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlockCCFragment extends Fragment {


    public BlockCCFragment() {
        // Required empty public constructor
    }

    private RecyclerView mRlvBlock;
    private Button mBtnBlock;
    private BlockCCAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_block_cc, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRlvBlock = view.findViewById(R.id.rlvBlock);
        mBtnBlock = view.findViewById(R.id.btnBlock);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            ((HomeActivity) getActivity()).setmToolbarBlockFragment();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            dataImageCard().observe(Objects.requireNonNull(getActivity()), list -> {
                mAdapter = new BlockCCAdapter(getActivity());
                mAdapter.setListNotes(list);
                mRlvBlock.setAdapter(mAdapter);
                mRlvBlock.setLayoutManager(new LinearLayoutManager(getActivity()));
                mRlvBlock.setHasFixedSize(true);
               /* mBtnBlock.setOnClickListener(v -> {
                    list.remove(0);
                    mAdapter.setListNotes(list);

                });*/
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (getActivity() != null) {
            ((HomeActivity) getActivity()).setmToolbarHomeFragment();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (data != null) {
            if (requestCode == REQUEST_BLOCK_DELETE){
                if (resultCode == REQUEST_BLOCK_DELETE){
                    int position = Integer.parseInt(data.getData().toString());
                    Toast.makeText(getActivity(), position+"", Toast.LENGTH_SHORT).show();
                }
            }
        }*/
    }
}
