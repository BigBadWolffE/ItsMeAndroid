package com.indocyber.itsmeandroid.view.contactcc.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.EditTag;

import java.util.ArrayList;
import java.util.List;


public class EditTagsAdapter extends RecyclerView.Adapter<EditTagsAdapter.EditTagsViewHolder> {
    private List<EditTag> listTag = new ArrayList<>();
    private final Activity activity;

    public EditTagsAdapter(Activity activity) {
        this.activity = activity;
    }
    public void setListTags (List<EditTag> listTag){
        this.listTag = listTag;
    }
    /*public void setListTags(List<EditTag> listCard) {
        final EdittTagsCallback diffCallback = new EdittTagsCallback(this.listTag, listCard);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.listTag.clear();
        this.listTag.addAll(listCard);
        diffResult.dispatchUpdatesTo(this);
    }*/

    @NonNull
    @Override
    public EditTagsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_tag, parent, false);
        return new EditTagsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EditTagsViewHolder holder, int position) {
        EditTag model = listTag.get(position);
        holder.txtTag.setText(model.getTag());





    }

    @Override
    public int getItemCount() {
        return listTag.size();
    }

    class EditTagsViewHolder extends RecyclerView.ViewHolder {
        final TextView txtTag;
        final Button btnClose;



        EditTagsViewHolder(View itemView) {
            super(itemView);
            txtTag = itemView.findViewById(R.id.txtTag);
            btnClose = itemView.findViewById(R.id.btnClose);


        }
    }


}
