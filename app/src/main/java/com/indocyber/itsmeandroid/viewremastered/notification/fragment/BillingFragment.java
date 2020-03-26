package com.indocyber.itsmeandroid.viewremastered.notification.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.Billing;
import com.indocyber.itsmeandroid.model.Notification;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;
import com.indocyber.itsmeandroid.viewremastered.notification.Adapter.BillingAdapter;
//import com.indocyber.itsmeandroid.view.billingdetail.BillingDetailActivity;
import com.indocyber.itsmeandroid.viewremastered.notification.Activity.DetailNotificationBillingActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillingFragment extends Fragment implements BillingAdapter.BillingListener {

    private final List<Billing> billingList = new ArrayList<>();
    private final BillingAdapter mBillingAdapter = new BillingAdapter(new ArrayList<>(), billing -> onItemClick(billing));

    @BindView(R.id.recyclerBilling)
    RecyclerView mRecyclerBilling;


    public BillingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_billing, container, false);
        ButterKnife.bind(this, view);
        mRecyclerBilling.setLayoutManager(new LinearLayoutManager(getActivity()));
        generateBilling();
        mBillingAdapter.updateBillingList(billingList);
        mRecyclerBilling.setAdapter(mBillingAdapter);
        return view;
    }

    private void generateBilling() {
        billingList.add(
                new Billing(0, "eStatement Kartu Kredit BCA Mastercard",
                        "Tagihan BCA Credit Card anda Rp 5.000.000,00 akan jatuh tempo 28 November 2019",
                        "12 Januari 2020, 18.00 WIB", Notification.UNREAD,
                        "electronic-statement-credit-card-15112019.pdf", "admin"));
        billingList.add(
                new Billing(1, "eStatement Kartu Kredit Mandiri Visa",
                        "Berikut adalah tagihan e-Statement Kartu Kredit Anda periode bulan Februari 2020",
                        "11 Januari 2020, 08.00 WIB",
                        Notification.UNREAD, "electronic-statement-credit-card-15112019.pdf",
                        "admin"));
        billingList.add(
                new Billing(2, "eStatement Kartu Kredit ANZ",
                        "Berikut adalah tagihan e-Statement Kartu Kredit Anda periode bulan Januari 2020",
                        "11 Januari 2020, 08.00 WIB",
                        Notification.UNREAD, "electronic-statement-credit-card-15112019.pdf",
                        "admin"));
        billingList.add(
                new Billing(3, "eStatement Kartu Kredit BCA Visa",
                        "Berikut adalah tagihan e-Statement Kartu Kredit Anda periode bulan Januari 2020",
                        "11 Januari 2020, 08.00 WIB",
                        Notification.UNREAD, "electronic-statement-credit-card-15112019.pdf",
                        "admin"));
    }


    @Override
    public void onBillingItemClick(Billing billing) {

    }

    private void onItemClick(Billing billing) {

        Intent intent = new Intent(getActivity(), DetailNotificationBillingActivity.class);
        intent.putExtra("billingTitle", billing.getTitle());
        intent.putExtra("billingDate", billing.getDate());
        intent.putExtra("billingBody", billing.getBody());
        intent.putExtra("billingAttachmentName", billing.getAttachmentName());
        intent.putExtra("billingAttachmentPassword", billing.getAttachmentPassword());
        startActivity(intent);


    }
}
