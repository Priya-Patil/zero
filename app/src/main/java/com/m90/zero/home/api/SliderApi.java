package com.m90.zero.home.api;


import com.m90.zero.home.model.SliderResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SliderApi {


    @GET("slider")
    Call<SliderResponse> getSlider();

}
