package com.m90.zero.profile.api;


import com.m90.zero.profile.model.ProfileResponse;
import com.m90.zero.profile.model.StateResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface StateApi {

    @GET("state")
    Call  <StateResponse> getState ();


}
