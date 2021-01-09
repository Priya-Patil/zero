package com.m90.zero.profile.model;

import com.google.gson.annotations.SerializedName;

public class WalletAmountResponse {

    @SerializedName("balance_amount")
    public String balance_amount;

    @SerializedName("user_id")
    public String user_id;


    @Override
    public String toString() {
        return "WalletAmountResponse{" +
                "balance_amount='" + balance_amount + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }
}
