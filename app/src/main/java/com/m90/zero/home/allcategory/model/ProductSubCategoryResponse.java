package com.m90.zero.home.allcategory.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductSubCategoryResponse {

    @SerializedName("data")
    public ArrayList<ProductSubCategoryDetailsResponse> data;
    @SerializedName("access_token")
    public String access_token;
    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public String message;
    @SerializedName("content_type")
    public String content_type;

    @Override
    public String toString() {
        return "ProductSubCategoryResponse{" +
                "data=" + data +
                ", access_token='" + access_token + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", content_type='" + content_type + '\'' +
                '}';
    }
}
