package com.m90.zero.productdetail.model;

import com.google.gson.annotations.SerializedName;
import com.m90.zero.home.brands.model.BrandsDetailListResponce;

import java.util.ArrayList;

public class ProductDescribeResponse {


    @SerializedName("data")
    public ProductDescribeDetailsResponce data;

    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public String message;
    @SerializedName("content_type")
    public String content_type;

    @Override
    public String toString() {
        return "BrandsResponse{" +
                "data=" + data +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", content_type='" + content_type + '\'' +
                '}';
    }


}
