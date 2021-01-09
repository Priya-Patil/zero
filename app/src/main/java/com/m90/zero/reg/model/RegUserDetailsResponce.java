package com.m90.zero.reg.model;


import com.google.gson.annotations.SerializedName;

public  class RegUserDetailsResponce {

    @SerializedName("mobile_number")
    public String mobile_number;
    @SerializedName("email")
    public String email;
    @SerializedName("name")
    public String name;

    @SerializedName("updated_at")
    public String updated_at;
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("id")
    public int id;

    @SerializedName("amount")
    public String amount;
    @SerializedName("txnid")
    public String txnid;

    @Override
    public String toString() {
        return "RegUserDetailsResponce{" +
                "mobile_number='" + mobile_number + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", created_at='" + created_at + '\'' +
                ", id=" + id +
                ", amount=" + amount +
                ", txnid='" + txnid + '\'' +
                '}';
    }
}