package com.m90.zero.profile.api;


import com.m90.zero.profile.model.ProfileImageUpdateResponse;
import com.m90.zero.profile.model.ProfileResponse;
import com.m90.zero.profile.model.ProfileUpdateResponse;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ProfileApi {

    @FormUrlEncoded
    @POST("customer/profile")
    Call  <ProfileResponse> getProfile (
            @Field("user_id") int user_id
    );

    //@Part("profile_image") RequestBody file,
    //@Part("profile_image\"; filename=\"pp.png\" ") RequestBody file,
    @Multipart
    @POST("customer/image-update")
    Call<ProfileImageUpdateResponse> updateProfileImagec(
                        @Part("id") RequestBody id,
                        @Part("profile_image") RequestBody file);



    @Multipart
    @POST("customer/image-update")
    Call<ProfileImageUpdateResponse> updateProfileImage(
                        @Part("id") RequestBody id,
                        @Part MultipartBody.Part file
    );




    @FormUrlEncoded
    @POST("customer/profile-update")
    Call  <ProfileUpdateResponse> updateProfile (
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


}
