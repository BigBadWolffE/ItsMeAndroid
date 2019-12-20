package com.indocyber.itsmeandroid.view.inbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.view.billing.activity.BillingActivity;
import com.indocyber.itsmeandroid.view.notification.activity.NotificationActivity;

/**
 *
 * @author Muhammad Faisal
 * @version 1.0
 */
public class InboxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        createToolbar();
        LinearLayout notificationButton = findViewById(R.id.btnNotification);
        notificationButton.setOnClickListener(view -> gotoNotification());
        LinearLayout billingButton = findViewById(R.id.btnBilling);
        billingButton.setOnClickListener(view -> gotoBilling());
    }

    private  void createToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Inbox");
            getSupportActionBar().setElevation(0f);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void gotoNotification(){
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }

    private void gotoBilling(){
        Intent intent = new Intent(this, BillingActivity.class);
        startActivity(intent);
    }
}
