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
import com.indocyber.itsmeandroid.utilities.GlobalVariabel;

import java.util.ArrayList;
import java.util.List;


public class EditTagsAdapter extends RecyclerView.Adapter<EditTagsAdapter.EditTagsViewHolder> {
    private List<String> listTag;
    private final Activity activity;
    private blockItemClickListener listener;

    public List<String> getListTag() {
        return listTag;
    }

    public EditTagsAdapter(Activity activity, List<String> tagList) {
        this.activity = activity;
        listTag = tagList;
    }

    public void tagAction(String tag, int action){
        if (action == GlobalVariabel.TAG_ADD) {
            if(listTag.contains(tag)) {
                Toast.makeText(activity, "Tag already exist!", Toast.LENGTH_SHORT).show();
                return;
            }
            listTag.add(0, tag);
        } else if (action == GlobalVariabel.TAG_REMOVE) {
            listTag.remove(tag);
        }
        notifyDataSetChanged();
    }

    /*public void setListTags (List<EditTag> listTag){
        Toast.makeText(activity, listTag.size()+"", Toast.LENGTH_SHORT).show();
        this.listTag.clear();
        this.listTag.addAll(listTag) ;
    }*/

    public void setOnBlockListener(blockItemClickListener mblockItemClickListener) {
        this.listener = mblockItemClickListener;

    }

//    public void setListTags(ArrayList<EditTag> listCard) {
//        final EdittTagsCallback diffCallback = new EdittTagsCallback(this.listTag, listCard);
//        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
//        this.listTag.clear();
//        this.listTag.addAll(listCard);
//        diffResult.dispatchUpdatesTo(this);
//    }

    @NonNull
    @Override
    public EditTagsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_tag, parent, false);
        return new EditTagsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EditTagsViewHolder holder, int position) {
        holder.bind(listTag.get(position), view -> listener.itemClick(position));
    }

    @Override
    public int getItemCount() {
        if(listTag == null) {
            return 0;
        }
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

        public void bind(String tag, View.OnClickListener clickEvent) {
            txtTag.setText(tag);
            btnClose.setOnClickListener(clickEvent);
        }
    }

    public interface blockItemClickListener {
        void itemClick(int position);
    }

}
