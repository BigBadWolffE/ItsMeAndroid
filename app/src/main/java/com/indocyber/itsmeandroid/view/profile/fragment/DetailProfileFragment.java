package com.indocyber.itsmeandroid.view.profile.fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;

import com.indocyber.itsmeandroid.R;

public class DetailProfileFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private EditText mNamaLengkap, mAlamat, mEmailAddress, mNoTelp, mPass, mPin, mSecretQuestion;
    private ImageView mEditAlamat, mEditEmail, mEditNoTelp, mEditPass, mEditPin;

    public DetailProfileFragment() {
        // Required empty public constructor
    }

    public static DetailProfileFragment newInstance(String param1, String param2) {
        DetailProfileFragment fragment = new DetailProfileFragment();
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
        return inflater.inflate(R.layout.fragment_detail_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNamaLengkap = view.findViewById(R.id.txtProfileNamaLengkap);
        mAlamat = view.findViewById(R.id.txtProfileAlamat);
        mEditAlamat = view.findViewById(R.id.imgEditAlamat);
        mEmailAddress = view.findViewById(R.id.txtProfileEmail);
        mEditEmail = view.findViewById(R.id.imgEditEmail);
        mNoTelp = view.findViewById(R.id.txtProfileNoTelp);
        mEditNoTelp = view.findViewById(R.id.imgEditNoTelp);
        mPass = view.findViewById(R.id.txtProfilePassword);
        mEditPass = view.findViewById(R.id.imgEditPassword);
        mPin = view.findViewById(R.id.txtProfilePin);
        mEditPin = view.findViewById(R.id.imgEditPin);
        mSecretQuestion = view.findViewById(R.id.txtProfileSecretQuestion);

        mNamaLengkap.setEnabled(false);
        mNamaLengkap.setTextColor(Color.BLACK);
        mAlamat.setEnabled(false);
        mAlamat.setTextColor(Color.BLACK);
        mAlamat.setHorizontalScrollBarEnabled(false);
        mAlamat.setSingleLine(false);
        mEmailAddress.setEnabled(false);
        mEmailAddress.setTextColor(Color.BLACK);
        mEmailAddress.setHorizontalScrollBarEnabled(false);
        mEmailAddress.setSingleLine(false);
        mNoTelp.setEnabled(false);
        mNoTelp.setTextColor(Color.BLACK);
        mPass.setEnabled(false);
//        mPass.setTextColor(Color.BLACK);
        mPin.setEnabled(false);
//        mPin.setTextColor(Color.BLACK);
        mSecretQuestion.setEnabled(false);
        mSecretQuestion.setTextColor(Color.BLACK);

        mEditAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAlamat.setEnabled(true);
                mAlamat.setSingleLine(false);
                mAlamat.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
                mAlamat.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            }
        });

        mEditEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEmailAddress.setEnabled(true);
                mEmailAddress.setSingleLine(false);
                mEmailAddress.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
                mEmailAddress.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            }
        });

        mEditNoTelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNoTelp.setEnabled(true);
                mNoTelp.setInputType(InputType.TYPE_CLASS_PHONE);
            }
        });

    }
}
