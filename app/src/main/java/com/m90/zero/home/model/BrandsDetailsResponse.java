package com.m90.zero.home.model;

import com.google.gson.annotations.SerializedName;

public class BrandsDetailsResponse {
    @SerializedName("id")
    public int id;
    @SerializedName("category_id")
    public String category_id;

   @SerializedName("name")
    public String name;

    @SerializedName("brand_image")
    public String brand_image;


    @Override
    public String toString() {
        return "BrandsDetailsResponse{" +
                "id=" + id +
                ", category_id='" + category_id + '\'' +
                ", name='" + name + '\'' +
                ", brand_image='" + brand_image + '\'' +
                '}';
    }
}
