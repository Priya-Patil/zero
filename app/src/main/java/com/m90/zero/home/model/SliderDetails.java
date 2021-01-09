package com.m90.zero.home.model;

import com.google.gson.annotations.SerializedName;

public class SliderDetails {

    @SerializedName("id")
    public int id;

    @SerializedName("page_name")
    public String pageName;

    @SerializedName("section_name")
    public String sectionName;

    @SerializedName("slider_image")
    public String sliderImage;

    @Override
    public String toString() {
        return "SliderDetails{" +
                "id=" + id +
                ", pageName='" + pageName + '\'' +
                ", sectionName='" + sectionName + '\'' +
                ", sliderImage='" + sliderImage + '\'' +
                '}';
    }
}
