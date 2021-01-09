package com.m90.zero.profile.model;

import com.google.gson.annotations.SerializedName;

public class SponsorResponse {

    @SerializedName("user_id")
    public String user_id;

    @SerializedName("sponsor_code")
    public String sponsor_code;

    @Override
    public String toString() {
        return "SponsorResponse{" +
                "user_id='" + user_id + '\'' +
                ", sponsor_code='" + sponsor_code + '\'' +
                '}';
    }

   /* public int compareTo(SponsorResponse sponsor) {
        if (sponsor.sponsor_code[]>)
    }*/
}
