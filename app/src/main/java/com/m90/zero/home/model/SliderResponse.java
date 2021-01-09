package com.m90.zero.home.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public  class SliderResponse {
    @SerializedName("data")
    public ArrayList<SliderDetails> data;

    @SerializedName("status")
    public int status;

    @SerializedName("message")
    public String message;

    @SerializedName("content_type")
    public String contentType;


    @Override
    public String toString() {
        return "SliderResponse{" +
                "data=" + data +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}
