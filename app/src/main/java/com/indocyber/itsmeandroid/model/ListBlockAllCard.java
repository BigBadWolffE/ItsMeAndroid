package com.indocyber.itsmeandroid.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ListBlockAllCard implements Parcelable {
    private List<BlockAllCardModel> list =  null;

    public ListBlockAllCard() {
    }

    public ListBlockAllCard(List<BlockAllCardModel> list) {
        this.list = list;
    }

    private ListBlockAllCard(Parcel in) {
        list = in.createTypedArrayList(BlockAllCardModel.CREATOR);
    }

    public static final Creator<ListBlockAllCard> CREATOR = new Creator<ListBlockAllCard>() {
        @Override
        public ListBlockAllCard createFromParcel(Parcel in) {
            return new ListBlockAllCard(in);
        }

        @Override
        public ListBlockAllCard[] newArray(int size) {
            return new ListBlockAllCard[size];
        }
    };

    public List<BlockAllCardModel> getList() {
        return list;
    }

    public void setList(List<BlockAllCardModel> list) {
        this.list = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(list);
    }
}
