package com.m90.zero.profile.model;

import com.google.gson.annotations.SerializedName;
import com.m90.zero.login.model.UserDetailsResponce;

import java.util.ArrayList;

public class ProfileAllDetailsResponse {

    @SerializedName("id")
    public int id;

    @SerializedName("user_id")
    public String user_id;

    @SerializedName("first_name")
    public String first_name;

    @SerializedName("middle_name")
    public String middle_name;

    @SerializedName("last_name")
    public String last_name;

    @SerializedName("state_id")
    public StateDetailsResponse state_id;

    @SerializedName("city_id")
    public StateDetailsResponse city_id;

    @SerializedName("pincode")
    public String pincode;

    @SerializedName("address")
    public String address;

    @SerializedName("created_at")
    public String created_at;

    @SerializedName("updated_at")
    public String updated_at;

    @SerializedName("user")
    public UserDetailsResponce user;

    @SerializedName("avatar")
    public AvatarResponse avatar;

    @SerializedName("referal")
    public ReferalResponse referal;

    @SerializedName("sponsor_amount")
    public ArrayList<String> sponsor_amount;

    @SerializedName("wallet_amount")
    public ArrayList<WalletAmountResponse> wallet_amount;

    @Override
    public String toString() {
        return "ProfileAllDetailsResponse{" +
                "id=" + id +
                ", user_id='" + user_id + '\'' +
                ", first_name='" + first_name + '\'' +
                ", middle_name='" + middle_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", state_id=" + state_id +
                ", city_id=" + city_id +
                ", pincode='" + pincode + '\'' +
                ", address='" + address + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", user=" + user +
                ", avatar=" + avatar +
                ", referal=" + referal +
                ", sponsor_amount=" + sponsor_amount +
                ", wallet_amount=" + wallet_amount +
                '}';
    }
}
