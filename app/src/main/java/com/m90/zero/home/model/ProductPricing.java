package com.m90.zero.home.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductPricing implements Serializable {


    @SerializedName("vendor_product_id")
    public String vendor_product_id;

     @SerializedName("unit_price")
    public String unit_price;

     @SerializedName("discount_price")
    public String discount_price;

    @Override
    public String toString() {
        return "ProductPricing{" +
                "vendor_product_id='" + vendor_product_id + '\'' +
                ", unit_price='" + unit_price + '\'' +
                ", discount_price='" + discount_price + '\'' +
                '}';
    }


}
