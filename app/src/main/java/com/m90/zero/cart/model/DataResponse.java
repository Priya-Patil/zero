package com.m90.zero.cart.model;

import com.google.gson.annotations.SerializedName;
import com.m90.zero.productdetail.model.CartItem;

import java.util.Map;

public class DataResponse {


    @SerializedName("items")
    public Map<String, CartItem> items;

    @SerializedName("total")
    public int total;

    @SerializedName("count")
    public int count;

    @Override
    public String toString() {
        return "DataResponse{" +
                "items=" + items +
                ", total=" + total +
                ", count=" + count +
                '}';
    }
}
