package com.m90.zero.reg.model;


import com.google.gson.annotations.SerializedName;

public  class AfterPaymentRegResponce {

    @SerializedName("data")
    public AfterPaymentRegDetailsResponce data;
    @SerializedName("access_token")
    public String access_token;
    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public Object message;
    @SerializedName("content_type")
    public String content_type;

    @Override
    public String toString() {
        return "AfterPaymentRegResponce{" +
                "data=" + data +
                ", access_token='" + access_token + '\'' +
                ", status=" + status +
                ", message=" + message +
                ", content_type='" + content_type + '\'' +
                '}';
    }
}