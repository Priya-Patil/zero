package com.m90.zero.login.model;


import com.google.gson.annotations.SerializedName;

public  class UserDetailsResponce {

    @SerializedName("id")
    public int id;
    @SerializedName("role_id")
    public int role_id;
    @SerializedName("name")
    public String name;
    @SerializedName("mobile_number")
    public String mobile_number;
    @SerializedName("email")
    public String email;

    @SerializedName("avatar")
    public String  avatar;
    @SerializedName("email_verified_at")
    public String email_verified_at;
    @SerializedName("status")
    public String status;
    @SerializedName("settings")
    public String settings;
    @SerializedName("created_at")
    public String created_at;

    @SerializedName("updated_at")
    public String updated_at;
    @SerializedName("mobile_verified_at")
    public String mobile_verified_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail_verified_at() {
        return email_verified_at;
    }

    public void setEmail_verified_at(String email_verified_at) {
        this.email_verified_at = email_verified_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getMobile_verified_at() {
        return mobile_verified_at;
    }

    public void setMobile_verified_at(String mobile_verified_at) {
        this.mobile_verified_at = mobile_verified_at;
    }

    @Override
    public String toString() {
        return "UserDetailsResponce{" +
                "id=" + id +
                ", role_id=" + role_id +
                ", name='" + name + '\'' +
                ", mobile_number='" + mobile_number + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email_verified_at='" + email_verified_at + '\'' +
                ", status='" + status + '\'' +
                ", settings='" + settings + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", mobile_verified_at='" + mobile_verified_at + '\'' +
                '}';
    }
}