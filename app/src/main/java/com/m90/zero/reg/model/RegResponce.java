package com.m90.zero.reg.model;


import com.google.gson.annotations.SerializedName;

public  class RegResponce {

    @SerializedName("data")
    public RegUserDetailsResponce data;
    @SerializedName("access_token")
    public String access_token;
    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public Object message;
    @SerializedName("content_type")
    public String content_type;

    public RegUserDetailsResponce getData() {
        return data;
    }

    public void setData(RegUserDetailsResponce data) {
        this.data = data;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    @Override
    public String toString() {
        return "RegResponce{" +
                "data=" + data +
                ", access_token='" + access_token + '\'' +
                ", status=" + status +
                ", message=" + message +
                ", content_type='" + content_type + '\'' +
                '}';
    }
}