package com.m90.zero.forgetpassword.model;


import com.google.gson.annotations.SerializedName;

public  class OTPResponce {

    @SerializedName("data")
    public Object data;
    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public String message;
    @SerializedName("content_type")
    public String content_type;

    @Override
    public String toString() {
        return "OTPResponce{" +
                "data=" + data +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", content_type='" + content_type + '\'' +
                '}';
    }
}