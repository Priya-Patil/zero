package com.m90.zero.cart.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AddCartResponse {

    @SerializedName("content_type")
    public String content_type;

    @SerializedName("message")
    public String message;

    @SerializedName("status")
    public int status;

    @SerializedName("data")
    public ArrayList<AddCartDetailResponse> data;

    @Override
    public String toString() {
        return "AddCartResponse{" +
                "content_type='" + content_type + '\'' +
                ", message='" + message + '\'' +
                ", status=" + status +
                ", data=" + data +
                '}';
    }
}
