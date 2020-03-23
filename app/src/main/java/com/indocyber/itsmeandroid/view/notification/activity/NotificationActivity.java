package com.indocyber.itsmeandroid.view.notification.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.Notification;
import com.indocyber.itsmeandroid.view.notification.adapter.NotificationAdapter;
import com.indocyber.itsmeandroid.view.notificationdetail.NotificationDetail;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Muhammad Faisal
 * @version 1.0
 */
public class NotificationActivity extends AppCompatActivity {

    private final List<Notification> notificationList = new ArrayList<>();
    NotificationAdapter notificationAdapter =new NotificationAdapter(new ArrayList<>(), notification -> onItemClick(notification));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        generateNotification();
        createToolbar();
        RecyclerView notificationListContainer = findViewById(R.id.recNotificationList);
        notificationListContainer.setLayoutManager(
                new LinearLayoutManager(this.getApplicationContext()));
        notificationAdapter.updateNotificationList(notificationList);
        notificationListContainer.setAdapter(notificationAdapter);
    }

    private  void createToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Notification");
            getSupportActionBar().setElevation(0f);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void generateNotification() {
        notificationList.add(
                new Notification(0, "Informasi Limit", "Sisa limit Credit Card Master Anda : "
                + "9894 xxxx xxxx 9800 : "
                + "Rp 2.000.000,00", "11 November 2019, 08.00", Notification.UNREAD));
        notificationList.add(
                new Notification(1, "Informasi Jatuh Tempo", "Tagihan BCA Credit Card anda "
                        + "Rp 2.000.000,00 "
                        + "akan jatuh tempo pada 28 November 2019", "09 November 2019, 12.00",
                        Notification.UNREAD));
        notificationList.add(
                new Notification(2, "Credit Card Blocked", "Credit Card Mastercard anda "
                        + "2345 xxxx xxxx 9450 berhasil di blok oleh "
                        + "Ariz Ariyanto pada 08 November 2019", "08 November 2019, 12.00",
                        Notification.UNREAD));
        notificationList.add(
                new Notification(3, "Informasi", "Credit Card Visa anda"
                        + "berhasil ditambahkan di It's Me App" , "11 November 2019, 08.00",
                        Notification.UNREAD));
    }

    private void onItemClick(Notification notification) {
        Intent intent = new Intent(this, NotificationDetail.class);
        intent.putExtra("notificationTitle", notification.getTitle());
        intent.putExtra("notificationDate", notification.getDate());
        intent.putExtra("notificationBody", notification.getBody());
        startActivity(intent);
    }

}
