package com.m90.zero.cart.api;


import com.m90.zero.cart.model.AddCartResponse;
import com.m90.zero.cart.model.CartResponse;
import com.m90.zero.reg.model.CodeResponce;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CartApi {


    @FormUrlEncoded
    @POST("cart/update")
    Call<AddCartResponse> addCart (
            @Field("customer_id") String customer_id,
            @Field("instance") String instance,
            @Field("total_qty") String total_qty,
            @Field("total_price") String total_price,
            @Field("items") String items
    );


    @FormUrlEncoded
    @POST("cart/get/cart")
    Call<CartResponse> getCart (
            @Field("customer_id") String customer_id
    );



    @GET("customer/weaksp")
    Call<CodeResponce> getCode (
            // lang: eng/mar

    );
}
