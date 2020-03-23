package com.indocyber.itsmeandroid.viewremastered.akun;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.view.BaseFragment;
import com.indocyber.itsmeandroid.viewremastered.resetpinfromaccount.ResetPinFromAkunActivityRemastered;
import com.indocyber.itsmeandroid.viewremastered.historytransaction.HistoryTransactionActivityRemastered;
import com.indocyber.itsmeandroid.viewremastered.loginandregister.ResetPasswordFromForgotActivity;
import com.indocyber.itsmeandroid.viewremastered.metodepembayaran.MetodePembayaranActivityRemastered;
import com.indocyber.itsmeandroid.viewremastered.resetpinfromaccount.ResetPinSebelumnyaActivityRemastered;

/**
 * A simple {@link Fragment} subclass.
 */
public class AkunRemasteredFragment extends BaseFragment {

    public AkunRemasteredFragment() {
        // Required empty public constructor
    }

    public static TextView tvHistory,tvReset,tvSecurity,tvMetodePembayaran;


    @Override
    protected int layoutRes() {
        return R.layout.fragment_akun_remastered;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(layoutRes(), container, false);



        tvHistory = rootView.findViewById(R.id.textView9);
        tvReset = rootView.findViewById(R.id.textView10);
        tvSecurity = rootView.findViewById(R.id.textView11);
        tvMetodePembayaran = rootView.findViewById(R.id.tv10);

        tvMetodePembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MetodePembayaranActivityRemastered.class);
                getActivity().startActivityForResult(intent,8);

            }
        });

        tvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(getActivity(), HistoryTransactionActivityRemastered.class);
                getActivity().startActivityForResult(register,1);
            }
        });

        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ResetPasswordFromForgotActivity.class);
                getActivity().startActivityForResult(intent,9);
            }
        });
        tvSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ResetPinSebelumnyaActivityRemastered.class);
                getActivity().startActivityForResult(intent,9);
            }
        });




        return rootView;

    }
}
