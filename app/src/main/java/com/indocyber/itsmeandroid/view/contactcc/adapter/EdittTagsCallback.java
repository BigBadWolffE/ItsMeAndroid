package com.indocyber.itsmeandroid.view.contactcc.adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.indocyber.itsmeandroid.model.EditTag;
import com.indocyber.itsmeandroid.model.ImageCardModel;

import java.util.List;

public class EdittTagsCallback extends DiffUtil.Callback {
    private final List<EditTag> mOldNoteList;
    private final List<EditTag> mNewNoteList;

    public EdittTagsCallback(List<EditTag> oldNoteList, List<EditTag> newNoteList) {
        this.mOldNoteList = oldNoteList;
        this.mNewNoteList = newNoteList;
    }

    @Override
    public int getOldListSize() {
        return mOldNoteList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewNoteList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldNoteList.get(oldItemPosition).getId() == mNewNoteList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final EditTag oldEmployee = mOldNoteList.get(oldItemPosition);
        final EditTag newEmployee = mNewNoteList.get(newItemPosition);

        return oldEmployee.getTag().equals(newEmployee.getTag());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
