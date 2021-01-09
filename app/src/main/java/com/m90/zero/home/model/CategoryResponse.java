package com.m90.zero.home.model;

import com.google.gson.annotations.SerializedName;
import com.m90.zero.login.model.UserDetailsResponce;

import java.util.ArrayList;

public class CategoryResponse {
    @SerializedName("data")
    public ArrayList<CategoryDetailsResponse> data;
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
        return "CategoryResponse{" +
                "data=" + data +
                ", access_token='" + access_token + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", content_type='" + content_type + '\'' +
                '}';
    }
}
