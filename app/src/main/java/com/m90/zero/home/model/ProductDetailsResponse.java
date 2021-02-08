package com.m90.zero.home.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductDetailsResponse implements Serializable {

    @SerializedName("product_group_id")
    public String product_group_id;

    @SerializedName("product_id")
    public String product_id;

    @SerializedName("vp_short")
    public ArrayList<ProductVpShortDetailsResponse> vp_short;

    @SerializedName("vendor_id")
    public ArrayList<VendorIdResponse> vendor_id;

    @SerializedName("pricing")
    public ProductPricing productPricing;

    @SerializedName("images")
    public ArrayList<ProductImages> productImages;

    @Override
    public String toString() {
        return "ProductDetailsResponse{" +
                "product_group_id='" + product_group_id + '\'' +
                ", product_id='" + product_id + '\'' +
                ", vp_short=" + vp_short +
                ", vendor_id=" + vendor_id +
                ", productPricing=" + productPricing +
                ", productImages=" + productImages +
                '}';
    }

}
