package com.m90.zero.home.model;

import android.app.Service;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductData  implements Serializable {

    @SerializedName("id")
    public String id;

    @SerializedName("product_name")
    public String product_name;

 @SerializedName("short_details")
    public String short_details;

 @SerializedName("category_id")
    public String category_id;

 @SerializedName("brand_id")
    public String brand_id;

    @Override
    public String toString() {
        return "ProductData{" +
                "id='" + id + '\'' +
                ", product_name='" + product_name + '\'' +
                ", short_details='" + short_details + '\'' +
                ", category_id='" + category_id + '\'' +
                ", brand_id='" + brand_id + '\'' +
                '}';
    }
}
