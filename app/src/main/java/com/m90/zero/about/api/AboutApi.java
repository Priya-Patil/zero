package com.m90.zero.about.api;


import com.m90.zero.about.model.AboutResponce;
import com.m90.zero.login.model.LoginResponce;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AboutApi {

    //http://api.eurekatalents.in/api/about
    @GET("about")
    Call<AboutResponce> getAbout (
           );
}
