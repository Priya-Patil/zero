package com.m90.zero.profile.model;

import com.google.gson.annotations.SerializedName;
import com.m90.zero.faq.model.FaqDetailsResponce;

import java.util.ArrayList;

public class WalletResponse {

    @SerializedName("data")
    public ArrayList<WalletDetailsResponse> data;

    @SerializedName("content_type")
    public String content_type;

    @SerializedName("message")
    public String message;

    @SerializedName("status")
    public int status;

    @Override
    public String toString() {
        return "WalletResponse{" +
                "data=" + data +
                ", content_type='" + content_type + '\'' +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }


}
