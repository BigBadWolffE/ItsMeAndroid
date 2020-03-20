package com.indocyber.itsmeandroid.view.billing.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.Billing;
import com.indocyber.itsmeandroid.model.Notification;
import com.indocyber.itsmeandroid.view.billing.adapter.BillingAdapter;
import com.indocyber.itsmeandroid.view.billingdetail.BillingDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class BillingActivity extends AppCompatActivity {

    private final List<Billing> billingList = new ArrayList<>();
    private final BillingAdapter adapter = new BillingAdapter(new ArrayList<>(), billing -> onItemClick(billing) );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);
        createToolbar();
        RecyclerView billingListView = findViewById(R.id.recBillingList);
        billingListView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        generateBilling();
        adapter.updateBillingList(billingList);
        billingListView.setAdapter(adapter);
    }

    private  void createToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Billing");
            getSupportActionBar().setElevation(0f);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void generateBilling() {
        billingList.add(
                new Billing(0, "e-Statement Credit Card BCA", "Berikut ini adalah tagihan "
                        + "e-statement kartu kredit anda periode bulan November 2019",
                        "11 November 2019, 08.00", Notification.UNREAD,
                        "sample.pdf", "admin"));
        billingList.add(
                new Billing(1, "e-Billing Credit Card Mandiri Visa", "Berikut ini adalah tagihan "
                        + "e-statement kartu kredit Mandiri anda periode bulan November 2019 "
                        + "akan jatuh tempo pada bulan November 2019", "09 November 2019, 12.00",
                        Notification.UNREAD, "sample.pdf",
                        "admin"));
        billingList.add(
                new Billing(2, "e-Billing Credit Card BRI Visa", "Berikut ini adalah tagihan "
                        + "e-statement kartu kredit BRI anda periode bulan November 2019 "
                        + "akan jatuh tempo pada bulan November 2019", "09 November 2019, 12.00",
                        Notification.UNREAD, "sample.pdf",
                        "admin"));
        billingList.add(
                new Billing(3, "e-Billing Credit Card BCA Visa", "Berikut ini adalah tagihan "
                        + "e-statement kartu kredit BCA anda periode bulan November 2019 "
                        + "akan jatuh tempo pada bulan November 2019", "09 November 2019, 12.00",
                        Notification.UNREAD, "sample.pdf",
                        "admin"));
    }

    //// TODO: 19/12/2019 Delete when API ready
    private void onItemClick(Billing billing) {
        Intent intent = new Intent(this, BillingDetailActivity.class);
        intent.putExtra("billingTitle", billing.getTitle());
        intent.putExtra("billingDate", billing.getDate());
        intent.putExtra("billingBody", billing.getBody());
        intent.putExtra("billingAttachmentName", billing.getAttachmentName());
        intent.putExtra("billingAttachmentPassword", billing.getAttachmentPassword());
        startActivity(intent);
    }
}
