package com.indocyber.itsmeandroid.view.billing.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.Billing;

import java.util.List;

/**
 *
 *
 *@author Muhammad Faisal
 *@version 1.0
 */
public class BillingAdapter extends RecyclerView.Adapter<BillingAdapter.BillingViewHolder> {

    private List<Billing> billingList;
    private BillingAdapter.BillingListener onItemClickListener;

    public BillingAdapter(List<Billing> billingList, BillingAdapter.BillingListener listener) {
        this.billingList = billingList;
        this.onItemClickListener = listener;
    }

    public void updateBillingList(List<Billing> newBillingList){
        billingList.clear();
        this.billingList = newBillingList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BillingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BillingViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_billing, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BillingViewHolder holder, int position) {
        holder.bind(billingList.get(position));
        holder.billingDate.setText(billingList.get(position).getDate());
        holder.billingTitle.setText(billingList.get(position).getTitle());
        holder.billingBody.setText(billingList.get(position).getBody());
        holder.billingAttachment.setText(billingList.get(position).getAttachmentName());
        holder.itemView.setOnClickListener(view ->
                onItemClickListener.onBillingItemClick(billingList.get(position)));
    }

    public interface BillingListener {
        void onBillingItemClick(Billing billing);
    }

    @Override
    public int getItemCount() {
        return billingList.size();
    }

    class BillingViewHolder extends RecyclerView.ViewHolder {

        private TextView billingTitle;
        private TextView billingBody;
        private TextView billingDate;
        private TextView billingAttachment;

        private BillingViewHolder(@NonNull View itemView) {
            super(itemView);
            billingTitle = itemView.findViewById(R.id.txtBillingTitle);
            billingBody = itemView.findViewById(R.id.txtBillingMessage);
            billingDate = itemView.findViewById(R.id.txtBillingDate);
            billingAttachment = itemView.findViewById(R.id.txtBillingAttachment);
        }

        private void bind(Billing billing){
            billingTitle.setText(billing.getTitle());
//            if (billing.getBody().length() > 70) {
//                String cutText = billing.getBody().substring(0, 67) + "...";
//                billing.setBody(cutText);
//            } else {
//                billingBody.setText(billing.getBody());
//            }
            billingDate.setText(billing.getDate());
            billingAttachment.setText(billing.getAttachmentName());
        }
    }
}
