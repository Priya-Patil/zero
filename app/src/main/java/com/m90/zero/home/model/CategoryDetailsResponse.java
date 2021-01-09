package com.m90.zero.home.model;

import com.google.gson.annotations.SerializedName;
import com.m90.zero.login.model.UserDetailsResponce;

import java.io.Serializable;

public class CategoryDetailsResponse implements Serializable {

    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;

    @Override
    public String toString() {
        return "CategoryDetailsResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
