package com.m90.zero.tac.api;


import com.m90.zero.about.model.AboutResponce;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TACApi {

    //http://api.eurekatalents.in/api/about
    @GET("tc")
    Call<AboutResponce> getTAC (
           );
}
