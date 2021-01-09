package com.m90.zero.home.allcategory.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductSubCategoryDetailsResponse implements Serializable {

    @SerializedName("id")
    public String id;

    @SerializedName("parent_id")
    public String parent_id;

     @SerializedName("name")
    public String name;

    @Override
    public String toString() {
        return "ProductSubCategoryDetailsResponse{" +
                "id='" + id + '\'' +
                ", parent_id='" + parent_id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
