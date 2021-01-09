package com.m90.zero.home.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductDetailsResponse implements Serializable {

    @SerializedName("id")
    public int id;

    @SerializedName("category_id")
    public int category_id;

  @SerializedName("category_name")
    public String category_name;


    @SerializedName("name")
    public String name;

  @SerializedName("short_details")
    public String short_details;

 @SerializedName("pictures")
    public String pictures;

 @SerializedName("rating")
    public String rating;

@SerializedName("unit_price")
    public String unit_price;

@SerializedName("discount_price")
    public String discount_price;

@SerializedName("tag")
    public String tag;


    @Override
    public String toString() {
        return "ProductDetailsResponse{" +
                "id=" + id +
                ", category_id=" + category_id +
                ", category_name='" + category_name + '\'' +
                ", name='" + name + '\'' +
                ", short_details='" + short_details + '\'' +
                ", pictures='" + pictures + '\'' +
                ", rating='" + rating + '\'' +
                ", unit_price='" + unit_price + '\'' +
                ", discount_price='" + discount_price + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }


}
