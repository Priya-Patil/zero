package com.m90.zero.cart.model;

import com.google.gson.annotations.SerializedName;

public class AddCartDetailResponse {

    @SerializedName("id")
    public int id;

      @SerializedName("customer_id")
    public String customer_id;

      @SerializedName("instance")
    public String instance;

      @SerializedName("total_qty")
    public String total_qty;

      @SerializedName("total_price")
    public String total_price;

      @SerializedName("created_at")
    public String created_at;

      @SerializedName("updated_at")
    public String updated_at;

    @Override
    public String toString() {
        return "AddCartDetailResponse{" +
                "id=" + id +
                ", customer_id='" + customer_id + '\'' +
                ", instance='" + instance + '\'' +
                ", total_qty='" + total_qty + '\'' +
                ", total_price='" + total_price + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
