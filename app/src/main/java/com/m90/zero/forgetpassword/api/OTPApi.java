package com.m90.zero.forgetpassword.api;



import com.m90.zero.forgetpassword.model.OTPResponce;
import com.m90.zero.forgetpassword.model.SetPassResponce;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface OTPApi {
    @FormUrlEncoded
    @POST("otp/send")
    Call<OTPResponce> getOTP (
            @Field("mobile_number") String mobile_number
            // lang: eng/mar

    );

    @FormUrlEncoded
    @POST("otp/password-reset")
    Call<SetPassResponce> setPassword (
            @Field("mobile_number") String mobile_number,
            @Field("otp") String otp,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation
            // lang: eng/mar

    );


}
