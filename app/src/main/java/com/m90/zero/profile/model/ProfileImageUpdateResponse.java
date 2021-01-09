package com.m90.zero.profile.model;

import com.google.gson.annotations.SerializedName;

public class ProfileImageUpdateResponse {


    @SerializedName("data")
    public int data;

    @SerializedName("content_type")
    public String content_type;

    @SerializedName("message")
    public String message;

    @SerializedName("status")
    public int status;

    @Override
    public String toString() {
        return "ProfileImageUpdateResponse{" +
                "data=" + data +
                ", content_type='" + content_type + '\'' +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
