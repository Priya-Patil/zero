package com.m90.zero.productdetail.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VendorDetailsResponce {

        @SerializedName("vendor_id")
        public String vendor_id;

        @SerializedName("SKU")
        public String SKU;

         @SerializedName("unit_type")
        public String unit_type;

          @SerializedName("quantity_per_unit")
        public String quantity_per_unit;

        @SerializedName("note")
        public String note;

        @SerializedName("pictures")
        public String pictures;

         @SerializedName("rating")
        public String rating;


    @Override
    public String toString() {
        return "VendorDetailsResponce{" +
                "vendor_id='" + vendor_id + '\'' +
                ", SKU='" + SKU + '\'' +
                ", unit_type='" + unit_type + '\'' +
                ", quantity_per_unit='" + quantity_per_unit + '\'' +
                ", note='" + note + '\'' +
                ", pictures='" + pictures + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }



}




