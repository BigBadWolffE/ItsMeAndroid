package com.indocyber.itsmeandroid.view.profile.fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.User;
import com.indocyber.itsmeandroid.utilities.Preference;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;
import com.indocyber.itsmeandroid.view.BaseFragment;
import com.indocyber.itsmeandroid.viewmodel.ProfileDetailViewModel;
import com.indocyber.itsmeandroid.viewmodel.ViewModelFactory;

import java.util.HashMap;

import javax.inject.Inject;

import dmax.dialog.SpotsDialog;


public class DetailProfileFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String FIELD_ADDRESS = "address";
    public static final String FIELD_PHONE = "phone";
    public static final String FIELD_PASSWORD = "password";

    private String mParam1;
    private String mParam2;
    private AlertDialog mLoader;
    private EditText mNamaLengkap, mAlamat, mEmailAddress, mNoTelp, mPass, mPin, mSecretQuestion;
    private TextView mErrorValidation;
    private ImageView mEditAlamat, mEditNoTelp, mEditPass, mEditPin;
    private Preference mPreference;
    private ProfileDetailViewModel viewModel;
    private String currentText;
    @Inject
    ViewModelFactory factory;

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
    protected int layoutRes() {
        return R.layout.fragment_detail_profile;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        pref = getContext().getSharedPreferences("MyPref", 0);
//        editor = pref.edit();
//        Gson gson = new Gson();
//        String paramUserData = pref.getString("ProfileDetail", null);
//        mDetailModel = gson.fromJson(paramUserData, User.class);
        mLoader = new SpotsDialog.Builder()
                .setCancelable(false)
                .setContext(getActivity())
                .build();
        mPreference = new Preference(getActivity());
        String authKey = mPreference.getUserAuth();
        viewModel = ViewModelProviders.of(getActivity(), factory).get(ProfileDetailViewModel.class);
        viewModel.getUserData(authKey);
        mNamaLengkap = view.findViewById(R.id.txtProfileNamaLengkap);
        mAlamat = view.findViewById(R.id.txtProfileAlamat);
        mEditAlamat = view.findViewById(R.id.imgEditAlamat);
        mEmailAddress = view.findViewById(R.id.txtProfileEmail);
        mNoTelp = view.findViewById(R.id.txtProfileNoTelp);
        mEditNoTelp = view.findViewById(R.id.imgEditNoTelp);
        mPass = view.findViewById(R.id.txtProfilePassword);
        mEditPass = view.findViewById(R.id.imgEditPassword);
        mPin = view.findViewById(R.id.txtProfilePin);
        mSecretQuestion = view.findViewById(R.id.txtProfileSecretQuestion);
        mErrorValidation = view.findViewById(R.id.layoutError);

        mNamaLengkap.setEnabled(false);
        mNamaLengkap.setTextColor(Color.BLACK);
        mAlamat.setEnabled(false);
        mAlamat.setTextColor(Color.BLACK);
        mAlamat.setHorizontalScrollBarEnabled(false);
        mAlamat.setSingleLine(false);
        mAlamat.setOnFocusChangeListener(createFocusListener(FIELD_ADDRESS));
        mEmailAddress.setEnabled(false);
        mEmailAddress.setTextColor(Color.BLACK);
        mEmailAddress.setHorizontalScrollBarEnabled(false);
        mEmailAddress.setSingleLine(false);
        mNoTelp.setEnabled(false);
        mNoTelp.setTextColor(Color.BLACK);
        mNoTelp.setOnFocusChangeListener(createFocusListener(FIELD_PHONE));
        mPass.setEnabled(false);
        mPass.setOnFocusChangeListener(createFocusListener(FIELD_PASSWORD));
//        mPass.setTextColor(Color.BLACK);
        mPin.setEnabled(false);
//        mPin.setTextColor(Color.BLACK);
        mSecretQuestion.setEnabled(false);
        mSecretQuestion.setTextColor(Color.BLACK);
        mEditAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mAlamat.hasFocus()) {
                    mAlamat.setEnabled(true);
                    mAlamat.setSingleLine(false);
                    mAlamat.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
                    mAlamat.requestFocus();
                    mAlamat.setSelection(mAlamat.getText().length());
                } else {
                    mAlamat.clearFocus();
                    mAlamat.setEnabled(false);
                }
            }
        });

        mEditNoTelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mNoTelp.hasFocus()) {
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
                if (!mPass.hasFocus()) {
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

        mPin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else {
                    validationPin();
                    view.setBackgroundColor(getResources().getColor(R.color.colorwhite));
                    view.setEnabled(false);
                }
            }
        });
        
        observeViewModel();
    }

    private void validationPin() {
        if (mPin.getText().length() < 6) {
            mErrorValidation.setVisibility(View.VISIBLE);
        } else {
            mErrorValidation.setVisibility(View.GONE);
        }
    }

    private void setNotNull(User mDetailModel) {
        mNamaLengkap.setText(mDetailModel.getNamaLengkap());
        mAlamat.setText(mDetailModel.getAlamat());
        mEmailAddress.setText(mDetailModel.getEmail());
        mNoTelp.setText(mDetailModel.getNoTelp());
        mPass.setText(mDetailModel.getPassword());
        mPin.setText("000000");
        mSecretQuestion.setText(mDetailModel.getSecretAnswer());
    }

    private User setToModel() {
        User mDetailModelInside = new User();
        mDetailModelInside.setNamaLengkap(mNamaLengkap.getText().toString());
        mDetailModelInside.setAlamat(mAlamat.getText().toString());
        mDetailModelInside.setEmail(mEmailAddress.getText().toString());
        mDetailModelInside.setNoTelp(mNoTelp.getText().toString());
        mDetailModelInside.setPassword(mPass.getText().toString());
        mDetailModelInside.setSecretAnswer(mSecretQuestion.getText().toString());
        return mDetailModelInside;
    }

    private void observeViewModel() {
//        viewModel.getIsLoading().observe(this, isLoading -> {
//            if (isLoading) {
//                mLoader.show();
//            } else {
//                mLoader.dismiss();
//            }
//        });

        viewModel.getUser().observe(this, user -> {
            if (user != null) {
                setNotNull(user);
            }
        });

//        viewModel.getError().observe(this, error -> {
//            if (error != null) {
//                UtilitiesCore.buildAlertDialog(
//                        getActivity(),
//                        error,
//                        R.drawable.ic_invalid,
//                        dialogInterface -> dialogInterface.dismiss()
//                );
//            }
//        });
    }

    private View.OnFocusChangeListener createFocusListener(String field) {

        return (view, focus) -> {
            if (focus) {
                view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                if (view instanceof TextView) currentText = ((TextView) view).getText().toString();
            } else {
                view.setBackgroundColor(getResources().getColor(R.color.colorwhite));
                view.setEnabled(false);
                if (view instanceof TextView) {
                    String newText = ((TextView) view).getText().toString();
                    if (!currentText.equals(newText)) updateField(field, newText);
                }
            }
        };
    }

    private void updateField(String field, String value) {
        Preference preference = new Preference(getActivity());
        String authKey = preference.getUserAuth();
        HashMap<String, String> fieldUpdate = new HashMap<>();
        fieldUpdate.put(field, value);
        viewModel.updateProfile(authKey, fieldUpdate);
    }
}
