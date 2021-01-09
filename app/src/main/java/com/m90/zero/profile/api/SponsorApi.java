package com.m90.zero.profile.api;


import com.m90.zero.profile.model.StateResponse;
import com.m90.zero.profile.model.WalletResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface SponsorApi {



    @GET()
    Call<WalletResponse> getSponsorBalance (
            @Url String url
    );

}
