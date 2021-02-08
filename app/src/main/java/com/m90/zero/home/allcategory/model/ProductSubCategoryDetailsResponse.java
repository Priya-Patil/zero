package com.m90.zero.home.allcategory.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductSubCategoryDetailsResponse implements Parcelable {

    @SerializedName("id")
    public int id;

    @SerializedName("parent_id")
    public String parent_id;

     @SerializedName("name")
    public String name;

    protected ProductSubCategoryDetailsResponse(Parcel in) {
        id = in.readInt();
        parent_id = in.readString();
        name = in.readString();
    }

    public static final Creator<ProductSubCategoryDetailsResponse> CREATOR = new Creator<ProductSubCategoryDetailsResponse>() {
        @Override
        public ProductSubCategoryDetailsResponse createFromParcel(Parcel in) {
            return new ProductSubCategoryDetailsResponse(in);
        }

        @Override
        public ProductSubCategoryDetailsResponse[] newArray(int size) {
            return new ProductSubCategoryDetailsResponse[size];
        }
    };

    @Override
    public String toString() {
        return "ProductSubCategoryDetailsResponse{" +
                "id='" + id + '\'' +
                ", parent_id='" + parent_id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(parent_id);
        dest.writeString(name);
    }
}
