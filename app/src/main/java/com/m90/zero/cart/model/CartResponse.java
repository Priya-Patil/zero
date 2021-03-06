package com.m90.zero.cart.model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class CartResponse {

    @SerializedName("content_type")
    public String content_type;

    @SerializedName("message")
    public String message;

    @SerializedName("status")
    public int status;

    @SerializedName("data")
    public DataResponse data;
    //public Map<String, DataResponse> data;

    @Override
    public String toString() {
        return "CartResponse{" +
                "content_type='" + content_type + '\'' +
                ", message='" + message + '\'' +
                ", status=" + status +
                ", data=" + data +
                '}';
    }
}
