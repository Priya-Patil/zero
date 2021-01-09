package com.m90.zero.profile.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProfileUpdateDetailsResponse {


    @SerializedName("first_name")
    public String first_name;

    @SerializedName("middle_name")
    public String middle_name;

    @SerializedName("last_name")
    public String last_name;

    @SerializedName("state_id")
    public String state_id;

    @SerializedName("city_id")
    public String city_id;

    @SerializedName("pincode")
    public String pincode;

    @SerializedName("address")
    public String address;

    @Override
    public String toString() {
        return "ProfileUpdateDetailsResponse{" +
                "first_name='" + first_name + '\'' +
                ", middle_name='" + middle_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", state_id='" + state_id + '\'' +
                ", city_id='" + city_id + '\'' +
                ", pincode='" + pincode + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
