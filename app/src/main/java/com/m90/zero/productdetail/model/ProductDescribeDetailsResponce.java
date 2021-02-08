package com.m90.zero.productdetail.model;

import com.google.gson.annotations.SerializedName;
import com.m90.zero.home.model.ProductData;
import com.m90.zero.home.model.ProductImages;
import com.m90.zero.home.model.ProductPricing;

import java.util.ArrayList;

public class ProductDescribeDetailsResponce {

        @SerializedName("id")
        public int id;

        @SerializedName("product_id")
        public String product_id;

         @SerializedName("brand_id")
        public String brand_id;

        @SerializedName("product")
        public ProductDescribeData productData;

        @SerializedName("pricing")
        public ProductPricing productPricing;

        @SerializedName("stock")
        public ProductDescribeStock productStock;

        @SerializedName("images")
        public ProductImages productImages;

    @Override
    public String toString() {
        return "ProductDescribeDetailsResponce{" +
                "id=" + id +
                ", product_id='" + product_id + '\'' +
                ", brand_id='" + brand_id + '\'' +
                ", productData=" + productData +
                ", productPricing=" + productPricing +
                ", productStock=" + productStock +
                ", productImages=" + productImages +
                '}';
    }


}




