package com.m90.zero.reg.model;


import com.google.gson.annotations.SerializedName;

public  class CodeResponce {

    @SerializedName("id")
    public String id;
    @SerializedName("code")
    public String code;

    @Override
    public String toString() {
        return "CodeResponce{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}