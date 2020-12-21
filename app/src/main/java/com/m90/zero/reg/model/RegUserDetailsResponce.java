package com.m90.zero.reg.model;


import com.google.gson.annotations.SerializedName;

public  class RegUserDetailsResponce {

    @SerializedName("mobile_number")
    public String mobile_number;
    @SerializedName("email")
    public String email;
    @SerializedName("name")
    public String name;

    @SerializedName("updated_at")
    public String updated_at;
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("id")
    public int id;

    @Override
    public String toString() {
        return "RegUserDetailsResponce{" +
                "mobile_number='" + mobile_number + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", created_at='" + created_at + '\'' +
                ", id=" + id +
                '}';
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}