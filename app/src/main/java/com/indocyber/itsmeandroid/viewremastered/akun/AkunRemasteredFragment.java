package com.indocyber.itsmeandroid.viewremastered.akun;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.services.network.Api;
import com.indocyber.itsmeandroid.utilities.Preference;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;
import com.indocyber.itsmeandroid.view.BaseFragment;
import com.indocyber.itsmeandroid.viewremastered.resetpinfromaccount.ResetPinFromAkunActivityRemastered;
import com.indocyber.itsmeandroid.viewremastered.historytransaction.HistoryTransactionActivityRemastered;
import com.indocyber.itsmeandroid.viewremastered.loginandregister.ResetPasswordFromForgotActivity;
import com.indocyber.itsmeandroid.viewremastered.metodepembayaran.MetodePembayaranActivityRemastered;
import com.indocyber.itsmeandroid.viewremastered.resetpinfromaccount.ResetPinSebelumnyaActivityRemastered;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AkunRemasteredFragment extends BaseFragment {

    private ImageView profileImage;
    @BindView(R.id.textView9)
    TextView tvHistory;

    public static TextView tvReset,tvSecurity,tvMetodePembayaran;


    @Override
    protected int layoutRes() {
        return R.layout.fragment_akun_remastered;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(layoutRes(), container, false);

        ButterKnife.bind(this, rootView);
        TextView profileName = rootView.findViewById(R.id.userProfileName);
        Preference preference = new Preference(getActivity());
        profileName.setText(preference.getLoggedUserFullname());
        profileImage = rootView.findViewById(R.id.circleImageProfile);
        UtilitiesCore.loadImageFromUri(profileImage, getActivity(), Api.PROFILE_IMAGE,
                preference.getUserAuth(), preference.getMetaData());
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

    @OnClick(R.id.lblEditProfile)
    void editProfile(){
        Intent intent = new Intent(getActivity(), EditProfileActivity.class);
        startActivity(intent);
    }
}
