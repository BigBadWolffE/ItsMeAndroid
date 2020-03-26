package com.indocyber.itsmeandroid.viewremastered.notification.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.Notification;
import com.indocyber.itsmeandroid.viewremastered.notification.Adapter.NotificationAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {

    @BindView(R.id.recyclerNotification)
    RecyclerView mRecyclerNotification;

    private final List<Notification> notificationList = new ArrayList<>();
    NotificationAdapter notificationAdapter =new NotificationAdapter(new ArrayList<>(), notification -> onItemClick(notification));


    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this, view);
        generateNotification();
        mRecyclerNotification.setLayoutManager(
                new LinearLayoutManager(getActivity()));
        notificationAdapter.updateNotificationList(notificationList);
        mRecyclerNotification.setAdapter(notificationAdapter);
        return view;
    }

    private void generateNotification() {
        notificationList.add(
                new Notification(0, "Jatuh Tempo Pembayaran  Kartu Kredit",
                        "Tagihan BCA Credit Card anda Rp 5.000.000,00 akan jatuh tempo 28 November 2019",
                        "12 Januari 2020, 18.00 WIB",
                        Notification.UNREAD));
        notificationList.add(
                new Notification(1, "Pembelian Pulsa berhasil",
                        "Pembelian Pulsa Rp 100.000,00 ke no 081728813566 berhasil",
                        "11 Januari 2020, 08.00 WIB",
                        Notification.UNREAD));
        notificationList.add(
                new Notification(2, "Pengajuan Limit Kartu Kredit",
                        "Pengajuan limit kartu kredit BCA anda sebesar Rp 20.000.000,00 berhasil",
                        "10 Januari 2020, 12.00 WIB",
                        Notification.UNREAD));
        notificationList.add(
                new Notification(3, "Pembelian Pulsa berhasil",
                        "Pembelian Pulsa Rp 100.000,00 ke no 08128555331 berhasil" ,
                        "10 Januari 2020, 08.00 WIB",
                        Notification.UNREAD));
        notificationList.add(
                new Notification(3, "Pembayaran Kartu Kredit BCA",
                        "Tagihan BCA Credit Card anda Rp 5.000.000,00 akan jatuh tempo 28 November 2019" ,
                        "9 Januari 2020, 19.00 WIB",
                        Notification.UNREAD));
    }

    private void onItemClick(Notification notification) {
//        Intent intent = new Intent(this, NotificationDetail.class);
//        intent.putExtra("notificationTitle", notification.getTitle());
//        intent.putExtra("notificationDate", notification.getDate());
//        intent.putExtra("notificationBody", notification.getBody());
//        startActivity(intent);
    }
}
