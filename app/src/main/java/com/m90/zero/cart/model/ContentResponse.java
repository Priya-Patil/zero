package com.m90.zero.cart.model;

import com.google.gson.annotations.SerializedName;

public class ContentResponse {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

     @SerializedName("price")
    public int price;

     @SerializedName("quantity")
    public int quantity;


    @Override
    public String toString() {
        return "ContentResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
