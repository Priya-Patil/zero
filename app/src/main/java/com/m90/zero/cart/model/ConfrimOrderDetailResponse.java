package com.m90.zero.cart.model;

import com.google.gson.annotations.SerializedName;

public class ConfrimOrderDetailResponse {


    @SerializedName("order_id")
    public String order_id;

    @Override
    public String toString() {
        return "ConfrimOrderDetailResponse{" +
                "order_id='" + order_id + '\'' +
                '}';
    }
}
