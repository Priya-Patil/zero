package com.m90.zero.reg.api;


import com.m90.zero.loging.model.LoginResponce;
import com.m90.zero.reg.model.CodeResponce;
import com.m90.zero.reg.model.RegResponce;
import com.m90.zero.reg.model.RegUserDetailsResponce;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RegApi {

    //http://api.eurekatalents.in/api/customer/register?first_name=Manish&last_name=Patil
    // &mobile_number=9823017728&email=kalpana.kambl4@gmail.com&password=123456&password_confirmation=123456&
    // sponsor_by=ZEEERO379988

    @FormUrlEncoded
    @POST("customer/register")
    Call<RegResponce> addUser (
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("mobile_number") String mobile_number,
            @Field("email") String email,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation,
            @Field("sponsor_by") String sponsor_by
            // lang: eng/mar

    );


    //http://api.eurekatalents.in/api/customer/weaksp
    @GET("customer/weaksp")
    Call<CodeResponce> getCode (
            // lang: eng/mar

    );
}
