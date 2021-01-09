package com.m90.zero.profile.model;

import com.google.gson.annotations.SerializedName;

public class WalletDetailsResponse {

    @SerializedName("id")
    public int id;

    @SerializedName("user_id")
    public String user_id;

    @SerializedName("remark")
    public String remark;

     @SerializedName("credit_amount")
    public String credit_amount;

  @SerializedName("debit_amount")
    public String debit_amount;

  @SerializedName("balance_amount")
    public String balance_amount;


 @SerializedName("created_at")
    public String created_at;

 @SerializedName("updated_at")
    public String updated_at;

 @SerializedName("sponsor")
    public SponsorResponse sponsor;


    @Override
    public String toString() {
        return "WalletDetailsResponse{" +
                "id=" + id +
                ", user_id='" + user_id + '\'' +
                ", remark='" + remark + '\'' +
                ", credit_amount='" + credit_amount + '\'' +
                ", debit_amount='" + debit_amount + '\'' +
                ", balance_amount='" + balance_amount + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", sponsor=" + sponsor +
                '}';
    }
}
