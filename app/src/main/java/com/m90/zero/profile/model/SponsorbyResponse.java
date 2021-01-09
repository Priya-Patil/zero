package com.m90.zero.profile.model;

import com.google.gson.annotations.SerializedName;

public class SponsorbyResponse {

    @SerializedName("id")
    public int id;

    @SerializedName("username")
    public String username;

    @Override
    public String toString() {
        return "SponsorbyResponse{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
