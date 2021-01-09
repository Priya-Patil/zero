package com.m90.zero.cart.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ItemsResponse {

    @SerializedName("id")
    public int id;

    @SerializedName("cart_id")
    public String cart_id;

    @SerializedName("content")
    public ArrayList<ContentResponse> content;

    @Override
    public String toString() {
        return "ItemsResponse{" +
                "id=" + id +
                ", cart_id='" + cart_id + '\'' +
                ", content=" + content +
                '}';
    }


}
