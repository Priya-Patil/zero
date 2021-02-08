package com.m90.zero.home.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.m90.zero.login.model.UserDetailsResponce;

import java.io.Serializable;

public class CategoryDetailsResponse implements Parcelable {

    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;

    protected CategoryDetailsResponse(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    public static final Creator<CategoryDetailsResponse> CREATOR = new Creator<CategoryDetailsResponse>() {
        @Override
        public CategoryDetailsResponse createFromParcel(Parcel in) {
            return new CategoryDetailsResponse(in);
        }

        @Override
        public CategoryDetailsResponse[] newArray(int size) {
            return new CategoryDetailsResponse[size];
        }
    };

    @Override
    public String toString() {
        return "CategoryDetailsResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }
}
