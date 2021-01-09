package com.m90.zero.profile.model;

import com.google.gson.annotations.SerializedName;

public class DownlineResponse {

    @SerializedName("id")
    public int id;

    @SerializedName("role_id")
    public String role_id;

    @SerializedName("name")
    public String name;

    @SerializedName("mobile_number")
    public String mobile_number;

    @SerializedName("email")
    public String email;

    @SerializedName("avatar")
    public String avatar;

    @SerializedName("email_verified_at")
    public String email_verified_at;

    @SerializedName("password")
    public String password;

    @SerializedName("status")
    public String status;

    @SerializedName("remember_token")
    public String remember_token;

    @SerializedName("settings")
    public String settings;

     @SerializedName("created_at")
    public String created_at;

      @SerializedName("updated_at")
    public String updated_at;

      @SerializedName("mobile_verified_at")
    public String mobile_verified_at;


    @Override
    public String toString() {
        return "DownlineResponse{" +
                "id=" + id +
                ", role_id='" + role_id + '\'' +
                ", name='" + name + '\'' +
                ", mobile_number='" + mobile_number + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email_verified_at='" + email_verified_at + '\'' +
                ", password='" + password + '\'' +
                ", status='" + status + '\'' +
                ", remember_token='" + remember_token + '\'' +
                ", settings='" + settings + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", mobile_verified_at='" + mobile_verified_at + '\'' +
                '}';
    }
}
