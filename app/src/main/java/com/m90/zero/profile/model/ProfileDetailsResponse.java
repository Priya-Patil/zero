package com.m90.zero.profile.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProfileDetailsResponse {

    @SerializedName("profile")
    public ArrayList<ProfileAllDetailsResponse> profile;

    @SerializedName("downline")
    public int downline;

    @Override
    public String toString() {
        return "ProfileDetailsResponse{" +
                "profile=" + profile +
                ", downline='" + downline + '\'' +
                '}';
    }
}
