package com.m90.zero.home.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductGroupsDetailsResponse implements Serializable {

    @SerializedName("group_id")
    public int group_id;

    @SerializedName("title")
    public String title;

    @SerializedName("products")
    public ArrayList<ProductDetailsResponse> products;

    @Override
    public String toString() {
        return "ProductGroupsDetailsResponse{" +
                "group_id=" + group_id +
                ", title='" + title + '\'' +
                ", products=" + products +
                '}';
    }
}
