package com.indocyber.itsmeandroid.viewremastered.akun;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.viewremastered.historytransaction.HistoryTransactionActivityRemastered;
import com.indocyber.itsmeandroid.viewremastered.loginandregister.LoginActivityRemastered;
import com.indocyber.itsmeandroid.viewremastered.loginandregister.ResetPasswordFromForgotActivity;
import com.indocyber.itsmeandroid.viewremastered.metodepembayaran.MetodePembayaranActivityRemastered;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AkunRemasteredFragment extends Fragment {

    public AkunRemasteredFragment() {
        // Required empty public constructor
    }

    public static TextView tvHistory,tvReset,tvSecurity,tvMetodePembayaran;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_akun_remastered, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;

    }

    @OnClick(R.id.layoutHistory)
    void openHistory(){
        Intent register = new Intent(getActivity(), HistoryTransactionActivityRemastered.class);
        getActivity().startActivityForResult(register,1);

    }

    @OnClick(R.id.layoutMetodePembayaran)
    void  openMetodePembayaran(){
        Intent intent = new Intent(getActivity(), MetodePembayaranActivityRemastered.class);
        getActivity().startActivityForResult(intent,8);
    }

    @OnClick(R.id.layoutResetPassword)
    void openResetPassword(){
        Intent intent = new Intent(getActivity(), ResetPasswordFromForgotActivity.class);
        getActivity().startActivityForResult(intent,9);

    }

    @OnClick(R.id.layoutResetSecurityCode)
    void openResetSecurityCode(){

    }

    @OnClick(R.id.lblEditProfile)
    void EditProfile(){
        Intent intent = new Intent(getActivity(), EditProfileActivity.class);
        startActivity(intent);
    }
}
