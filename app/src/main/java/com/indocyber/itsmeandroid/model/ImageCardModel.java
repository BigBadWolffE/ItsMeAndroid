package com.indocyber.itsmeandroid.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ImageCardModel implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private int image;
    private String numberCard;
    private String nameCard;
    private String expireCard;
    private String cost;
    private String printDate;
    private String printDueDate;
    private boolean isBlockedCard;
    private String billingAddress;
    private String country;
    private String city;
    private String postalCode;
    private String lastBill;
    private String minPayment;
    private String availableCredit;
    private List<String> tagList;

    public ImageCardModel(int image, String numberCard, String nameCard, String expireCard, String cost, String printDate, String printDueDate, boolean isBlockedCard) {
        this.image = image;
        this.numberCard = numberCard;
        this.nameCard = nameCard;
        this.expireCard = expireCard;
        this.cost = cost;
        this.printDate = printDate;
        this.printDueDate = printDueDate;
        this.isBlockedCard = isBlockedCard;
    }

    @Ignore
    public ImageCardModel(int id, int image, String numberCard, String nameCard, String expireCard, String cost, String printDate, String printDueDate, boolean isBlockedCard) {
        this.id = id;
        this.image = image;
        this.numberCard = numberCard;
        this.nameCard = nameCard;
        this.expireCard = expireCard;
        this.cost = cost;
        this.printDate = printDate;
        this.printDueDate = printDueDate;
        this.isBlockedCard = isBlockedCard;
    }

    protected ImageCardModel(Parcel in) {
        id = in.readInt();
        image = in.readInt();
        numberCard = in.readString();
        nameCard = in.readString();
        expireCard = in.readString();
        cost = in.readString();
        printDate = in.readString();
        printDueDate = in.readString();
        isBlockedCard = in.readByte() != 0;
        lastBill = in.readString();
        minPayment = in.readString();
        availableCredit = in.readString();
        billingAddress = in.readString();
        country = in.readString();
        city = in.readString();
        postalCode = in.readString();
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
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImageCardModel> CREATOR = new Creator<ImageCardModel>() {
        @Override
        public ImageCardModel createFromParcel(Parcel in) {
            return new ImageCardModel(in);
        }

        @Override
        public ImageCardModel[] newArray(int size) {
            return new ImageCardModel[size];
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
}
