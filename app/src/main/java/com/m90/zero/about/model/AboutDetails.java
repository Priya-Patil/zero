package com.m90.zero.about.model;


import com.google.gson.annotations.SerializedName;
import com.m90.zero.login.model.UserDetailsResponce;

public  class AboutDetails {

    @SerializedName("name")
    public String name;
    @SerializedName("description")
    public String description;
   }