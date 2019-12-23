package com.indocyber.itsmeandroid.view.blockcc.adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.indocyber.itsmeandroid.model.ImageCardModel;

import java.util.List;

public class BlockCCCallback extends DiffUtil.Callback {
    private final List<ImageCardModel> mOldNoteList;
    private final List<ImageCardModel> mNewNoteList;

    public BlockCCCallback(List<ImageCardModel> oldNoteList, List<ImageCardModel> newNoteList) {
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
        final ImageCardModel oldEmployee = mOldNoteList.get(oldItemPosition);
        final ImageCardModel newEmployee = mNewNoteList.get(newItemPosition);

        return oldEmployee.getNameCard().equals(newEmployee.getNameCard());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
