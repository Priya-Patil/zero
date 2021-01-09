package com.m90.zero.profile.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReferalResponse {

    @SerializedName("user_id")
    public String user_id;

 @SerializedName("sponsor_code")
    public String sponsor_code;

 @SerializedName("sponsor_by")
 public SponsorbyResponse sponsor_by;

    @Override
    public String toString() {
        return "ReferalResponse{" +
                "user_id='" + user_id + '\'' +
                ", sponsor_code='" + sponsor_code + '\'' +
                ", sponsor_by=" + sponsor_by +
                '}';
    }
}
