package com.m90.zero.home.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BrandResponse {
    @SerializedName("data")
    public ArrayList<BrandsDetailsResponse> data;

    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public String message;
    @SerializedName("content_type")
    public String content_type;

    @Override
    public String toString() {
        return "BrandResponse{" +
                "data=" + data +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", content_type='" + content_type + '\'' +
                '}';
    }

}
