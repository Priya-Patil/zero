package com.m90.zero.about.model;


import com.google.gson.annotations.SerializedName;
import com.m90.zero.login.model.UserDetailsResponce;

public  class AboutResponce {

    @SerializedName("data")
    public AboutDetails data;
    @SerializedName("access_token")
    public String access_token;
    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public String message;
    @SerializedName("content_type")
    public String content_type;

    @Override
    public String toString() {
        return "AboutResponce{" +
                "data=" + data +
                ", access_token='" + access_token + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", content_type='" + content_type + '\'' +
                '}';
    }
}