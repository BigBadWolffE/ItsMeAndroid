package com.indocyber.itsmeandroid.viewremastered.pullovermenu.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.indocyber.itsmeandroid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPullOverMenu extends BottomSheetDialogFragment {

    private BottomSheetBehavior mBehavior;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        final View view = View.inflate(getContext(), R.layout.fragment_pull_over_menu, null);

        dialog.setContentView(view);
        mBehavior.setPeekHeight(BottomSheetBehavior.STATE_EXPANDED);

        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (BottomSheetBehavior.STATE_EXPANDED == newState) {
                    //showView(app_bar_layout, getActionBarSize());
                    //hideView(lyt_profile);
                }
                if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
                    //hideView(app_bar_layout);
                    //showView(lyt_profile, getActionBarSize());
                    dismiss();
                }

                if (BottomSheetBehavior.STATE_HIDDEN == newState) {
                    dismiss();

                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });


        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

}
