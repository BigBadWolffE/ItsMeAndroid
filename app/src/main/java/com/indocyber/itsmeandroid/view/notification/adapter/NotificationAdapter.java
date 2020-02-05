package com.indocyber.itsmeandroid.view.notification.adapter;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.Notification;

import java.util.List;

/**
 *
 *
 *@author Muhammad Faisal
 *@version 1.0
 */
public class NotificationAdapter extends
        RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Notification> notificationList;
    private NotificationClickListener clickListener;
    public NotificationAdapter(List<Notification> notificationList,
                               NotificationAdapter.NotificationClickListener listener) {
        this.notificationList = notificationList;
        this.clickListener = listener;
    }

    public void updateNotificationList(List<Notification> newNotificationList) {
        notificationList.clear();
        this.notificationList = newNotificationList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.bind(notificationList.get(position));
        holder.itemView.setOnClickListener(view -> {
            clickListener.onItemClick(notificationList.get(position));
            onViewHolderClick(holder, position);
        });
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    private void onViewHolderClick(NotificationViewHolder holder, int position) {
        Notification notification = notificationList.get(position);
        if (notification.getStatus() != Notification.READ){
            notification.setStatus(Notification.READ);
            Typeface typeFace = ResourcesCompat
                    .getFont(holder.itemView.getContext(), R.font.interstate_light);
            holder.getBody().setTypeface(typeFace);
        }
        notifyDataSetChanged();
    }

    public interface NotificationClickListener {
        void onItemClick(Notification notification);
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder {

        private TextView mNotificationTitle;
        private TextView mNotificationBody;
        private TextView mNotificationDate;
        private LinearLayout mNotificationStatus;

        TextView getBody() {
            return mNotificationBody;
        }

        NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            mNotificationTitle = itemView.findViewById(R.id.txtNotificationTitle);
            mNotificationBody = itemView.findViewById(R.id.txtNotificationMessage);
            mNotificationDate = itemView.findViewById(R.id.txtNotificationDate);
            mNotificationStatus = itemView.findViewById(R.id.llMessageFlag);
        }

        void bind(Notification notification) {
            mNotificationTitle.setText(notification.getTitle());
            mNotificationBody.setText(notification.getBody());
            mNotificationDate.setText(notification.getDate());
            if(notification.getStatus() == Notification.READ) {
                mNotificationStatus.setBackground(
                        super.itemView.getContext().getDrawable(R.drawable.bgmessageflagread));
            } else if (notification.getStatus() == Notification.UNREAD) {
                mNotificationStatus.setBackground(
                        super.itemView.getContext().getDrawable(R.drawable.bgmessageflagunread));
            }
        }

    }
}
