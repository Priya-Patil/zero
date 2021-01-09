package com.m90.zero.home.allcategory.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CategoryAllDetailsResponse implements Serializable {

    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

     @SerializedName("slug")
    public String slug;

    @Override
    public String toString() {
        return "CategoryAllDetailsResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                '}';
    }
}
