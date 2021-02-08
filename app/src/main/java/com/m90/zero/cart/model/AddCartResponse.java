package com.m90.zero.cart.model;

import com.google.gson.annotations.SerializedName;
import com.m90.zero.productdetail.model.CartItem;

import java.util.Map;

public class AddCartResponse {

    @SerializedName("content_type")
    public String content_type;

    @SerializedName("message")
    public String message;

    @SerializedName("status")
    public int status;

    @SerializedName("data")
    public Map<String, CartItem> data;

    //public ArrayList<AddCartDetailResponse> data;

    @Override
    public String toString() {
        return "AddCartResponse{" +
                "content_type='" + content_type + '\'' +
                ", message='" + message + '\'' +
                ", status=" + status +
                ", data=" + data +
                '}';
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map<String, CartItem> getData() {
        return data;
    }

    public void setData(Map<String, CartItem> data) {
        this.data = data;
    }
}
