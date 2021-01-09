package com.m90.zero.profile.model;

import com.google.gson.annotations.SerializedName;

public class StateDetailsResponse {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    public StateDetailsResponse(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "StateDetailsResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
