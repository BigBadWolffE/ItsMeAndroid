package com.indocyber.itsmeandroid.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;


public class BlockAllCardModel implements Parcelable {


    private int id;
    private int image;
    private String numberCard = "";
    private String nameCard = "";
    private String expireCard = "";
    private String cost = "";
    private String printDate = "";
    private String printDueDate = "";
    private boolean isBlockedCard;
    private String billingAddress = "";
    private String country = "";
    private String city = "";
    private String postalCode = "";
    private String lastBill = "";
    private String minPayment = "";
    private String availableCredit = "";
    private List<String> tagList;
    private boolean isCheckCard = false;

    public BlockAllCardModel(int image, String numberCard, String nameCard, String expireCard, String cost, String printDate, String printDueDate, boolean isBlockedCard) {
        this.image = image;
        this.numberCard = numberCard;
        this.nameCard = nameCard;
        this.expireCard = expireCard;
        this.cost = cost;
        this.printDate = printDate;
        this.printDueDate = printDueDate;
        this.isBlockedCard = isBlockedCard;
    }

    public BlockAllCardModel(boolean isCheckCard) {
        this.isCheckCard = isCheckCard;
    }

    protected BlockAllCardModel(Parcel in) {
        id = in.readInt();
        image = in.readInt();
        numberCard = in.readString();
        nameCard = in.readString();
        expireCard = in.readString();
        cost = in.readString();
        printDate = in.readString();
        printDueDate = in.readString();
        isBlockedCard = in.readByte() != 0;
        billingAddress = in.readString();
        country = in.readString();
        city = in.readString();
        postalCode = in.readString();
        lastBill = in.readString();
        minPayment = in.readString();
        availableCredit = in.readString();
        tagList = in.createStringArrayList();
        isCheckCard = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(image);
        dest.writeString(numberCard);
        dest.writeString(nameCard);
        dest.writeString(expireCard);
        dest.writeString(cost);
        dest.writeString(printDate);
        dest.writeString(printDueDate);
        dest.writeByte((byte) (isBlockedCard ? 1 : 0));
        dest.writeString(billingAddress);
        dest.writeString(country);
        dest.writeString(city);
        dest.writeString(postalCode);
        dest.writeString(lastBill);
        dest.writeString(minPayment);
        dest.writeString(availableCredit);
        dest.writeStringList(tagList);
        dest.writeByte((byte) (isCheckCard ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BlockAllCardModel> CREATOR = new Creator<BlockAllCardModel>() {
        @Override
        public BlockAllCardModel createFromParcel(Parcel in) {
            return new BlockAllCardModel(in);
        }

        @Override
        public BlockAllCardModel[] newArray(int size) {
            return new BlockAllCardModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getNumberCard() {
        return numberCard;
    }

    public void setNumberCard(String numberCard) {
        this.numberCard = numberCard;
    }

    public String getNameCard() {
        return nameCard;
    }

    public void setNameCard(String nameCard) {
        this.nameCard = nameCard;
    }

    public String getExpireCard() {
        return expireCard;
    }

    public void setExpireCard(String expireCard) {
        this.expireCard = expireCard;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getPrintDate() {
        return printDate;
    }

    public void setPrintDate(String printDate) {
        this.printDate = printDate;
    }

    public String getPrintDueDate() {
        return printDueDate;
    }

    public void setPrintDueDate(String printDueDate) {
        this.printDueDate = printDueDate;
    }

    public boolean isBlockedCard() {
        return isBlockedCard;
    }

    public void setBlockedCard(boolean blockedCard) {
        isBlockedCard = blockedCard;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getLastBill() {
        return lastBill;
    }

    public void setLastBill(String lastBill) {
        this.lastBill = lastBill;
    }

    public String getMinPayment() {
        return minPayment;
    }

    public void setMinPayment(String minPayment) {
        this.minPayment = minPayment;
    }

    public String getAvailableCredit() {
        return availableCredit;
    }

    public void setAvailableCredit(String availableCredit) {
        this.availableCredit = availableCredit;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public boolean isCheckCard() {
        return isCheckCard;
    }

    public void setCheckCard(boolean checkCard) {
        isCheckCard = checkCard;
    }
}
