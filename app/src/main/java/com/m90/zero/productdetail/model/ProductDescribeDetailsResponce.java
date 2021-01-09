package com.m90.zero.productdetail.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductDescribeDetailsResponce {

        @SerializedName("id")
        public int id;

        @SerializedName("category_id")
        public String category_id;

         @SerializedName("brand_id")
        public String brand_id;

          @SerializedName("product_name")
        public String product_name;

        @SerializedName("short_details")
        public String short_details;

        @SerializedName("description")
        public String description;

        @SerializedName("vendor_details")
        public ArrayList<VendorDetailsResponce> vendor_details;

    @Override
    public String toString() {
        return "ProductDescribeDetailsResponce{" +
                "id=" + id +
                ", category_id='" + category_id + '\'' +
                ", brand_id='" + brand_id + '\'' +
                ", product_name='" + product_name + '\'' +
                ", short_details='" + short_details + '\'' +
                ", description='" + description + '\'' +
                ", vendor_details=" + vendor_details +
                '}';
    }


}




