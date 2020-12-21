package com.m90.zero.login.api;


import com.m90.zero.login.model.LoginResponce;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginApi {


   // http://api.eurekatalents.in/api/customer/login
  /* mobile_number9823017720
           password123456
*/
    @FormUrlEncoded
    @POST("customer/login")
    Call<LoginResponce> checkLogin (
            @Field("mobile_number") String mobile_number,
            @Field("password") String password
            // lang: eng/mar

    );
}
