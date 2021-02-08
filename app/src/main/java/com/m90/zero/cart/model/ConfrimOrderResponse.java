package com.m90.zero.cart.model;

import com.google.gson.annotations.SerializedName;

public class ConfrimOrderResponse {

    @SerializedName("content_type")
    public String content_type;

    @SerializedName("message")
    public Object message;

    @SerializedName("status")
    public int status;

    @SerializedName("data")
    public ConfrimOrderDetailResponse data;

    @Override
    public String toString() {
        return "ConfrimOrderResponse{" +
                "content_type='" + content_type + '\'' +
                ", message=" + message +
                ", status=" + status +
                ", data=" + data +
                '}';
    }
}
