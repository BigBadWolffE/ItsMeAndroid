package com.indocyber.itsmeandroid.viewremastered.akun;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
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
import com.indocyber.itsmeandroid.viewremastered.BaseFragment;
import com.indocyber.itsmeandroid.viewremastered.loginandregister.LoginActivityRemastered;
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
    Preference preference;

    public static TextView tvReset,tvSecurity,tvMetodePembayaran,signOut;



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
        preference = new Preference(getActivity());
        profileName.setText(preference.getLoggedUserFullname());
        profileImage = rootView.findViewById(R.id.circleImageProfile);
        UtilitiesCore.loadImageFromUri(profileImage, getActivity(), Api.PROFILE_IMAGE,
                preference.getUserAuth(), preference.getMetaData());
        tvReset = rootView.findViewById(R.id.textView10);
        tvSecurity = rootView.findViewById(R.id.textView11);
        tvMetodePembayaran = rootView.findViewById(R.id.tv10);
        signOut = rootView.findViewById(R.id.tvSignOut);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
                ab.setTitle("Its Me!");
                ab.setMessage("Apakah anda yakin untuk keluar?");
                ab.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //if you want to kill app . from other then your main avtivity.(Launcher)
//                        android.os.Process.killProcess(android.os.Process.myPid());
//                        System.exit(1);
                        //LoginAuthActivityRemastered.etusernameauth.setText("");
                        preference.clearPref();
                        Intent intent = new Intent(getActivity(), LoginActivityRemastered.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        //if you want to finish just current activity
                    }
                });
                ab.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                ab.show();

            }
        });

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

    @Override
    public void onResume() {
        super.onResume();
        UtilitiesCore.loadImageFromUri(profileImage, getActivity(), Api.PROFILE_IMAGE,
                preference.getUserAuth(), preference.getMetaData());
    }
}
