package com.m90.zero.cart.api;


import com.m90.zero.cart.model.AddCartResponse;
import com.m90.zero.cart.model.CartResponse;
import com.m90.zero.cart.model.ConfrimOrderResponse;
import com.m90.zero.cart.model.EmptyCartResponse;
import com.m90.zero.cart.model.PlaceOrderResponse;
import com.m90.zero.productdetail.model.ProductDescribeAddheadResponse;
import com.m90.zero.reg.model.CodeResponce;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface CartApi {

    @POST()
    Call<AddCartResponse> addCart(
            @Header("Authorization") String authHeader,
            @Url String url
    );

    @FormUrlEncoded
    @POST("cart/add")
    Call<AddCartResponse> addCart3 (
            @Field("user_id") String user_id,
            @Field("product_id") String product_id
    );

    @FormUrlEncoded
    @POST("cart/remove/one")
    Call<ProductDescribeAddheadResponse> subSingle(
            @Header("Authorization") String authHeader,
            @Field("user_id") String user_id,
            @Field("product_id") String product_id
    );


    @POST()
    Call<ProductDescribeAddheadResponse> removeSingleItem(
            @Header("Authorization") String authHeader,
            @Url String url
    );

    @POST()
    Call<EmptyCartResponse> cartEmpty   (
            @Header("Authorization") String authHeader,
            @Url String url
    );



    @FormUrlEncoded
    @POST("cart/update")
    Call<AddCartResponse> updateQuantity (
            @Header("Authorization") String authHeader,
            @Field("user_id") String user_id,
            @Field("product_id") String product_id,
            @Field("quantity") String quantity
    );


    @FormUrlEncoded
    @POST("cart")
    Call<CartResponse> getCart2 (
            @Field("user_id") String user_id
    );

    @POST()
    Call<CartResponse> getCart (
            @Header("Authorization") String authHeader,
            @Url String url
    );


    @GET("customer/weaksp")
    Call<CodeResponce> getCode (
            // lang: eng/mar

    );

    @FormUrlEncoded
    @POST("order/place")
    Call<PlaceOrderResponse> placeOrder (
            @Header("Authorization") String authHeader,
            @Field("user_id") String user_id,
            @Field("total_amount") String total_amount,
            @Field("total_quantity") String total_quantity,
            @Field("is_paid") String is_paid,
            @Field("payment_method") String payment_method,
            @Field("shipping_full_name") String shipping_full_name,
            @Field("shipping_state") String shipping_state,
            @Field("shipping_city") String shipping_city,
            @Field("shipping_address") String shipping_address,
            @Field("shipping_pincode") String shipping_pincode,
            @Field("shipping_phone") String shipping_phone,
            @Field("billing_full_name") String billing_full_name,
            @Field("billing_state") String billing_state,
            @Field("billing_city") String billing_city,
            @Field("billing_address") String billing_address,
            @Field("billing_pincode") String billing_pincode,
            @Field("billing_phone") String billing_phone,
            @Field("items") String items
    );


    @FormUrlEncoded
    @POST("order/confirm")
    Call<ConfrimOrderResponse> orderConfirm (
            @Header("Authorization") String authHeader,
            @Field("order_id") int order_id,
            @Field("txnid") String txnid,
            @Field("easepayid") String easepayid,
            @Field("status") String status,
            @Field("error") String error
    );

}
