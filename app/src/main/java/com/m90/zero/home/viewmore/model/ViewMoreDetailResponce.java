package com.m90.zero.home.viewmore.model;

import com.google.gson.annotations.SerializedName;
import com.m90.zero.home.model.ProductDetailsResponse;

import java.util.ArrayList;

public class ViewMoreDetailResponce {

    @SerializedName("title")
    public String title;

    @SerializedName("products")
    public ArrayList<ProductDetailsResponse> products;

    @Override
    public String toString() {
        return "ViewMoreDetailResponce{" +
                "title='" + title + '\'' +
                ", products=" + products +
                '}';
    }
}
