package com.m90.zero.home.viewmore.model;

import com.google.gson.annotations.SerializedName;
import com.m90.zero.home.model.ProductDetailsResponse;

import java.util.ArrayList;

public class ViewMoreDetailResponce {

    @SerializedName("id")
    public int group_id;

    @SerializedName("title")
    public String title;

    @SerializedName("vendor")
    public ArrayList<ProductDetailsResponse> vendor;

    @Override
    public String toString() {
        return "ViewMoreDetailResponce{" +
                "group_id=" + group_id +
                ", title='" + title + '\'' +
                ", vendor=" + vendor +
                '}';
    }
}
