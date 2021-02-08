package com.m90.zero.home.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductGroupsDetailsResponse implements Parcelable {

    @SerializedName("id")
    public int group_id;

    @SerializedName("title")
    public String title;

    @SerializedName("vendor")
    public ArrayList<ProductDetailsResponse> vendor;

    protected ProductGroupsDetailsResponse(Parcel in) {
        group_id = in.readInt();
        title = in.readString();
    }

    public static final Creator<ProductGroupsDetailsResponse> CREATOR = new Creator<ProductGroupsDetailsResponse>() {
        @Override
        public ProductGroupsDetailsResponse createFromParcel(Parcel in) {
            return new ProductGroupsDetailsResponse(in);
        }

        @Override
        public ProductGroupsDetailsResponse[] newArray(int size) {
            return new ProductGroupsDetailsResponse[size];
        }
    };

    @Override
    public String toString() {
        return "ProductGroupsDetailsResponse{" +
                "group_id=" + group_id +
                ", title='" + title + '\'' +
                ", vendor=" + vendor +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(group_id);
        dest.writeString(title);
    }
}
