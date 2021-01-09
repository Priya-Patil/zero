package com.m90.zero.login.model;


import com.google.gson.annotations.SerializedName;

public  class UserDetailsResponce {

    @SerializedName("id")
    public int id;
    @SerializedName("role_id")
    public int role_id;
    @SerializedName("name")
    public String name;
    @SerializedName("mobile_number")
    public String mobile_number;
    @SerializedName("email")
    public String email;

    @SerializedName("avatar")
    public String  avatar;
    @SerializedName("email_verified_at")
    public String email_verified_at;
    @SerializedName("status")
    public String status;
    @SerializedName("settings")
    public String settings;
    @SerializedName("created_at")
    public String created_at;

    @SerializedName("updated_at")
    public String updated_at;
    @SerializedName("mobile_verified_at")
    public String mobile_verified_at;
    @SerializedName("amount")
    public String amount;
    @SerializedName("txnid")
    public String txnid;

    @Override
    public String toString() {
        return "UserDetailsResponce{" +
                "id=" + id +
                ", role_id=" + role_id +
                ", name='" + name + '\'' +
                ", mobile_number='" + mobile_number + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email_verified_at='" + email_verified_at + '\'' +
                ", status='" + status + '\'' +
                ", settings='" + settings + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", mobile_verified_at='" + mobile_verified_at + '\'' +
                ", amount=" + amount +
                '}';
    }
}