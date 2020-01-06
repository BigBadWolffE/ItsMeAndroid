package com.indocyber.itsmeandroid.view.profile.fragment;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ProfileDetailModel;
import com.indocyber.itsmeandroid.model.ProfileKTPModel;

public class DetailProfileFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private EditText mNamaLengkap, mAlamat, mEmailAddress, mNoTelp, mPass, mPin, mSecretQuestion;
    private ImageView mEditAlamat, mEditEmail, mEditNoTelp, mEditPass, mEditPin;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private ProfileDetailModel mDetailModel = new ProfileDetailModel();

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

        pref = getContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        Gson gson = new Gson();
        String paramUserData = pref.getString("ProfileDetail", null);
        mDetailModel = gson.fromJson(paramUserData, ProfileDetailModel.class);

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

        if (mDetailModel != null) {
            setNotNull();
        }

        View.OnFocusChangeListener focusListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (focus) {
                    view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else {
                    view.setBackgroundColor(getResources().getColor(R.color.colorwhite));
                    view.setEnabled(false);
                }
            }
        };

        mNamaLengkap.setEnabled(false);
        mNamaLengkap.setTextColor(Color.BLACK);
        mAlamat.setEnabled(false);
        mAlamat.setTextColor(Color.BLACK);
        mAlamat.setHorizontalScrollBarEnabled(false);
        mAlamat.setSingleLine(false);
        mAlamat.setOnFocusChangeListener(focusListener);
        mEmailAddress.setEnabled(false);
        mEmailAddress.setTextColor(Color.BLACK);
        mEmailAddress.setHorizontalScrollBarEnabled(false);
        mEmailAddress.setSingleLine(false);
        mEmailAddress.setOnFocusChangeListener(focusListener);
        mNoTelp.setEnabled(false);
        mNoTelp.setTextColor(Color.BLACK);
        mNoTelp.setOnFocusChangeListener(focusListener);
        mPass.setEnabled(false);
        mPass.setOnFocusChangeListener(focusListener);
//        mPass.setTextColor(Color.BLACK);
        mPin.setEnabled(false);
        mPin.setOnFocusChangeListener(focusListener);
//        mPin.setTextColor(Color.BLACK);
        mSecretQuestion.setEnabled(false);
        mSecretQuestion.setTextColor(Color.BLACK);
        mSecretQuestion.setOnFocusChangeListener(focusListener);
        mEditAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mAlamat.requestFocus()) {
                    mAlamat.setEnabled(true);
                    mAlamat.setSingleLine(false);
                    mAlamat.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
                    mAlamat.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                    mAlamat.requestFocus();
                    mAlamat.setSelection(mAlamat.getText().length());
                } else {
                    mAlamat.clearFocus();
                    mAlamat.setEnabled(false);
                }
            }
        });

        mEditEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mEmailAddress.requestFocus()) {
                    mEmailAddress.setEnabled(true);
                    mEmailAddress.setSingleLine(false);
                    mEmailAddress.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
                    mEmailAddress.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    mEmailAddress.requestFocus();
                    mEmailAddress.setSelection(mEmailAddress.getText().length());
                } else {
                    mEmailAddress.clearFocus();
                    mEmailAddress.setEnabled(false);
                }
            }
        });

        mEditNoTelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mNoTelp.requestFocus()) {
                    mNoTelp.setEnabled(true);
                    mNoTelp.setInputType(InputType.TYPE_CLASS_PHONE);
                    mNoTelp.requestFocus();
                    mNoTelp.setSelection(mNoTelp.getText().length());
                } else {
                    mNoTelp.clearFocus();
                    mNoTelp.setEnabled(false);
                }
            }
        });

        mEditPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mPass.requestFocus()) {
                    mPass.setEnabled(true);
                    mPass.requestFocus();
                    mPass.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mPass.setSelection(mPass.getText().length());
                } else {
                    mPass.clearFocus();
                    mPass.setEnabled(false);
                }
            }
        });

        mEditPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mPin.requestFocus()) {
                    mPin.setEnabled(true);
                    mPin.requestFocus();
                    mPin.setInputType(InputType.TYPE_CLASS_NUMBER
                            | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                    mPin.setSelection(mPin.getText().length());
                } else {
                    mPin.clearFocus();
                    mPin.setEnabled(false);
                }
            }
        });
    }

    private void setNotNull() {
        mNamaLengkap.setText(mDetailModel.getNamaLengkap());
        mAlamat.setText(mDetailModel.getAlamat());
        mEmailAddress.setText(mDetailModel.getEmail());
        mNoTelp.setText(mDetailModel.getNoTelp());
        mPass.setText(mDetailModel.getPassword());
        mPin.setText(mDetailModel.getPin());
        mSecretQuestion.setText(mDetailModel.getHobby());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ProfileDetailModel mDetailModelInside = new ProfileDetailModel();
        mDetailModelInside = setToModel();
        saveToSharedPreferences(mDetailModelInside);
    }

    private void saveToSharedPreferences(ProfileDetailModel mDetailModelInside) {
        Gson gson = new Gson();
        String json = gson.toJson(mDetailModelInside);
        editor.putString("ProfileDetail", json);
        editor.commit();
    }

    private ProfileDetailModel setToModel() {
        ProfileDetailModel mDetailModelInside = new ProfileDetailModel();
        mDetailModelInside.setNamaLengkap(mNamaLengkap.getText().toString());
        mDetailModelInside.setAlamat(mAlamat.getText().toString());
        mDetailModelInside.setEmail(mEmailAddress.getText().toString());
        mDetailModelInside.setNoTelp(mNoTelp.getText().toString());
        mDetailModelInside.setPassword(mPass.getText().toString());
        mDetailModelInside.setPin(mPin.getText().toString());
        mDetailModelInside.setHobby(mSecretQuestion.getText().toString());
        return mDetailModelInside;
    }
}
