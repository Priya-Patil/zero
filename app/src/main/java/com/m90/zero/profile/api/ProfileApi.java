package com.m90.zero.profile.api;


import android.content.Context;

import com.m90.zero.login.model.LoginResponce;
import com.m90.zero.profile.model.LeveltreeResponse;
import com.m90.zero.profile.model.ProfileImageUpdateResponse;
import com.m90.zero.profile.model.ProfileResponse;
import com.m90.zero.profile.model.ProfileUpdateResponse;
import com.m90.zero.retrofit.RetrofitClientInstance;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ProfileApi {


    @FormUrlEncoded
    @POST("customer/profile")
    Call  <ProfileResponse> getProfile (
            @Header("Authorization") String authHeader,
            @Field("user_id") int user_id
            );

    @Multipart
    @POST("customer/image-update")
    Call<ProfileImageUpdateResponse> updateProfileImage(
            @Header("Authorization") String authHeader,
                        @Part("id") RequestBody id,
                        @Part MultipartBody.Part file
    );

    @FormUrlEncoded
    @POST("customer/profile-update")
    Call  <ProfileUpdateResponse> updateProfile (
            @Header("Authorization") String authHeader,
            @Field("user_id") int user_id,
            @Field("first_name") String first_name,
            @Field("middle_name") String middle_name,
            @Field("last_name") String last_name,
            @Field("mobile_number") String mobile_number,
            @Field("email") String email,
            @Field("state_id") String state_id,
            @Field("city_id") String city_id,
            @Field("pincode") String pincode,
            @Field("address") String address
    );

    @FormUrlEncoded
    @POST("customer/level-tree")
    Call  <LeveltreeResponse> getLeveltree (
            @Field("user_id") int user_id

    );




}
