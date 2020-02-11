package com.indocyber.itsmeandroid.view.notificationdetail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.indocyber.itsmeandroid.R;

public class NotificationDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);
        Bundle extras = getIntent().getExtras();

        String title = extras != null ? extras.getString("notificationTitle") : "";
        createToolbar(title);
        TextView billingTitle = findViewById(R.id.txtNotificationTitle);
        billingTitle.setText(title);

        String body = extras != null ? extras.getString("notificationBody") : "";
        TextView billingBody = findViewById(R.id.txtNotificationMessage);
        billingBody.setText(body);

        String date = extras != null ? extras.getString("notificationDate") : "";
        TextView billingDate = findViewById(R.id.txtNotificationDate);
        billingDate.setText(date);
    }

    private  void createToolbar(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setElevation(0f);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
