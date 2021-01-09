package com.m90.zero.profile.model;

import com.google.gson.annotations.SerializedName;

public class AvatarResponse {

    @SerializedName("id")
    public int id;

    @SerializedName("avatar")
    public String avatar;

    @Override
    public String toString() {
        return "AvatarResponse{" +
                "id=" + id +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
