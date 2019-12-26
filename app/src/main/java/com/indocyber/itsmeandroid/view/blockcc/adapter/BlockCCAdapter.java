package com.indocyber.itsmeandroid.view.blockcc.adapter;

import android.app.Activity;
import android.app.Dialog;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.view.blockconfirmationpin.BlockConfirmationPinActivity;

import java.util.ArrayList;
import java.util.List;

import static com.indocyber.itsmeandroid.view.blockconfirmationpin.BlockConfirmationPinActivity.INTENT_BLOCK_CONFIRMATION;


public class BlockCCAdapter extends RecyclerView.Adapter<BlockCCAdapter.BlockViewHolder> {
    private final List<ImageCardModel> listCard = new ArrayList<>();
    private final Activity activity;

    public BlockCCAdapter(Activity activity) {
        this.activity = activity;
    }

//    public void setListNotes(List<ImageCardModel> listCard) {
//        final BlockCCCallback diffCallback = new BlockCCCallback(this.listCard, listCard);
//        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
//        this.listCard.clear();
//        this.listCard.addAll(listCard);
//        diffResult.dispatchUpdatesTo(this);
//    }

    public void refreshAdapter(List<ImageCardModel> newList) {
        listCard.clear();
        listCard.addAll(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BlockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_block, parent, false);
        return new BlockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlockViewHolder holder, int position) {
        ImageCardModel model = listCard.get(position);
        holder.txtNameCard.setText(model.getNameCard());
        holder.txtNumberCard.setText(model.getNumberCard());
        holder.txtExpireCard.setText(model.getExpireCard());

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.bg_gradient_soft)
                .error(R.drawable.bg_gradient_soft)
                .priority(Priority.HIGH);

        Glide.with(activity)
                .load(model.getImage())
                .apply(options)
                .into(holder.imageBlock);

        holder.fabBLock.setOnClickListener(v -> {
            showCustomDialog(model);
        });

    }

    @Override
    public int getItemCount() {
        return listCard.size();
    }

    class BlockViewHolder extends RecyclerView.ViewHolder {
        final TextView txtNumberCard;
        final TextView txtNameCard;
        final TextView txtExpireCard;
        final ImageView imageBlock;
        final FloatingActionButton fabBLock;


        BlockViewHolder(View itemView) {
            super(itemView);
            txtNumberCard = itemView.findViewById(R.id.txtNumberCard);
            txtNameCard = itemView.findViewById(R.id.txtNameCard);
            txtExpireCard = itemView.findViewById(R.id.txtExpireCard);
            imageBlock = itemView.findViewById(R.id.imageBlock);
            fabBLock = itemView.findViewById(R.id.fabBLock);

        }
    }

    private void showCustomDialog(ImageCardModel model) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_warning_block_card);
        dialog.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        final Button btnBlockNow = (Button) dialog.findViewById(R.id.btnBlockNow);
        final EditText edtxMessage = (EditText) dialog.findViewById(R.id.edtxMessage);

        final TextView txtNumberCard = (TextView) dialog.findViewById(R.id.txtNumberCard);
        txtNumberCard.setText(model.getNumberCard());


        btnBlockNow.setOnClickListener(v -> {
            if (edtxMessage.getText().toString().isEmpty()) {
                edtxMessage.setError("field empty");
            } else {
                dialog.dismiss();
                Intent intent = new Intent(activity, BlockConfirmationPinActivity.class);
                intent.putExtra(INTENT_BLOCK_CONFIRMATION, model);
                activity.startActivity(intent);
            }
        });

        ((ImageButton) dialog.findViewById(R.id.imgButtonClose)).setOnClickListener(v -> dialog.dismiss());


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}
