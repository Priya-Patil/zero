package com.m90.zero.profile.api;


import com.m90.zero.profile.model.StateResponse;
import com.m90.zero.profile.model.WalletResponse;
import com.m90.zero.reg.model.AfterPaymentRegResponce;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface CityApi {

    @GET()
    Call<StateResponse> getCity (
            @Url String url
    );

}
