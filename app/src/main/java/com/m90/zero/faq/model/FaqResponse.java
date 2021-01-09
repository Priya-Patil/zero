package com.m90.zero.faq.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FaqResponse {

    @SerializedName("content_type")
    public String content_type;

    @SerializedName("message")
    public String message;

    @SerializedName("status")
    public int status;

    @SerializedName("access_token")
    public String access_token;

    @SerializedName("data")
    public ArrayList<FaqDetailsResponce> data;

    @Override
    public String toString() {
        return "FaqModel{" +
                "content_type='" + content_type + '\'' +
                ", message='" + message + '\'' +
                ", status=" + status +
                ", access_token='" + access_token + '\'' +
                ", data=" + data +
                '}';
    }
}
