package com.m90.zero.profile.api;


import com.m90.zero.profile.model.ProfileResponse;
import com.m90.zero.profile.model.WalletResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface WalletBalanceApi {


    @GET()
    Call<WalletResponse> getwallet_balance (
            @Header("Authorization") String authHeader,
            @Url String url
    );

}
