package com.m90.zero.forgetpassword.model;


import com.google.gson.annotations.SerializedName;

public  class SetPassResponce {

    @SerializedName("data")
    public String data;
    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public Object message;
    @SerializedName("content_type")
    public String content_type;

    @Override
    public String toString() {
        return "SetPassResponce{" +
                "data='" + data + '\'' +
                ", status=" + status +
                ", message=" + message +
                ", content_type='" + content_type + '\'' +
                '}';
    }
}